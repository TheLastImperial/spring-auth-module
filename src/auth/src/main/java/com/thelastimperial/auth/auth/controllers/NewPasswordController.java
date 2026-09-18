package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thelastimperial.auth.auth.controllers.requests.NewPassword;
import com.thelastimperial.auth.auth.services.NewPasswordService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * NewPasswordController Controller to new password logic.
*/
@AllArgsConstructor
@Controller
@RequestMapping(path = "/auth/new-password")
@Slf4j
public class NewPasswordController {
    private final NewPasswordService newPasswordService;

    /**
     * Form to update user credentials.
     * @param token the token of the recovery request.
     * @param newPassword Object with the new password.
     * @param model Model view
     * @return The template name.
    */
    @GetMapping("/{token}")
    public String newPassword(@PathVariable String token, NewPassword newPassword, Model model) {
        model.addAttribute("token", token);
        return "auth/new-password";
    }

    /**
     * Path to update the user credentials.
     * @param newPassword Object with the new password.
     * @param result To control errors
     * @return template name to validate that update the credentials.
    */
    @PostMapping
    public String updatePassword(NewPassword newPassword, BindingResult result) {
        if(result.hasErrors()){
            return "auth/new-password";
        }
        newPasswordService.update(newPassword);
        return "auth/notify-password-updated";
    }
}
