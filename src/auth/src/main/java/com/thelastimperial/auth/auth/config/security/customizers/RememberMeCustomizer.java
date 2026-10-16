package com.thelastimperial.auth.auth.config.security.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.web.authentication.RememberMeServices;

import lombok.AllArgsConstructor;

/**
 *
 * RememberMeCustomizer default remember me to Auth module.
*/
@AllArgsConstructor
public class RememberMeCustomizer implements Customizer<RememberMeConfigurer<HttpSecurity>> {
    private RememberMeServices rememberMeServices;

    @Override
    public void customize(RememberMeConfigurer<HttpSecurity> t) {
        t
            .rememberMeServices(rememberMeServices)
            .rememberMeParameter("remember-me");
    }

}
