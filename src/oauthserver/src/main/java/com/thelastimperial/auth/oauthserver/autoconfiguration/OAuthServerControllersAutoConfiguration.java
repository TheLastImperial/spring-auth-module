package com.thelastimperial.auth.oauthserver.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.oauthserver.controllers.OAuthClientController;
import com.thelastimperial.auth.oauthserver.services.OAuthClientService;

@AutoConfiguration
@AutoConfigureAfter(OAuthServerAutoConfiguration.class)
public class OAuthServerControllersAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name="oAuthClientController")
    public OAuthClientController oAuthClientController(OAuthClientService oAuthClientService) {
        return new OAuthClientController(oAuthClientService);
    }
}
