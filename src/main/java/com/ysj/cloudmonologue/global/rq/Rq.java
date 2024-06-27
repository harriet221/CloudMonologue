package com.ysj.cloudmonologue.global.rq;

import com.ysj.cloudmonologue.domain.member.entity.Member;
import com.ysj.cloudmonologue.global.app.AppConfig;
import com.ysj.cloudmonologue.global.security.SecurityUser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final EntityManager entityManager;
    private Member member;

    // Cookie
    public void setCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }

    private String getSiteCookieDomain() {
        String cookieDomain = AppConfig.getSiteCookieDomain();
        if (!cookieDomain.equals("localhost")) return "." + cookieDomain;
        return null;
    }

    public void setCrossDomainCookie(String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .domain(getSiteCookieDomain())
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
    }

    public void removeCrossDomainCookie(String name) {
        removeCookie(name);
        ResponseCookie cookie = ResponseCookie.from(name, null)
                .path("/")
                .maxAge(0)
                .domain(getSiteCookieDomain())
                .secure(true)
                .httpOnly(true)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
    }

    public Cookie getCookie(String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public String getCookieValue(String name, String defaultValue) {
        Cookie cookie = getCookie(name);
        if (cookie == null) {
            return defaultValue;
        }
        return cookie.getValue();
    }

    public void removeCookie(String name) {
        Cookie cookie = getCookie(name);
        if (cookie == null) return;
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    // Session
    public void setSession(String key, String value) {
        req.getSession().setAttribute(key, value);
    }

    public void invalidateSession() {
        req.getSession().invalidate();
    }

    public void destroySession() {
        req.getSession().invalidate();
    }

    // member / user
    public Member getMember() {
        if (isLogout()) return null;

        if (member == null) {
            member = entityManager.getReference(Member.class, getUser().getId());
        }
        return member;
    }

    private SecurityUser getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(context -> context.getAuthentication())
                .filter(authentication -> authentication.getPrincipal() instanceof SecurityUser)
                .map(authentication -> (SecurityUser) authentication.getPrincipal())
                .orElse(null);
    }

    // login / logout
    public boolean isLogin() {
        return getUser() != null;
    }

    public void setLogin(SecurityUser securityUser) {
        SecurityContextHolder.getContext().setAuthentication(securityUser.genAuthentication());
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public void setLogout() {
        removeCrossDomainCookie("accessToken");
        removeCrossDomainCookie("refreshToken");
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    // HTTP request message
    public String getHeader(String name, Object o) {
        return req.getHeader(name);
    }

    public void setHeader(String name, String value) {
        resp.setHeader(name, value);
    }

    public boolean isFrontUrl(String url) {
        return url.startsWith(AppConfig.getSiteFrontUrl());
    }

    // redirect
//    public String redirect(String url, String msg) {
//        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("redirect:");
//        sb.append(url);
//
//        if (msg != null) {
//            sb.append("?msg=");
//            sb.append(msg);
//        }
//        return sb.toString();
//    }
//
//    public String historyBack(String msg) {
//        req.setAttribute("failMsg", msg);
//        return "global/js";
//    }
//
//    public String redirectOrBack(RsData<?> rs, String path) {
//        if (rs.isFail()) return historyBack(rs.getMsg());
//        return redirect(path, rs.getMsg());
//    }

    // check admin
//    public boolean isAdmin() {
//        if (isLogout()) return false;
//        return getUser()
//                .getAuthorities()
//                .stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//    }
}