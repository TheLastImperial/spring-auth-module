package com.thelastimperial.auth.authn.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.web.webauthn.management.JdbcPublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.JdbcUserCredentialRepository;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

import com.thelastimperial.auth.authn.config.WebAuthnProperty;
/**
 *
 * WebAuthnAutoConfiguration Autoconfiguration of WebAuthn.
*/
@AutoConfiguration
public class WebAuthnAutoConfiguration {
    /**
     * Create UserCredentialRepository bean repository.
     * @param jdbcOperations
     * @return UserCredentialRepository
    */
    @Bean
    @ConditionalOnMissingBean
    public UserCredentialRepository jdbcUserCredentialRepository(
        JdbcOperations jdbcOperations
    ){
        return new JdbcUserCredentialRepository(jdbcOperations);
    }

    /**
     * Create PublicKeyCredentialUserEntityRepository bean repository.
     * @param jdbcOperations
     * @return PublicKeyCredentialUserEntityRepository
    */
    @Bean
    @ConditionalOnMissingBean
    public PublicKeyCredentialUserEntityRepository jdbcPublicKeyCredentialUserEntityRepository(
        JdbcOperations jdbcOperations
    ){
        return new JdbcPublicKeyCredentialUserEntityRepository(jdbcOperations);
    }

    /**
     * Web Authn Properties
     * @return WebAuthnProperty
    */
    @Bean
    @ConditionalOnMissingBean
    public WebAuthnProperty webAuthnProperty(){
        return new WebAuthnProperty();
    }
}
