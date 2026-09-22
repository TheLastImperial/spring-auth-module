package com.thelastimperial.auth.admin.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;

import com.thelastimperial.auth.admin.handlers.UserHandler;
import com.thelastimperial.auth.admin.services.impl.LockUnlockAuditServiceImpl;
import com.thelastimperial.auth.auth.handlers.CustomAuthenticationFailureHandler;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActionRepository;
import com.thelastimperial.auth.domain.repositories.UserAuditRepository;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.entities.AuditWrapper;
import com.thelastimperial.utils.services.AuditService;
import com.thelastimperial.utils.services.UsernameService;

@AutoConfiguration
public class AdminAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
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

    @Bean
    @ConditionalOnMissingBean
    public AuditService<AuditWrapper<UserEntity>> lockUnlockAuditServiceImpl(
        UserAuditRepository userAuditRepository, UserActionRepository userActionRepository
    ) {
        return new LockUnlockAuditServiceImpl(userAuditRepository, userActionRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserHandler userHandler(
        UserRepository userRepository, UsernameService usernameService,
        AuditService<AuditWrapper<UserEntity>> lockUnlockAuditServiceImpl
    ){
        return new UserHandler(userRepository, usernameService, lockUnlockAuditServiceImpl);
    }
}
