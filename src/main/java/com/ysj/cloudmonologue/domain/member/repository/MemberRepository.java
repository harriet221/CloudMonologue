package com.ysj.cloudmonologue.domain.member.repository;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final SqlSessionTemplate sql;

    public void save(MemberDto memberDto) {
        sql.insert("Member.save", memberDto);
    }
}
