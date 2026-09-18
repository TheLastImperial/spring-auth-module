package com.thelastimperial.auth.oauthserver.config.properties;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ClientProperty {
    private String tz;
    private int expiresDays;
    private int secretSize;
    private int clientsize;
    private String scopes;
}
