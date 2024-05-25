package com.ysj.cloudmonologue.global.rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;

    public void setSession(String key, String value) {
        req.getSession().setAttribute(key, value);
    }

    public String getMember() {
        HttpSession session = req.getSession(false); // 기존 세션이 없으면 null 반환
        if (session != null) {
            return (String) session.getAttribute("userId");
        }
        return null;
    }

    public void invalidateSession() {
        req.getSession().invalidate();
    }
}

