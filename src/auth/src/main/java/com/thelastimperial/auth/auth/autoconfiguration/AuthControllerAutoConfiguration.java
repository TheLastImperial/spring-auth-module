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
import com.thelastimperial.auth.auth.handlers.RecoveryHandler;
import com.thelastimperial.auth.auth.handlers.RegisterHandler;
import com.thelastimperial.auth.auth.services.ActivationService;
import com.thelastimperial.auth.auth.services.NewPasswordService;

@AutoConfiguration
@AutoConfigureAfter(AuthAutoConfiguration.class)
public class AuthControllerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name="activationController")
    public ActivationController activationController(ActivationService activationService) {
        return new ActivationController(activationService);
    }

    @Bean
    @ConditionalOnMissingBean(name="loginController")
    public LoginController loginController() {
        return new LoginController();
    }

    @Bean
    @ConditionalOnMissingBean(name="newPasswordController")
    public NewPasswordController newPasswordController(NewPasswordService newPasswordService) {
        return new NewPasswordController(newPasswordService);
    }

    @Bean
    @ConditionalOnMissingBean(name="recoveryController")
    public RecoveryController recoveryController(RecoveryHandler recoveryHandler){
        return new RecoveryController(recoveryHandler);
    }

    @Bean
    @ConditionalOnMissingBean(name="registerController")
    public RegisterController registerController(RegisterHandler registerHandler) {
        return new RegisterController(registerHandler);
    }

    @Bean
    @ConditionalOnMissingBean(name="homeController")
    public HomeController homeController() {
        return new HomeController();
    }
}
