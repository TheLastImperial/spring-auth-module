package com.thelastimperial.auth.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thelastimperial.auth.auth.config.filters.LoginRateLimitFilter;
import com.thelastimperial.utils.services.RateLimitService;
import com.thelastimperial.utils.services.ratelimits.LocalRateLimitService;

@AutoConfiguration
public class AuthRateLimitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "rateLimitService")
    public RateLimitService rateLimitService() {
        return new LocalRateLimitService();
    }

    @Bean
    @ConditionalOnMissingBean(name="loginRateLimitFilter")
    public OncePerRequestFilter loginRateLimitFilter(RateLimitService loginRateLimitService) {
        return new LoginRateLimitFilter(loginRateLimitService);
    }
}
