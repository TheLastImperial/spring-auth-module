package com.thelastimperial.auth.admin.config.security.customizers;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class AdminAuthorizeCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry t) {
        t
            .requestMatchers(HttpMethod.GET, "/admin/users")
                .hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/admin/users/lock-unlock/{username}")
                .hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/admin/users/show/{username}")
                .hasRole("ADMIN");
    }

}
