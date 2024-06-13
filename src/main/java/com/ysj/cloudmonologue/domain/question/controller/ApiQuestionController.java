package com.ysj.cloudmonologue.domain.question.controller;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import com.ysj.cloudmonologue.domain.question.dto.QuestionDto;
import com.ysj.cloudmonologue.domain.question.service.QuestionService;
import com.ysj.cloudmonologue.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiQuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/today")
    public String showTodayQuestion(Model model) {
        Member currentUser = rq.getMember();
        if(currentUser == null) {
            model.addAttribute("error", "! 로그인 후 이용해 주세요 !");
            return "login";
        }
        MemberDto loggedMember = memberService.findByUserId(currentUser.getUserId());

        Long questionId;
        if(loggedMember.getBannedQuestions() == null) {
            questionId = questionService.pickQuestion(loggedMember.getId());
        } else {
            String[] bannedQuestionsArray = loggedMember.getBannedQuestions().split("@");
            List<Long> bannedQuestions = Arrays.stream(bannedQuestionsArray)
                    .map(Long::parseLong)
                    .toList();
            questionId = questionService.pickQuestion(bannedQuestions, loggedMember.getId());
        }
        QuestionDto question = questionService.findQuestionById(questionId);
        model.addAttribute("question", question);
        return "today";
    }

    @GetMapping("/banned/{id}")
    public String banQuestion(@PathVariable("id") Long questionId) {
        MemberDto loggedMember = memberService.findByUserId(rq.getMember().getUserId());
        String bannedQuestions = loggedMember.getBannedQuestions();
        if(bannedQuestions == null) bannedQuestions = ""+questionId;
        else bannedQuestions = bannedQuestions+"@"+questionId;
        loggedMember.setBannedQuestions(bannedQuestions);
        memberService.update(loggedMember);
        return "redirect:/today";
    }
}
