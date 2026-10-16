package com.thelastimperial.auth.auth.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

/**
 *
 * LogoutCustomizer default logout customizer to Auth module
*/
public class LogoutCustomizer implements Customizer<LogoutConfigurer<HttpSecurity>> {

    @Override
    public void customize(LogoutConfigurer<HttpSecurity> t) {
        t
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/auth/login")
            .permitAll();
    }

}
