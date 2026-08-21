package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thelastimperial.auth.auth.services.ActivationService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/auth")
public class ActivationController {
    private final ActivationService activationService;

    @GetMapping("/activation/{tokenId}")
    public String activate(@PathVariable String tokenId) {
        activationService.activate(tokenId);
        return "auth/notify-activation";
    }
    
}
