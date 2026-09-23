package com.thelastimperial.auth.admin.controllers;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.thelastimperial.auth.admin.handlers.UserHandler;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.PageUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping ("/admin/users")
@Slf4j
public class AdminUserController {
    private final UserRepository userRepository;
    private final UserHandler userHandler;

    @GetMapping
    public String index(
        Model model, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(PageUtils.getPage(page), PageUtils.getPageSize(size));
        Page<UserEntity> pageData = userRepository.findAll(pageable);
        model.addAttribute("page", pageData);
        return "admin/users/index";
    }

    @GetMapping("/lock-unlock/{username}")
    public String lockUser(@PathVariable String username, Principal principal) {
        userHandler.lockUnlock(username, principal);
        return "redirect:/admin/users";
    }

    @GetMapping("/show/{username}")
    public String show(@PathVariable String username, Model model) {
        UserEntity user = userHandler.getUser(username);
        log.info("Expiry: {}", user.getExpiry());
        model.addAttribute("user", user);
        model.addAttribute("expiry", user.getExpiry());
        return "admin/users/show";
    }

}
