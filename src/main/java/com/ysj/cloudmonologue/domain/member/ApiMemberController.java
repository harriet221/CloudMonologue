package com.ysj.cloudmonologue.domain.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @RequestMapping("/user")
public class ApiMemberController {
    @GetMapping("/")
    public String home() {
        return "Welcome to the Cloud-Monologue";
    }

    @GetMapping("/login")
    public String login() {
        return "Please login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "You have been logged out";
    }
}
