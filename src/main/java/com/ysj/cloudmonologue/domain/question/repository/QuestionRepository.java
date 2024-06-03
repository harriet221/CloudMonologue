package com.ysj.cloudmonologue.domain.question.repository;

import com.ysj.cloudmonologue.domain.question.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final SqlSessionTemplate sql;

    public QuestionDto findById(Long id) {
        return sql.selectOne("Question.findById", id);
    }

    public int countTotal() {
        return sql.selectOne("Question.countTotal");
    }

    public List<Long> getAnsweredQuestions(Long memberId) {
        return sql.selectList("Question.getAnsweredQuestions", memberId);
    }
}
