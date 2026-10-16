package com.thelastimperial.auth.authn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private WebAuthnProperty webAuthnProperty;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers("/webauthn/webauthn.js").permitAll()
                    .requestMatchers("/").authenticated()
                    .requestMatchers("/webauthn/**").authenticated()
                    .anyRequest().permitAll()
            )
            .formLogin(Customizer.withDefaults())
            .webAuthn(authn -> authn
                .rpName(webAuthnProperty.getRpName())
                .rpId(webAuthnProperty.getRpId())
                .allowedOrigins(webAuthnProperty.getAllowedOrigins())
                .disableDefaultRegistrationPage(true)
            );
        return http.build();
    }
}
