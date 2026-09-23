package com.thelastimperial.auth.admin.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.admin.controllers.AdminUserController;
import com.thelastimperial.auth.admin.controllers.HomeController;
import com.thelastimperial.auth.admin.handlers.UserHandler;
import com.thelastimperial.auth.domain.repositories.UserRepository;

@AutoConfiguration
@AutoConfigureAfter(AdminAutoConfiguration.class)
public class AdminControllerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name="adminUserController")
    public AdminUserController adminUserController(
        UserRepository userRepository, UserHandler userHandler
    ){
        return new AdminUserController(userRepository, userHandler);
    }

    @Bean
    @ConditionalOnMissingBean(name="homeController")
    public HomeController homeController(){
        return new HomeController();
    }
}
