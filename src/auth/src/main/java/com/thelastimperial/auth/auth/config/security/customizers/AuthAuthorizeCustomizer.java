package com.thelastimperial.auth.auth.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
/**
 *
 * AuthAuthorizeCustomizer Default authorization to Auth module
*/
public class AuthAuthorizeCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry t) {
        t
            .requestMatchers(
                "/css/auth/**","/js/auth/**","/auth/**", "/error"
            ).permitAll();
    }

}
