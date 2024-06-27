package com.ysj.cloudmonologue.domain.member.service;

import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.member.repository.MemberRepository;
import com.ysj.cloudmonologue.global.exceptions.CodeMsg;
import com.ysj.cloudmonologue.global.exceptions.GlobalException;
import com.ysj.cloudmonologue.global.rsData.RsData;
import com.ysj.cloudmonologue.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public RsData<Member> join(String username, String password) {
        return join(username, password, username);
    }

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        if (findByUsername(username).isPresent()) {
            return RsData.of(CodeMsg.E400_Bad_Request.getCode(),"이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .refreshToken(authTokenService.genRefreshToken())
                .build();

        memberRepository.save(member);
        return RsData.of("200", "%s님 환영합니다. 회원가입이 완료되었습니다. 로그인 후 이용해주세요.".formatted(member.getUsername()), member);
    }

    @Transactional
    public RsData<Member> modifyOrJoin(String username, String providerTypeCode, String nickname) {
        Member member = findByUsername(username).orElse(null);

        if (member == null) {
            return join(
                    username, "", nickname
            );
        }
        return modify(member, nickname);
    }

    @Transactional
    public RsData<Member> modify(Member member, String nickname) {
        member.setNickname(nickname);
        return RsData.of("200", "회원 정보가 수정되었습니다.");
    }

    public record AuthAndMakeTokensResponseBody(
            @NonNull
            Member member,
            @NonNull
            String accessToken,
            @NonNull
            String refreshToken
    ) {}

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) {
        Member member = findByUsername(username)
                .orElseThrow(() -> new GlobalException(CodeMsg.E404_Not_Found.getCode(), "해당 유저가 존재하지 않습니다."));

        if (!passwordMatches(member, password))
            throw new GlobalException(CodeMsg.E400_Bad_Request.getCode(), "비밀번호가 일치하지 않습니다.");

        String refreshToken = member.getRefreshToken();
        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of(
                "200",
                "로그인 성공",
                new AuthAndMakeTokensResponseBody(member, accessToken, refreshToken)
        );
    }

    @Transactional
    public String genAccessToken(Member member) {
        return authTokenService.genAccessToken(member);
    }

    public boolean passwordMatches(Member member, String password) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = authTokenService.getDataFrom(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<String> authorities = (List<String>) payloadBody.get("authorities");

        return new SecurityUser(
                id,
                username,
                "",
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    public boolean validateToken(String token) {
        return authTokenService.validateToken(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = (Member) memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new GlobalException(CodeMsg.E404_Not_Found.getCode(), "해당 리프레시 토큰이 존재하지 않습니다."));

        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }
}
