package com.ysj.cloudmonologue.domain.member.controller;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ApiMemberController {
    private final MemberService memberService;

    @GetMapping("/main")
    public String showMain() {
        return "main";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto) {
        // 이미 가입한 회원 인지 체크
        memberService.save(memberDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto, Model model) {
        // 유효한 ID 인지 체크 without Spring Security
        MemberDto memberInfoDto = memberService.findByUserId(memberDto.getUserId());
        if(memberInfoDto == null) {
            model.addAttribute("error", "! 해당 ID가 존재하지 않습니다 !");
            return "login";
        }
        // 비번 일치 체크 without Spring Security
        if(!memberDto.getPassword().equals(memberInfoDto.getPassword())) {
            model.addAttribute("error", "! 비밀번호가 일치하지 않습니다 !");
            return "login";
        }
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
