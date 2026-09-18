package com.thelastimperial.auth.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * HomeController Default Root Home
*/
@Controller
public class HomeController {
    /**
     * Root Path
     * @return template name.
    */
    @GetMapping
    public String index() {
        return "home/index";
    }

}
