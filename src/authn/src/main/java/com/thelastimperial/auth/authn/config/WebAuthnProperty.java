package com.thelastimperial.auth.authn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "com.thelastimperial.auth.authn")
@Data
public class WebAuthnProperty {
    private String rpId;
    private String rpName;
    private String[] allowedOrigins;
}
