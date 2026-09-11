package com.thelastimperial.auth.oauthserver.config.properties;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data 
public class JWKProperty {
    private String id;
    private String publicKey;
    private String privateKey;
}
