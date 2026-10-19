package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * LoginController Controller to login view.
*/
@Controller
public class LoginController {

    /**
     * Login path
     * @return template to login.
    */
    @GetMapping("/login")
    public String index() {
        return "auth/login";
    }
}
