package com.thelastimperial.auth.authn.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.authn.controllers.WebAuthnController;
import com.thelastimperial.auth.authn.services.CredentialRecordService;

/**
 *
 * WebAuthnControllerAutoConfiguration autoconfiguration of Controllers
*/
@AutoConfiguration
@AutoConfigureAfter(WebAuthnServiceAutoConfiguration.class)
public class WebAuthnControllerAutoConfiguration {
    /**
     * Create WebAuthnController
     * @param credentialRecordService
     * @return WebAuthnController
    */
    @Bean
    @ConditionalOnMissingBean(name = "webAuthnController")
    public WebAuthnController webAuthnController(CredentialRecordService credentialRecordService) {
        return new WebAuthnController(credentialRecordService);
    }
}
