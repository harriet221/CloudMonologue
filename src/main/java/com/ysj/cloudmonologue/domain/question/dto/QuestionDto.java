package com.ysj.cloudmonologue.domain.question.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionDto {
    private Long id;
    private String content;
}

/*
-- question_table
 drop table if exists QUESTION_TABLE;
 create table QUESTION_TABLE
 (
	id bigint primary key auto_increment,
    content varchar(255)
);
* */