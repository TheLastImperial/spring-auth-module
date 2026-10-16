package com.thelastimperial.auth.authn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * HomeController Default root page
*/
@Controller
public class HomeController {
    /**
     * Default Root View
     * @return Thymeleaf template.
    */
    @GetMapping
    public String index() {
        return "home/index";
    }
}
