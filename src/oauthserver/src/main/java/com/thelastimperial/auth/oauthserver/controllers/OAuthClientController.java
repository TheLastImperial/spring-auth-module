package com.thelastimperial.auth.oauthserver.controllers;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.thelastimperial.auth.domain.responses.OAuthClient;
import com.thelastimperial.auth.oauthserver.controllers.request.NewRegisteredClient;
import com.thelastimperial.auth.oauthserver.services.OAuthClientService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/clients")
@Slf4j
public class OAuthClientController {
    private final OAuthClientService oAuthClientService;

    @GetMapping
    public String index(Principal principal, Model model,
        @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2")  int size
    ) {
        if(page < 1)
            page = 1;
        else
            page = page - 1;
        Page<OAuthClient> clients = oAuthClientService.getClients(principal, page, size);
        model.addAttribute("clients", clients);
        return "oauth/clients/index";
    }
    @GetMapping("/new")
    public String newClient(NewRegisteredClient newRegisteredClient) {
        return "oauth/clients/new";
    }

    @PostMapping("/new")
    public String create(NewRegisteredClient newRegisteredClient, BindingResult result,
        Principal principal, Model model
    ) {
        if(result.hasErrors()){
            return "oauth/clients/new";
        }
        OAuthClient client = oAuthClientService.save(newRegisteredClient, principal);
        model.addAttribute("client", client);
        return "oauth/clients/client";
    }
}
