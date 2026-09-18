package com.thelastimperial.auth.oauthserver.services.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserRegisteredClientEntity;
import com.thelastimperial.auth.domain.repositories.UserRegisteredClientRepository;
import com.thelastimperial.auth.domain.responses.OAuthClient;
import com.thelastimperial.auth.oauthserver.config.properties.OAuthServerProperty;
import com.thelastimperial.auth.oauthserver.controllers.request.NewRegisteredClient;
import com.thelastimperial.auth.oauthserver.services.OAuthClientService;
import com.thelastimperial.utils.crypto.SecureRandomString;
import com.thelastimperial.utils.services.UsernameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class OAuthClientServiceImpl implements  OAuthClientService {
    private final RegisteredClientRepository registeredClientRepository;
    private final UserRegisteredClientRepository userRegisteredClientRepository;
    private final UsernameService usernameService;
    private final PasswordEncoder passwordEncoder;
    private final OAuthServerProperty oAuthServerProperty;

    @Override
    public OAuthClient save(NewRegisteredClient newRegisteredClient, Principal principal) {
        String id = UUID.randomUUID().toString();
        SecureRandomString srString = SecureRandomString.getInstance();
        String clientId = srString.generate(
            oAuthServerProperty.getNewclient().getClientsize(), true, true, false
        );
        String clientSecret = srString.generate(oAuthServerProperty.getNewclient().getSecretSize());
        RegisteredClient registeredClient = RegisteredClient
            .withId(id)
            .clientId(clientId)
            .clientSecret(passwordEncoder.encode(clientSecret))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri(newRegisteredClient.getRedirectUris())
            .postLogoutRedirectUri(newRegisteredClient.getPostLogoutRedirectUris())
            .clientSettings(
                ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build()
            )
            .clientSecretExpiresAt(
                LocalDateTime.now().plusDays(
                    oAuthServerProperty.getNewclient().getExpiresDays()
                )
                .atZone(ZoneId.of(oAuthServerProperty.getNewclient().getTz()))
                .toInstant()
            )
            .scope(oAuthServerProperty.getNewclient().getScopes())
            .build();

        Optional<?> userOpt = usernameService.findByUsername(principal.getName());
        if(userOpt.isEmpty()){
            log.error("User dont exists.");
            return null;
        }
        UserRegisteredClientEntity userRegisteredClient = UserRegisteredClientEntity.builder()
            .registeredClientId(id)
            .user((UserEntity)userOpt.get())
            .build();
        registeredClientRepository.save(registeredClient);
        userRegisteredClientRepository.save(userRegisteredClient);
        OAuthClient oAuthClient = OAuthClient.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectUris(newRegisteredClient.getRedirectUris())
            .postLogoutRedirectUris(newRegisteredClient.getPostLogoutRedirectUris())
            .clientSecretExpiresAt(LocalDateTime.now().plusDays(
                oAuthServerProperty.getNewclient().getExpiresDays()
            ))
            .build();
        return oAuthClient;
    }

    @Override
    public List<OAuthClient> getClients(Principal principal) {
        return userRegisteredClientRepository.findClientsByUser(principal.getName());
    }

    @Override
    public Page<OAuthClient> getClients(Principal principal, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRegisteredClientRepository.findClientsByUser(principal.getName(), pageable);
    }

}
