package com.thelastimperial.auth.authn.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
/**
 *
 * WebAuthnAuthorizeCustomizer Set the minimal requestMatchers required to use the Authn module.
*/
public class WebAuthnAuthorizeCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry t
    ) {
        t
            .requestMatchers("/webauthn/webauthn.js").permitAll()
            .requestMatchers("/webauthn/**").authenticated();
    }

}
