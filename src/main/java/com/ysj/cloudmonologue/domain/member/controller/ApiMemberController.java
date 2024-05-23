package com.ysj.cloudmonologue.domain.member.controller;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ApiMemberController {
    private final MemberService memberService;

    @GetMapping("/main")
    public String showMain(Principal principal) {
        System.out.println("----------"+principal.getName()+"--------------");
        return "main";
    }

    @GetMapping("/signup")
    public String save() {
        return "signup";
    }

    @PostMapping("/save")
    public String save(MemberDto memberDto) {
        // 이미 가입한 회원 인지 체크
        memberService.save(memberDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto) {
        // 있는 ID 인지 체크
        // 비번 맞는지 체크
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
