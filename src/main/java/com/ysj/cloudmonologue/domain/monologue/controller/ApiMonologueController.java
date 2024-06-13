package com.ysj.cloudmonologue.domain.monologue.controller;

import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.domain.monologue.service.MonologueService;
import com.ysj.cloudmonologue.domain.question.dto.QuestionDto;
import com.ysj.cloudmonologue.domain.question.service.QuestionService;
import com.ysj.cloudmonologue.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ApiMonologueController {
    private final MonologueService monologueService;
    private final QuestionService questionService;
    private final Rq rq;

    @GetMapping("/{id}/answer")
    public String giveAnswer(@PathVariable("id") Long questionId, Model model) {
        QuestionDto questionDto = questionService.findQuestionById(questionId);
        model.addAttribute("question", questionDto);
        return "monologue";
    }

    @PostMapping("/{id}/answer")
    public String giveAnswer(@PathVariable("id") Long questionId, String content) throws Exception {
        Member member = rq.getMember();
        if(member == null) throw new Exception("error");
        monologueService.save(questionId, member.getId(), content);
        return "redirect:/main";
    }
}
