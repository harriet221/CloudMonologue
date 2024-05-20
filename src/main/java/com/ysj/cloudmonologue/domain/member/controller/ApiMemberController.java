package com.ysj.cloudmonologue.domain.member.controller;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ApiMemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(MemberDto memberDto) {
        memberService.save(memberDto);
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String login() {
        return "Please login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "You have been logged out";
    }
}
