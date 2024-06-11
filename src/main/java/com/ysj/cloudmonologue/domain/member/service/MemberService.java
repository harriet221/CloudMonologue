package com.ysj.cloudmonologue.domain.member.service;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.member.repository.MemberRepository;
import com.ysj.cloudmonologue.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
    public RsData<Member> join(String username, String providerTypeCode, String nickname, String profileImgUrl) {
        Member member = findByUsername(username).orElse(null);

        return join(
                username, "", nickname, profileImgUrl
        );
    }

    private Optional<Member> findByUsername(String username) {
        return memberRepository.findByUserName(username);
    }
}
