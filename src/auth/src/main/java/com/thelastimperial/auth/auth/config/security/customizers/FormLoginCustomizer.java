package com.thelastimperial.auth.auth.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

import com.thelastimperial.auth.auth.handlers.AuthenticationFailureHandler;

import lombok.AllArgsConstructor;

/**
 *
 * FormLoginCustomizer Default Form login customizer for auth module.
*/
@AllArgsConstructor
public class FormLoginCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {
    private final AuthenticationFailureHandler failureHandler;

    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> t) {
        t
            .loginPage("/login")
            .failureUrl("/login?error=true")
            .failureHandler(failureHandler)
            .defaultSuccessUrl("/", true)
            .permitAll();
    }

}
