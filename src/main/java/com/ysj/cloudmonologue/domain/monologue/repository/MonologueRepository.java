package com.ysj.cloudmonologue.domain.monologue.repository;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonologueRepository {
    private final SqlSessionTemplate sql;

}
