package com.ysj.cloudmonologue.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    // private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**")
                                .permitAll()
                                .anyRequest()
                                .permitAll()
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                )
                .csrf(
                        csrf ->
                                csrf.ignoringRequestMatchers(
                                        "/h2-console/**"
                                )
//                )
//                .oauth2Login( // 카카오 로그인 활성화에 필수적
//                        oauth2Login ->
//                                oauth2Login
//                                        .successHandler(customAuthenticationSuccessHandler)
                );

        return http.build();
    }
}
