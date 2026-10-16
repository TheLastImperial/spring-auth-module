package com.thelastimperial.auth.authn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnAuthorizeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnCustomizer;

@Configuration
public class SecurityConfig {
    private WebAuthnCustomizer webAuthnCustomizer;
    private WebAuthnAuthorizeCustomizer webAuthnAuthorizeCustomizer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> {
                webAuthnAuthorizeCustomizer.customize(auth);
                auth
                    .requestMatchers("/").authenticated()
                    .anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults())
            .webAuthn(webAuthnCustomizer);
        return http.build();
    }
}
