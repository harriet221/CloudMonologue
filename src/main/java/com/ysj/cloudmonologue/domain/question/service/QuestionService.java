package com.ysj.cloudmonologue.domain.question.service;

import com.ysj.cloudmonologue.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    // 질문 id로 내용 찾는 메서드
    public String findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public Long pickQuestion() {
        return pickQuestion(new ArrayList<>());
    }

    // 랜덤 질문 뽑는 메서드
    public Long pickQuestion(List<Long> bannedList) {
        int total = this.countTotalQuestions();
        Long randomQuestionId = -1L;

        Random random = new Random();
        while (randomQuestionId < 0L) {
            long randomId = random.nextInt(total) + 1;
            if (!bannedList.contains(randomId))
                randomQuestionId = randomId;
        }
        return randomQuestionId;
    }

    // 질문 총 개수 count 하는 메서드
    public int countTotalQuestions() {
        return questionRepository.countTotal();
    }

}
