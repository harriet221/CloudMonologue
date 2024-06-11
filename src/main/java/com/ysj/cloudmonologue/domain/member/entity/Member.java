package com.ysj.cloudmonologue.domain.member.entity;

import com.ysj.cloudmonologue.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Column(unique = true)
    private String refreshToken;

    private String profileImgUrl;

    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Transient
    public List<String> getAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();

        authorities.add("ROLE_MEMBER");

        if (List.of("system", "admin").contains(username)) {
            authorities.add("ROLE_ADMIN");
        }

        return authorities;
    }
}
