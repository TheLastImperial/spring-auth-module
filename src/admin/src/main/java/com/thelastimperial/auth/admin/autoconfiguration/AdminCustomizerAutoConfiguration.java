package com.thelastimperial.auth.admin.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.admin.config.security.customizers.AdminAuthorizeCustomizer;

@AutoConfiguration
@AutoConfigureAfter(AdminControllerAutoConfiguration.class)
public class AdminCustomizerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AdminAuthorizeCustomizer adminAuthorizeCustomizer() {
        return new AdminAuthorizeCustomizer();
    }
}
