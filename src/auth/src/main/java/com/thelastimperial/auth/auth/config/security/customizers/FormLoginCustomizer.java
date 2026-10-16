package com.thelastimperial.auth.auth.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

import com.thelastimperial.auth.auth.handlers.CredentialsExpiredHandler;
import com.thelastimperial.auth.auth.services.RecoveryService;

import lombok.AllArgsConstructor;

/**
 *
 * FormLoginCustomizer Default Form login customizer for auth module.
*/
@AllArgsConstructor
public class FormLoginCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {
    private RecoveryService recoveryService;

    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> t) {
        t
            .loginPage("/auth/login")
            .failureUrl("/auth/login?error=true")
            .failureHandler(new CredentialsExpiredHandler(recoveryService))
            .defaultSuccessUrl("/", true)
            .permitAll();
    }

}
