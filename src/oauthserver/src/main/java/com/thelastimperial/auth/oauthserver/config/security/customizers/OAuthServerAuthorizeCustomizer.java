package com.thelastimperial.auth.oauthserver.config.security.customizers;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class OAuthServerAuthorizeCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry t) {
        t
            .requestMatchers(HttpMethod.GET, "/clients").hasRole("USER")
            .requestMatchers(HttpMethod.GET, "/clients/new").hasRole("USER")
            .requestMatchers(HttpMethod.POST, "/clients/new").hasRole("USER");
    }

}
