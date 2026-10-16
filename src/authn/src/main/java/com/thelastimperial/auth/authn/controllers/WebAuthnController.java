package com.thelastimperial.auth.authn.controllers;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.api.CredentialRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thelastimperial.auth.authn.services.CredentialRecordService;

import lombok.AllArgsConstructor;

/**
 *
 * WebAuthnController Custom enpoints for webauthn.
*/
@AllArgsConstructor
@Controller
@RequestMapping("/webauthn")
public class WebAuthnController {
    private final CredentialRecordService credentialRecordService;

    /**
     * Custom Register view with the same default functionality.
     * @param principal the authenticated user.
     * @param model Object to send data to the view.
     * @return Thymeleaf template.
    */
    @GetMapping("/register")
    public String register(Principal principal, Model model) {
        List<CredentialRecord> credentials = credentialRecordService.findByUsername(
            principal.getName()
        );
        model.addAttribute("credentials", credentials);
        return "webauthn/register";
    }
    /**
     * Expose the default webauthn.js
     * When use disableDefaultRegistrationPage(true) the default /login/webauthn.js don't works
     * then explicit expose it, base on DefaultResourcesFilter.
     * @return The JavaScript webauthn.js.
    */
    @GetMapping("/webauthn.js")
    public ResponseEntity<Resource> webauthnJs() {
        Resource resource = new ClassPathResource(
            "org/springframework/security/spring-security-webauthn.js"
        );
        return ResponseEntity.ok()
            .contentType(
                new MediaType("text", "javascript", StandardCharsets.UTF_8)
            ).body(resource);
    }

}
