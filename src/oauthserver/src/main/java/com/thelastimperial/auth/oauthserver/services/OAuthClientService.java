package com.thelastimperial.auth.oauthserver.services;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.thelastimperial.auth.domain.responses.OAuthClient;
import com.thelastimperial.auth.oauthserver.controllers.request.NewRegisteredClient;

public interface OAuthClientService {
    public OAuthClient save(NewRegisteredClient newRegisteredClient, Principal principal);
    public List<OAuthClient> getClients(Principal principal);
    public Page<OAuthClient> getClients(Principal principal, int page, int size);
}
