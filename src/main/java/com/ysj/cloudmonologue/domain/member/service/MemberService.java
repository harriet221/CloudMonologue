package com.ysj.cloudmonologue.domain.member.service;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
