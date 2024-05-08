package com.ysj.cloudmonologue.domain.member;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
public class Member {
    private Long id;

    private LocalDate createDate;

    private String username;

    private String password;
}
