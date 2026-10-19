package com.thelastimperial.auth.authn.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

import com.thelastimperial.auth.authn.services.CredentialRecordService;
import com.thelastimperial.auth.authn.services.CredentialRecordServiceImpl;

/**
 *
 * WebAuthnServiceAutoConfiguration Autoconfiguration of services.
*/
@AutoConfiguration
@AutoConfigureAfter(WebAuthnAutoConfiguration.class)
public class WebAuthnServiceAutoConfiguration {

    /**
     * CredentialRecordService manage CredentialRecords
     * @param userEntityRepository
     * @param userCredentialRepository
     * @return CredentialRecordService
    */
    @Bean
    @ConditionalOnMissingBean
    public CredentialRecordService credentialRecordService(
        PublicKeyCredentialUserEntityRepository userEntityRepository,
        UserCredentialRepository userCredentialRepository
    ) {
        return new CredentialRecordServiceImpl(userEntityRepository, userCredentialRepository);
    }
}
