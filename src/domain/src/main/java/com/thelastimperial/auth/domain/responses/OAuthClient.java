package com.thelastimperial.auth.domain.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class OAuthClient {
    private String redirectUris;
    private String postLogoutRedirectUris;
    private LocalDateTime clientSecretExpiresAt;
    private String clientId;
    private String clientSecret;
}
