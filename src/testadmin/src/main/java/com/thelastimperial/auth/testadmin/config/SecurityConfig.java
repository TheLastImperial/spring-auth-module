package com.thelastimperial.auth.testadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.thelastimperial.auth.admin.config.security.customizers.AdminAuthorizeCustomizer;
import com.thelastimperial.auth.auth.config.filters.LoginRateLimitFilter;
import com.thelastimperial.auth.auth.config.security.customizers.AuthAuthorizeCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.FormLoginCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.LogoutCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.RememberMeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnAuthorizeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnCustomizer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private WebAuthnAuthorizeCustomizer webAuthnAuthorizeCustomizer;
    private AuthAuthorizeCustomizer authAuthorizeCustomizer;
    private AdminAuthorizeCustomizer adminAuthorizeCustomizer;

    private WebAuthnCustomizer webAuthnCustomizer;
    private FormLoginCustomizer formLoginCustomizer;
    private RememberMeCustomizer rememberMeCustomizer;
    private LogoutCustomizer logoutCustomizer;
    private LoginRateLimitFilter loginRateLimitFilter;

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) {

        http
        .authorizeHttpRequests(auth -> {
            authAuthorizeCustomizer.customize(auth);
            webAuthnAuthorizeCustomizer.customize(auth);
            adminAuthorizeCustomizer.customize(auth);
            auth.anyRequest().authenticated();
        })
        .formLogin(
            formLoginCustomizer
        )
        .rememberMe(
            rememberMeCustomizer
        )
        .logout(
            logoutCustomizer
        )
        .webAuthn(
            webAuthnCustomizer
        )
        .addFilterBefore(
            loginRateLimitFilter, UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
}
