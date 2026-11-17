package com.thelastimperial.auth.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.utils.services.RateLimitService;
import com.thelastimperial.utils.services.ratelimits.LocalRateLimitService;

@AutoConfiguration
public class AuthRateLimitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "loginRateLimitService")
    public RateLimitService loginRateLimitService() {
        return new LocalRateLimitService();
    }
}
