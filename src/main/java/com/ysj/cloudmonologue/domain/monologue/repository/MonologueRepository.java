package com.ysj.cloudmonologue.domain.monologue.repository;

import com.ysj.cloudmonologue.domain.monologue.dto.MonologueDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonologueRepository {
    private final SqlSessionTemplate sql;

    public void save(MonologueDto newMonologue) {
        sql.insert("Monologue.save", newMonologue);
    }
}
