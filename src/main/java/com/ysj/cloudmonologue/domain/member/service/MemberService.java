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

    public void login(MemberDto memberDto) {
        // 쿠키 / 세션 / JWT 등 유저 정보 유지 필요
    }
}
