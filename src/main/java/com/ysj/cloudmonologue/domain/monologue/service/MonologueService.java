package com.ysj.cloudmonologue.domain.monologue.service;

import com.ysj.cloudmonologue.domain.monologue.entity.Monologue;
import com.ysj.cloudmonologue.domain.monologue.repository.MonologueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonologueService {
    private final MonologueRepository monologueRepository;

    public void save(Long questionId, Long memberId, String content) {
        Monologue newMonologue = new Monologue(questionId, memberId, content);
        monologueRepository.save(newMonologue);
    }
}
