package com.ysj.cloudmonologue.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
    private Long id;
    private String createDate;
    private String modifyDate;
    private String userId;
    private String username;
    private String password;
}

/*
-- board_table
 drop table if exists MEMBER_TABLE;
 create table MEMBER_TABLE
 (
	id bigint primary key auto_increment,
    createDate datetime default now(),
    modifyDate datetime,
    userId varchar(20),
    username varchar(20),
    password varchar(20)
);
* */