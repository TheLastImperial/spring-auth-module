package com.thelastimperial.auth.authn.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.WebAuthnConfigurer;

import com.thelastimperial.auth.authn.config.WebAuthnProperty;

import lombok.AllArgsConstructor;

/**
 *
 * WebAuthnCustomizer set the default WebAuthn configuration.
*/
@AllArgsConstructor
public class WebAuthnCustomizer implements Customizer<WebAuthnConfigurer<HttpSecurity>> {
    private final WebAuthnProperty webAuthnProperty;

    @Override
    public void customize(WebAuthnConfigurer<HttpSecurity> t) {
        t
            .rpName(webAuthnProperty.getRpName())
            .rpId(webAuthnProperty.getRpId())
            .allowedOrigins(webAuthnProperty.getAllowedOrigins())
            .disableDefaultRegistrationPage(true);
    }

}
