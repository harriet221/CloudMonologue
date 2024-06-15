package com.ysj.cloudmonologue.domain.monologue.entity;

import com.ysj.cloudmonologue.global.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PUBLIC)
@Builder
@Setter
@Getter
@ToString(callSuper = true)
public class Monologue extends BaseEntity {
    private Long questionId;
    private Long memberId;
    private String content;
}