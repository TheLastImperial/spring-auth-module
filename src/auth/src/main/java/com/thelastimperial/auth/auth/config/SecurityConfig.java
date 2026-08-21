package com.thelastimperial.auth.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/hola").hasRole("USER")
            .requestMatchers("/css/auth/**","/js/auth/**","/auth/**").permitAll()
        )
        .formLogin( login -> login
            .loginPage("/auth/login")
            .failureUrl("/auth/login?error=true")
            .defaultSuccessUrl("/", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/auth/login")
            .permitAll()
        );
        return http.build();
    }
}
