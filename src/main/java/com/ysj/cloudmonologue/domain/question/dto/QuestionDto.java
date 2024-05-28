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

/*
insert into QUESTION_TABLE (content) values ('Are you a happy person?');
insert into QUESTION_TABLE (content) values ('What is happiness for you?');
insert into QUESTION_TABLE (content) values ('What do you think is the color for happiness?');
insert into QUESTION_TABLE (content) values ('Do you think that happiness lies within you? Or does it depend upon other people and external things?');
insert into QUESTION_TABLE (content) values ('Can money buy happiness?');
insert into QUESTION_TABLE (content) values ('Is happiness a state of mind?');
insert into QUESTION_TABLE (content) values ('What makes you feel happy?');
insert into QUESTION_TABLE (content) values ('What are the three most important things for you to be happy?');
 */