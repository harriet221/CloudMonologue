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

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto) {
        memberService.save(memberDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto) {
        memberService.login(memberDto);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        return "You have been logged out";
    }
}
