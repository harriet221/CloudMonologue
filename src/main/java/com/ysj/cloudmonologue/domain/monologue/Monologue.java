package com.ysj.cloudmonologue.domain.monologue;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
public class Monologue {
    private Long id;
    private LocalDate createDate;
}
