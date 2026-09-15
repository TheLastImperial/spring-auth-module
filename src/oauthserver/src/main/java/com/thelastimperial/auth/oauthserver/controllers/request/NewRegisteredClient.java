package com.thelastimperial.auth.oauthserver.controllers.request;

import groovy.transform.builder.Builder;
import lombok.Data;

@Data
@Builder
public class NewRegisteredClient {
    private String redirectUris;
    private String postLogoutRedirectUris;
}
