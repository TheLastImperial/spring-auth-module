package com.thelastimperial.auth.oauthclient.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.oauthclient.controllers.HomeController;

@AutoConfiguration
public class OAuthClientControllerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name="homeController")
    public HomeController homeController(){
        return new HomeController();
    }
}
