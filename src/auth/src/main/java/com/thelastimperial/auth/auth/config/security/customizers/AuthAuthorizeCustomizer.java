package com.thelastimperial.auth.auth.config.security.customizers;

import org.springframework.http.HttpMethod;
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
            .requestMatchers(HttpMethod.GET, "/activation/{tokenId}").permitAll()
            .requestMatchers(HttpMethod.GET, "/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/new-password/{token}").permitAll()
            .requestMatchers(HttpMethod.POST, "/new-password").permitAll()
            .requestMatchers(HttpMethod.GET, "/recovery").permitAll()
            .requestMatchers(HttpMethod.POST, "/recovery").permitAll()
            .requestMatchers(HttpMethod.GET, "/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/register").permitAll()
            .requestMatchers(
                "/css/auth/**","/js/auth/**","/auth/**", "/error"
            ).permitAll();
    }

}
