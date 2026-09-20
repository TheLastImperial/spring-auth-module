package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;
import com.thelastimperial.auth.auth.handlers.RecoveryHandler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/**
 *
 * RecoveryController Controller to request a recovery account.
*/
@AllArgsConstructor
@Controller
@RequestMapping(path = "/auth/recovery")
public class RecoveryController {
    private final RecoveryHandler recoveryHandler;

    /**
     * Form to request a recovery
     * @param recovery Object with data to request a recovery.
     * @return Template name.
    */
    @GetMapping
    public String recovery(Recovery recovery) {
        return "auth/recovery";
    }

    /**
     * Request a recovery
     * @param recovery Object to request a rrecovery.
     * @param result To manage errors.
     * @return Template name.
    */
    @PostMapping
    public String sendRecovery(@Valid Recovery recovery, BindingResult result) {
        if(result.hasErrors()){
            return "auth/recovery";
        }
        recoveryHandler.generate(recovery);
        return "redirect:/auth/recovery?generated=true";
    }

}
