package com.thelastimperial.auth.oauthserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
public class OAuthServerConfig {
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
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/auth/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
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
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/").hasRole("USER")
            .requestMatchers("/css/auth/**","/js/auth/**","/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin( login -> login
            .loginPage("/auth/login")
            .failureUrl("/auth/login?error=true")
            .defaultSuccessUrl("/", false)
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
