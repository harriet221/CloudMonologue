package com.ysj.cloudmonologue.domain.member.controller;

import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import com.ysj.cloudmonologue.global.rq.Rq;
import com.ysj.cloudmonologue.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiMemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/main")
    public String showMain(Model model) {
        Member member = rq.getMember();
        if(member == null) {
            model.addAttribute("error", "! 로그인 후 이용해 주세요 !");
            return "login";
        }
        model.addAttribute("member", member);
        return "main";
    }

    public record LoginRequestBody(@NotBlank String username, @NotBlank String password) {
    }

    @PostMapping(value = "/login")
    @Operation(summary = "로그인, accessToken, refreshToken 쿠키 생성됨")
    public String login(@Valid @RequestBody LoginRequestBody body) throws Exception {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.authAndMakeTokens(
                body.username,
                body.password
        );

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().refreshToken());
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().accessToken());

        return "redirect:/main";
    }

    @PostMapping("/logout")
    public String logout() {
        rq.setLogout();
        return "redirect:/";
    }
}
