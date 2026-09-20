package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.auth.handlers.RegisterHandler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * RegisterController Controller to register a new user.
*/
@AllArgsConstructor
@Controller
@RequestMapping(path = "/auth/register")
public class RegisterController {
    private final RegisterHandler registerHandler;

    /**
     * Form to register a new user.
     * @param newUser
     * @return
    */
    @GetMapping
    public String register(NewUser newUser, @RequestParam String invitation) {
        if(invitation != null)
            newUser.setInvitation(invitation);
        return "auth/register";
    }
    /**
     * Save the register for a new user.
     * @param newUser Object to be created.
     * @param result Manage errors.
     * @return Template name.
    */
    @PostMapping
    public String registerUser(@Valid NewUser newUser, BindingResult result) {
        if(result.hasErrors()){
            return "auth/register";
        }
        registerHandler.register(newUser);
        return "auth/notify-register";
    }

}
