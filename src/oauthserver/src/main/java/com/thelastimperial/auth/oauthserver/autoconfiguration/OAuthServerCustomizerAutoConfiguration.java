package com.thelastimperial.auth.oauthserver.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.oauthserver.config.security.customizers.OAuthServerAuthorizeCustomizer;
import com.thelastimperial.auth.oauthserver.config.security.customizers.OAuthServerExceptionHandlerCustomizer;

@AutoConfiguration
@AutoConfigureAfter(OAuthServerControllersAutoConfiguration.class)
public class OAuthServerCustomizerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OAuthServerAuthorizeCustomizer oAuthServerAuthorizeCustomizer() {
        return new OAuthServerAuthorizeCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuthServerExceptionHandlerCustomizer oAuthServerExceptionHandlerCustomizer() {
        return new OAuthServerExceptionHandlerCustomizer();
    }
}
