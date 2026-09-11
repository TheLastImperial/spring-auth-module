INSERT INTO oauth2_registered_client (id, client_id, client_id_issued_at, client_secret,
    client_secret_expires_at, client_name, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris,
    scopes,
    client_settings,
    token_settings
)
VALUES ('clienttest', 'test', '2026-05-06 13:28:18.242772',
    '$2a$10$eO6sAdt3WNQscoIA1f.RmuTNTF.ovZseDmr1jRBteIOMmDRSgknlG', NULL, 'clienttest',
    'client_secret_post', 'refresh_token,authorization_code',
    'http://127.0.0.1:8080/login/oauth2/code/clienttest', 'http://127.0.0.1:8080/',
    'openid,profile',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":false}',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration","PT5M"],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration","PT1H"],"settings.token.authorization-code-time-to-live":["java.time.Duration","PT5M"],"settings.token.device-code-time-to-live":["java.time.Duration","PT5M"]}'
);

