package com.thelastimperial.auth.oauthserver.autoconfiguration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.thelastimperial.auth.oauthserver.config.properties.OAuthServerProperty;
import com.thelastimperial.utils.crypto.JWKUtils;

import lombok.extern.slf4j.Slf4j;

@AutoConfiguration
@Slf4j
public class OAuthServerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public OAuthServerProperty oAuthServerProperty() {
        return new OAuthServerProperty();
    }

	@Bean
    @ConditionalOnMissingBean(name="authorizationServerSettings")
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings
			.builder()
			.build();
	}
	@Bean
    @ConditionalOnMissingBean(name="jwkSource")
	public JWKSource<SecurityContext> jwkSource(List<JWK> jwks) {
		JWKSet jwkSet = new JWKSet(jwks);
		return new ImmutableJWKSet<>(jwkSet);
	}
	@Bean
    @ConditionalOnMissingBean(name="jwtDecoder")
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}
    @Bean
    @ConditionalOnMissingBean(name="getOAuth2RsaKey")
    public JWK getOAuth2RsaKey(OAuthServerProperty oAuthServerProperty) throws Exception {
        log.debug("JWK ID: {}", oAuthServerProperty.getJwk().getId());

        return new RSAKey
            .Builder(
                (RSAPublicKey)JWKUtils.getPublicKey(
                    oAuthServerProperty.getJwk().getPublicKey()
                )
            )
            .privateKey(
                (RSAPrivateKey)JWKUtils.getPrivateKey(
                    oAuthServerProperty.getJwk().getPrivateKey()
                )
            )
            .keyID(oAuthServerProperty.getJwk().getId())
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(name = "getOAuth2JwtEncoder")
    public JwtEncoder getOAuth2JwtEncoder(
        JWKSource<SecurityContext> jwks, OAuthServerProperty oAuthServerProperty
    ){
        NimbusJwtEncoder jwtEncoder = new NimbusJwtEncoder(jwks);
        jwtEncoder.setJwkSelector(jwk -> jwk
            .stream()
            .filter(j -> j.getKeyID().equals(oAuthServerProperty.getJwk().getId()))
            .findFirst()
            .get()
        );
        return jwtEncoder;
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcRegisteredClientRepository getRegisteredClientRepository(
        JdbcOperations jdbcOperations
    ){
        return new JdbcRegisteredClientRepository(jdbcOperations);
    }

}
