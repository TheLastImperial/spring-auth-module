package com.thelastimperial.auth.auth.handlers;

import java.io.IOException;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final RecoveryService recoveryService;

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    ) throws IOException, ServletException {
        String username = exception.getAuthenticationRequest().getName();
        UserRecoveryEntity recovery = recoveryService.generate(username);
        if(exception instanceof CredentialsExpiredException ){
            setDefaultFailureUrl("/auth/new-password/" + recovery.getId());
        } else{
            setDefaultFailureUrl("/auth/login?error=true");
        }
        super.onAuthenticationFailure(request, response, exception);
    }

}
