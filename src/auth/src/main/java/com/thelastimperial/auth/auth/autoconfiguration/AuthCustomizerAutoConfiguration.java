package com.thelastimperial.auth.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.RememberMeServices;

import com.thelastimperial.auth.auth.config.security.customizers.AuthAuthorizeCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.FormLoginCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.LogoutCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.RememberMeCustomizer;
import com.thelastimperial.auth.auth.handlers.AuthenticationFailureHandler;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.utils.services.RateLimitService;

/**
 *
 * AuthCustomizerAutoConfiguration Creation of Customizers components to Auth module.
*/
@AutoConfiguration
@AutoConfigureAfter(AuthControllerAutoConfiguration.class)
public class AuthCustomizerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuthAuthorizeCustomizer authAuthorizeCustomizer() {
        return new AuthAuthorizeCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public FormLoginCustomizer formLoginCustomizer(
        AuthenticationFailureHandler authenticationFailureHandler
    ) {
        return new FormLoginCustomizer(authenticationFailureHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public RememberMeCustomizer rememberMeCustomizer(RememberMeServices rememberMeServices) {
        return new RememberMeCustomizer(rememberMeServices);
    }

    @Bean
    @ConditionalOnMissingBean
    public LogoutCustomizer logoutCustomizer() {
        return new LogoutCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler(
        RecoveryService recoveryService, RateLimitService loginRateLimitService
    ) {
        return new AuthenticationFailureHandler(recoveryService, loginRateLimitService);
    }
}
