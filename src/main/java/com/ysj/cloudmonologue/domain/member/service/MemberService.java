package com.ysj.cloudmonologue.domain.member.service;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.member.repository.MemberRepository;
import com.ysj.cloudmonologue.global.rq.Rq;
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
    private final Rq rq;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    public void save(MemberDto memberDto) {
        memberRepository.save(memberDto);
    }

    public MemberDto findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public void update(MemberDto memberDto) {
        memberRepository.update(memberDto);
    }

    @Transactional
    public RsData<Member> join(String username, String providerTypeCode, String nickname) {
        Member member = findByUsername(username).orElse(null);

        return join(username, "", nickname);
    }

    private Optional<Member> findByUsername(String username) {
        return memberRepository.findByUserName(username);
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
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) throws Exception {
        Member member = findByUsername(username)
                .orElseThrow(() -> new Exception("error"));

        if (!passwordMatches(member, password))
            throw new Exception("error");

        String refreshToken = member.getRefreshToken();
        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of(
                "200-1",
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

    public RsData<String> refreshAccessToken(String refreshToken) throws Exception {
        Member member = (Member) memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new Exception("error"));

        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }
}
