package com.thelastimperial.auth.oauthserver.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "com.thelastimperial.auth.oauthserver")
@Data
public class OAuthServerProperty {
    @NestedConfigurationProperty
    private JWKProperty jwk;
}
