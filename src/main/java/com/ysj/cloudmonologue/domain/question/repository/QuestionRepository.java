package com.ysj.cloudmonologue.domain.question.repository;


import com.ysj.cloudmonologue.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findQuestionById(Long id);

    @Query("SELECT count(*) from Question")
    int countTotal();

    @Query("SELECT q.id " +
            "FROM Question q " +
            "LEFT JOIN Monologue m ON q.id = m.questionId AND m.memberId = :memberId " +
            "WHERE m.questionId IS NOT NULL")
    List<Long> getAnsweredQuestions(@Param("memberId") Long memberId);
}
