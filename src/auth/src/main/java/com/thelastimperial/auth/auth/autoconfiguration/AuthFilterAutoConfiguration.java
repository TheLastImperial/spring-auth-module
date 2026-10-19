package com.thelastimperial.auth.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.thelastimperial.auth.auth.config.security.customizers.AuthAuthorizeCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.FormLoginCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.LogoutCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.RememberMeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnAuthorizeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnCustomizer;

import lombok.AllArgsConstructor;

/**
 *
 * AuthFilterAutoConfiguration default filters to Auth module.
*/
@AllArgsConstructor
@AutoConfiguration
@AutoConfigureAfter(AuthControllerAutoConfiguration.class)
public class AuthFilterAutoConfiguration {
    private WebAuthnAuthorizeCustomizer webAuthnAuthorizeCustomizer;
    private AuthAuthorizeCustomizer authAuthorizeCustomizer;

    private WebAuthnCustomizer webAuthnCustomizer;
    private FormLoginCustomizer formLoginCustomizer;
    private RememberMeCustomizer rememberMeCustomizer;
    private LogoutCustomizer logoutCustomizer;

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http
        .authorizeHttpRequests(auth -> {
            webAuthnAuthorizeCustomizer.customize(auth);
            authAuthorizeCustomizer.customize(auth);
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
        );
        return http.build();
    }
}
