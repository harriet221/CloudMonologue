package com.ysj.cloudmonologue.domain.monologue.entity;

import com.ysj.cloudmonologue.global.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString(callSuper = true)
public class Monologue extends BaseEntity {
    private Long questionId;
    private Long memberId;
    private String content;
}