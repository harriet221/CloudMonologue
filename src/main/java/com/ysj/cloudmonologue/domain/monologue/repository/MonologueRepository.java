package com.ysj.cloudmonologue.domain.monologue.repository;

import com.ysj.cloudmonologue.domain.monologue.entity.Monologue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonologueRepository extends JpaRepository<Monologue, Long> {

}
