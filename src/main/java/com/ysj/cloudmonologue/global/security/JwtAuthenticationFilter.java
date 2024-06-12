package com.ysj.cloudmonologue.global.security;


import com.ysj.cloudmonologue.domain.member.service.MemberService;
import com.ysj.cloudmonologue.global.rq.Rq;
import com.ysj.cloudmonologue.global.rsData.RsData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Rq rq;
    private final MemberService memberService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (!request.getRequestURI().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = rq.getHeader("Authorization", null);

        if (bearerToken != null) {
            String tokensStr = bearerToken.substring("Bearer ".length());
            String[] tokens = tokensStr.split(" ", 2);
            String refreshToken = tokens[0];
            String accessToken = tokens.length == 2 ? tokens[1] : "";

            if (!accessToken.isBlank()) {
                if (!memberService.validateToken(accessToken)) {
                    RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                    accessToken = rs.getData();
                    rq.setHeader("Authorization", "Bearer " + refreshToken + " " + accessToken);
                }

                SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
                rq.setLogin(securityUser);
            }
        } else {
            String accessToken = rq.getCookieValue("accessToken", "");

            if (!accessToken.isBlank()) {
                if (!memberService.validateToken(accessToken)) {
                    String refreshToken = rq.getCookieValue("refreshToken", "");

                    RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                    accessToken = rs.getData();
                    rq.setCrossDomainCookie("accessToken", accessToken);
                }
                SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
                rq.setLogin(securityUser);
            }
        }
        filterChain.doFilter(request, response);
    }
}