package com.thelastimperial.auth.oauthserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;

import com.thelastimperial.auth.auth.config.security.customizers.AuthAuthorizeCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.FormLoginCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.LogoutCustomizer;
import com.thelastimperial.auth.auth.config.security.customizers.RememberMeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnAuthorizeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnCustomizer;
import com.thelastimperial.auth.oauthserver.config.security.customizers.OAuthServerAuthorizeCustomizer;
import com.thelastimperial.auth.oauthserver.config.security.customizers.OAuthServerExceptionHandlerCustomizer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class OAuthServerConfig {
    private WebAuthnAuthorizeCustomizer webAuthnAuthorizeCustomizer;
    private AuthAuthorizeCustomizer authAuthorizeCustomizer;
    private OAuthServerAuthorizeCustomizer oAuthServerAuthorizeCustomizer;

    private WebAuthnCustomizer webAuthnCustomizer;
    private FormLoginCustomizer formLoginCustomizer;
    private RememberMeCustomizer rememberMeCustomizer;
    private LogoutCustomizer logoutCustomizer;
    private OAuthServerExceptionHandlerCustomizer oAuthServerExceptionHandlerCustomizer;

    @Bean
    @Order(1)
    @ConditionalOnMissingBean
    public SecurityFilterChain oAuth2ServerFilterChain(HttpSecurity http) throws Exception {
		http
			.oauth2AuthorizationServer((authorizationServer) -> {
				http.securityMatcher(authorizationServer.getEndpointsMatcher());
				authorizationServer
					.oidc(Customizer.withDefaults()) // Enable OpenID Connect 1.0
					.clientRegistrationEndpoint(Customizer.withDefaults());
			})
			.authorizeHttpRequests((authorize) ->
				authorize
					.anyRequest().authenticated()
			)
			// Redirect to the login page when not authenticated from the
			// authorization endpoint
			.exceptionHandling(
                oAuthServerExceptionHandlerCustomizer
			);
        return http.build();
    }
    @Bean
    @Order(2)
    @ConditionalOnMissingBean
    public SecurityFilterChain oAuth2FilterChain(
        HttpSecurity http, RememberMeServices rememberMeServices
    ) throws Exception {

        http
        .authorizeHttpRequests(auth -> {
            authAuthorizeCustomizer.customize(auth);
            webAuthnAuthorizeCustomizer.customize(auth);
            oAuthServerAuthorizeCustomizer.customize(auth);
            auth.anyRequest().authenticated();
        })
        .formLogin( login -> {
            formLoginCustomizer.customize(login);
            login.defaultSuccessUrl("/", false);
        })
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
