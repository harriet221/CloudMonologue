package com.ysj.cloudmonologue.domain.question.controller;

import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import com.ysj.cloudmonologue.domain.question.entity.Question;
import com.ysj.cloudmonologue.domain.question.service.QuestionService;
import com.ysj.cloudmonologue.global.exceptions.CodeMsg;
import com.ysj.cloudmonologue.global.exceptions.GlobalException;
import com.ysj.cloudmonologue.global.rq.Rq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class ApiQuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/today")
    @Operation(summary = "오늘의 질문 보기")
    public String showTodayQuestion(Model model) {
        Member member = rq.getMember();
        if(member == null) {
            model.addAttribute("error", "! 로그인 후 이용해 주세요 !");
            return "login";
        }

        Long questionId;
        if(member.getBannedQuestions() == null) {
            questionId = questionService.pickQuestion(member.getId());
        } else {
            String[] bannedQuestionsArray = member.getBannedQuestions().split("@");
            List<Long> bannedQuestions = Arrays.stream(bannedQuestionsArray)
                    .map(Long::parseLong)
                    .toList();
            questionId = questionService.pickQuestion(bannedQuestions, member.getId());
        }
        Optional<Question> question = questionService.findQuestionById(questionId);
        if (question.isPresent()) {
            model.addAttribute("question", question);
        } else {
            throw new GlobalException(CodeMsg.E404_Not_Found.getCode(), CodeMsg.E404_Not_Found.getMessage());
        }
        return "today";
    }

    @GetMapping("/banned/{id}")
    public String banQuestion(@PathVariable("id") Long questionId) {
        Member member = rq.getMember();
        String bannedQuestions = member.getBannedQuestions();
        if(bannedQuestions == null) bannedQuestions = ""+questionId;
        else bannedQuestions = bannedQuestions+"@"+questionId;
        member.setBannedQuestions(bannedQuestions);
        memberService.save(member);
        return "redirect:/today";
    }
}
