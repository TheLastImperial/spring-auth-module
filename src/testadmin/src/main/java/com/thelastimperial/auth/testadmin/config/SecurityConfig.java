package com.thelastimperial.auth.testadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;

import com.thelastimperial.auth.auth.handlers.CustomAuthenticationFailureHandler;
import com.thelastimperial.auth.auth.services.RecoveryService;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http,
        RememberMeServices rememberMeServices, RecoveryService recoveryService
    ) {
        http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/").hasRole("USER")
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers(
                "/css/auth/**","/js/auth/**","/auth/**", "/error"
            ).permitAll()
        )
        .formLogin( login -> login
            .loginPage("/auth/login")
            .failureUrl("/auth/login?error=true")
            .failureHandler(new CustomAuthenticationFailureHandler(recoveryService))
            .defaultSuccessUrl("/", true)
            .permitAll()
        )
        .rememberMe(rememberme -> rememberme
            .rememberMeServices(rememberMeServices)
            .rememberMeParameter("remember-me")
        )
        .logout(logout -> logout
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/auth/login")
            .permitAll()
        );
        return http.build();
    }
}
