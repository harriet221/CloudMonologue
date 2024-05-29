package com.ysj.cloudmonologue.domain.question.controller;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.service.MemberService;
import com.ysj.cloudmonologue.domain.question.dto.QuestionDto;
import com.ysj.cloudmonologue.domain.question.service.QuestionService;
import com.ysj.cloudmonologue.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiQuestionController {
    private final Rq rq;
    private final MemberService memberService;
    private final QuestionService questionService;

    @GetMapping("/today")
    public String showTodayQuestion(Model model) {
        String currentUser = rq.getMember();
        if(currentUser == null) {
            model.addAttribute("error", "! 로그인 후 이용해 주세요 !");
            return "login";
        }
        MemberDto loggedMember = memberService.findByUserId(currentUser);
        // 이미 답변한 질문인지 확인...은 언제하지
        // Monologue 쪽에서 userid, questionid 저장하고 있는데 이걸로 체크?
        // 애초에 질문 고를 때 memberid 줘서 monolgue랑 outer join 시키는 게 낫지 않나??
        Long questionId;
        if(loggedMember.getBannedQuestions() == null) {
            questionId = questionService.pickQuestion();
        } else {
            String[] bannedQuestionsArray = loggedMember.getBannedQuestions().split("@");
            List<Long> bannedQuestions = Arrays.stream(bannedQuestionsArray)
                    .map(Long::parseLong)
                    .toList();
            questionId = questionService.pickQuestion(bannedQuestions);
        }
        QuestionDto question = questionService.findQuestionById(questionId);
        model.addAttribute("question", question);
        return "today";
    }

    @PostMapping("/banned/{id}")
    public String banQuestion(@PathVariable("id") Long questionId) {
        MemberDto loggedMember = memberService.findByUserId(rq.getMember());
        String bannedQuestions = loggedMember.getBannedQuestions();
        if(bannedQuestions.equals("")) bannedQuestions += questionId;
        else bannedQuestions += ("@"+questionId);
        loggedMember.setBannedQuestions(bannedQuestions);
        return "redirect:/today";
    }
}
