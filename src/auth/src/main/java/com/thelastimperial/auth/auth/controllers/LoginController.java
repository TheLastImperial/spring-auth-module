package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(path = "/auth")
public class LoginController {

    @GetMapping("/login")
    public String index() {
        return "auth/login";
    }
}
