package com.ysj.cloudmonologue.domain.monologue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MonologueDto {
    private Long id;
    private String createDate;
    private Long questionId;
    private Long memberId;
    private String content;

    public MonologueDto(){};
    public MonologueDto(Long questionId, Long memberId, String content){
        this.questionId = questionId;
        this.memberId = memberId;
        this.content = content;
    };
}

/*
-- monologue_table
 drop table if exists MONOLOGUE_TABLE;
 create table MONOLOGUE_TABLE
 (
	id bigint primary key auto_increment,
    createDate datetime default now(),
    questionId bigint,
    memberId bigint,
    content text
);
* */