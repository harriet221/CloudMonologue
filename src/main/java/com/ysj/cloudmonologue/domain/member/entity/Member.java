package com.ysj.cloudmonologue.domain.member.entity;

import com.ysj.cloudmonologue.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString(callSuper = true)
public class Member extends BaseEntity {

    @Column(unique = true, length = 72)
    private String userId;

    @Column(length = 100)
    private String username;

    @Column(length = 72)
    private String password;

    private String bannedQuestions;
}
