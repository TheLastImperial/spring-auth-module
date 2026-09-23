package com.thelastimperial.auth.auth.autoconfiguration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.thelastimperial.auth.auth.handlers.CustomAuthenticationFailureHandler;
import com.thelastimperial.auth.auth.handlers.RegisterHandler;
import com.thelastimperial.auth.auth.handlers.RecoveryHandler;
import com.thelastimperial.auth.auth.services.ActivationService;
import com.thelastimperial.auth.auth.services.DefaultUserRoleService;
import com.thelastimperial.auth.auth.services.NewPasswordService;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.auth.services.RegisterInvitationService;
import com.thelastimperial.auth.auth.services.RegisterService;
import com.thelastimperial.auth.auth.services.impl.ActivationAuditServiceImpl;
import com.thelastimperial.auth.auth.services.impl.ActivationServiceImpl;
import com.thelastimperial.auth.auth.services.impl.DefaultNotificationServiceImpl;
import com.thelastimperial.auth.auth.services.impl.DefaultUserRoleServiceImpl;
import com.thelastimperial.auth.auth.services.impl.NewPasswordAuditServiceImpl;
import com.thelastimperial.auth.auth.services.impl.NewPasswordServiceImpl;
import com.thelastimperial.auth.auth.services.impl.RecoveryServiceImpl;
import com.thelastimperial.auth.auth.services.impl.RegisterInvitationServiceImpl;
import com.thelastimperial.auth.auth.services.impl.RegisterServiceImpl;
import com.thelastimperial.auth.auth.services.impl.UserDetailsServiceImpl;
import com.thelastimperial.auth.auth.services.impl.UserServiceImpl;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActionRepository;
import com.thelastimperial.auth.domain.repositories.UserActivationRepository;
import com.thelastimperial.auth.domain.repositories.UserAuditRepository;
import com.thelastimperial.auth.domain.repositories.UserInvitationRepository;
import com.thelastimperial.auth.domain.repositories.UserRecoveryRepository;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.auth.domain.repositories.UserRoleRepository;
import com.thelastimperial.utils.services.AuditService;
import com.thelastimperial.utils.services.UsernameService;

@AutoConfiguration
public class AuthAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(NotificationService.class)
    public NotificationService<Object> notificationService(){
        return new DefaultNotificationServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public RememberMeServices rememberMeServices(
        @Value("${com.thelastimperial.auth.rememberme.key}") String key,
        UserDetailsService userDetailsService, PersistentTokenRepository persistentTokenRepository
    ){
        return new PersistentTokenBasedRememberMeServices(
            key, userDetailsService, persistentTokenRepository
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcTokenRepositoryImpl getJdbcTokenRepositoryImpl(DataSource dataSource){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
        RememberMeServices rememberMeServices, RecoveryService recoveryService
    ) {
        http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/").hasRole("USER")
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
    @ConditionalOnMissingBean(name = "activationAuditServiceImpl")
    public AuditService<UserEntity> activationAuditServiceImpl(
        UserAuditRepository userAuditRepository, UserActionRepository userActionRepository
    ){
        return new ActivationAuditServiceImpl(userAuditRepository, userActionRepository);
    }

    @Bean
    @ConditionalOnMissingBean( name = "newPasswordAuditServiceImpl")
    public AuditService<UserEntity> newPasswordAuditServiceImpl(
        UserAuditRepository userAuditRepository, UserActionRepository userActionRepository
    ) {
        return new NewPasswordAuditServiceImpl(userAuditRepository, userActionRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ActivationService activationServiceImpl(
        UserActivationRepository userActivationRepository, UserRepository userRepository,
        AuditService<UserEntity> activationAuditServiceImpl
    ) {
        return new ActivationServiceImpl(userActivationRepository, userRepository,
            activationAuditServiceImpl
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public NewPasswordService newPasswordServiceImpl(UserRecoveryRepository userRecoveryRepository,
        UserRepository userRepository, PasswordEncoder passwordEncoder,
        AuditService<UserEntity> newPasswordAuditServiceImpl,
        NotificationService<?> newPasswordNotificationService
    ) {
        return new NewPasswordServiceImpl(userRecoveryRepository, userRepository, passwordEncoder,
                newPasswordAuditServiceImpl, newPasswordNotificationService
            );
    }

    @Bean
    @ConditionalOnMissingBean
    public RecoveryService recoveryServiceImpl(UserRecoveryRepository userRecoveryRepository,
        UsernameService usernameService
    ) {
        return new RecoveryServiceImpl(userRecoveryRepository, usernameService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RecoveryHandler recoveryHandler(RecoveryService recoveryService,
        NotificationService<?> recoveryNotificationService
    ){
        return new RecoveryHandler(recoveryService, recoveryNotificationService);
    }
    @Bean
    @ConditionalOnMissingBean
    public RegisterService registerServiceImpl(PasswordEncoder passwordEncoder,
        UserRepository userRepository,
        DefaultUserRoleService defaultUserRoleService
    ) {
        return new RegisterServiceImpl(
            passwordEncoder, userRepository, defaultUserRoleService
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisterInvitationService registerInvitationService(
        UserInvitationRepository userInvitationRepository
    ) {
        return new RegisterInvitationServiceImpl(userInvitationRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisterHandler handleRegisterService(
        RegisterService registerService,
        RegisterInvitationService registerInvitationService,
        UserActivationRepository userActivationRepository,
        NotificationService registerNotificationService
    ) {
        return new RegisterHandler(
            registerService, registerInvitationService,
            userActivationRepository, registerNotificationService
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultUserRoleService defaultUserRoleService(UserRoleRepository userRoleRepository) {
        return new DefaultUserRoleServiceImpl(userRoleRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public UsernameService userServiceImpl(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsServiceImpl(UsernameService usernameService) {
        return new UserDetailsServiceImpl(usernameService);
    }

}
