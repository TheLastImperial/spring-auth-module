package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.auth.services.RegisterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/auth")
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping("/register")
    public String register(NewUser newUser) {
        return "auth/register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid NewUser newUser, BindingResult result) {
        if(result.hasErrors()){
            return "auth/register";
        }
        registerService.register(newUser);
        return "auth/notify-register";
    }
    
}
