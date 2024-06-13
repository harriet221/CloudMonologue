package com.ysj.cloudmonologue.domain.member.controller;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import com.ysj.cloudmonologue.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ApiMemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/main")
    public String showMain(Model model) {
        MemberDto loggedMember = memberService.findByUserId(rq.getMember().getUserId());
        if(loggedMember == null) {
            model.addAttribute("error", "! 로그인 후 이용해 주세요 !");
            return "login";
        }
        model.addAttribute("member", loggedMember);
        return "main";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto, Model model) {
        // 이미 가입한 ID 인지 체크
        MemberDto memberInfoDto = memberService.findByUserId(memberDto.getUserId());
        if(memberInfoDto != null) {
            model.addAttribute("error", "! 이미 사용 중인 ID 입니다 !");
            return "signup";
        }
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
        rq.setSession("userId", memberDto.getUserId());
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        rq.invalidateSession();
        System.out.println("로그아웃 완료");
        return "redirect:/";
    }
}
