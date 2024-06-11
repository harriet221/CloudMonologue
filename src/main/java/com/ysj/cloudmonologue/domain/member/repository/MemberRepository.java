package com.ysj.cloudmonologue.domain.member.repository;

import com.ysj.cloudmonologue.domain.member.dto.MemberDto;
import com.ysj.cloudmonologue.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final SqlSessionTemplate sql;

    public void save(MemberDto memberDto) {
        sql.insert("Member.save", memberDto);
    }

    public MemberDto findByUserId(String userId) {
        return sql.selectOne("Member.findByUserId", userId);
    }

    public void update(MemberDto memberDto) {
        sql.update("Member.update", memberDto);
    }

    public Optional<Member> findByUserName(String username) {
        return sql.selectOne("Member.findByUserName", username);
    }
}
