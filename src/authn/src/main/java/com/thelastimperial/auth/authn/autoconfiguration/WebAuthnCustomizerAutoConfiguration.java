package com.thelastimperial.auth.authn.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.authn.config.WebAuthnProperty;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnAuthorizeCustomizer;
import com.thelastimperial.auth.authn.config.security.customizers.WebAuthnCustomizer;

/**
 *
 * WebAuthnCustomizerAutoConfiguration Autoconfiguration of default customizers.
*/
@AutoConfiguration
@AutoConfigureAfter(WebAuthnServiceAutoConfiguration.class)
public class WebAuthnCustomizerAutoConfiguration {

    /**
     * Default WebAuthCustomizer
     * @param webAuthnProperty
     * @return WebAuthnCustomizer
    */
    @Bean
    @ConditionalOnMissingBean
    public WebAuthnCustomizer webAuthnCustomizer(WebAuthnProperty webAuthnProperty) {
        return new WebAuthnCustomizer(webAuthnProperty);
    }
    /**
     * Default WebAuthnAuthorizeCustomizer.
     * Set the requestMatchers to use the Authn module.
     * @return
    */
    @Bean
    @ConditionalOnMissingBean
    public WebAuthnAuthorizeCustomizer webAuthnAuthorizeCustomizer(){
        return new WebAuthnAuthorizeCustomizer();
    }
}
