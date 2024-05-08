package com.ysj.cloudmonologue.domain.dialogue;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Dialogue {
    private Long id;
    private LocalDate createDate;
}
