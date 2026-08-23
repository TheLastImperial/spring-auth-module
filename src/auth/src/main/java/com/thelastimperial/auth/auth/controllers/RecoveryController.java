package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;
import com.thelastimperial.auth.auth.services.RecoveryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/auth")
public class RecoveryController {
    private final RecoveryService recoveryService;

    @GetMapping("/recovery")
    public String recovery(Recovery recovery) {
        return "auth/recovery";
    }

    @PostMapping("/recovery")
    public String sendRecovery(@Valid Recovery recovery, BindingResult result) {
        if(result.hasErrors()){
            return "auth/recovery";
        }
        recoveryService.generate(recovery);
        return "redirect:/auth/recovery?generated=true";
    }
    
}
