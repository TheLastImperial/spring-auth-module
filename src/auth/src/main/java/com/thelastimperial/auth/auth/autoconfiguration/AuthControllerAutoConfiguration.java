package com.thelastimperial.auth.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.auth.controllers.ActivationController;
import com.thelastimperial.auth.auth.controllers.HomeController;
import com.thelastimperial.auth.auth.controllers.LoginController;
import com.thelastimperial.auth.auth.controllers.NewPasswordController;
import com.thelastimperial.auth.auth.controllers.RecoveryController;
import com.thelastimperial.auth.auth.controllers.RegisterController;
import com.thelastimperial.auth.auth.services.ActivationService;
import com.thelastimperial.auth.auth.services.NewPasswordService;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.auth.services.RegisterService;

@AutoConfiguration
@AutoConfigureAfter(AuthAutoConfiguration.class)
public class AuthControllerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ActivationController activationController(ActivationService activationService) {
        return new ActivationController(activationService);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginController loginController() {
        return new LoginController();
    }

    @Bean
    @ConditionalOnMissingBean
    public NewPasswordController newPasswordController(NewPasswordService newPasswordService) {
        return new NewPasswordController(newPasswordService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RecoveryController recoveryController(RecoveryService recoveryService){
        return new RecoveryController(recoveryService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisterController registerController(RegisterService registerService) {
        return new RegisterController(registerService);
    }

    @Bean
    @ConditionalOnMissingBean
    public HomeController homeController() {
        return new HomeController();
    }
}
