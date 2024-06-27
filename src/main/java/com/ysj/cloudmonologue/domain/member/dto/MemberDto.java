package com.ysj.cloudmonologue.domain.member.dto;

import com.ysj.cloudmonologue.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter
@ToString
public class MemberDto {
    private Long id;
    private LocalDateTime createDate;
    private String username;
    private String nickname;
    private String password;
    private String bannedQuestions;
    // private List<Long> bannedQuestions;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
        this.bannedQuestions = member.getBannedQuestions();
    };
}