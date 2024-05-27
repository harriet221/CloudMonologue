package com.ysj.cloudmonologue.domain.question.repository;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final SqlSessionTemplate sql;

}
