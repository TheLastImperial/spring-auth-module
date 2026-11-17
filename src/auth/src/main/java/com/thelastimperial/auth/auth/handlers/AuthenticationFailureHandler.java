package com.thelastimperial.auth.auth.handlers;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;
import com.thelastimperial.utils.services.RateLimitService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * AuthenticationFailureHandler manage the authentication failures.
 * Exceptions hadled:
 *  CredentialsExpiredException : Redirect to create a new password.
 *  BadCredentialsException     : Implements Token Bucket algorithm.
 *  UsernameNotFoundException   : Implements Token Bucket algorithm.
*/
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final RecoveryService recoveryService;
    private final RateLimitService loginRateLimitService;
    private String prefixId = "loginRateLimit::";

    public AuthenticationFailureHandler(RecoveryService recoveryService,
        RateLimitService loginRateLimitService) {
        this.recoveryService = recoveryService;
        this.loginRateLimitService = loginRateLimitService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String redirectUrl = "/login?error=true";
        String username = exception.getAuthenticationRequest().getName();

        if(exception instanceof CredentialsExpiredException){
            UserRecoveryEntity recovery = recoveryService.generate(username);
            redirectUrl = "/new-password/" + recovery.getId();
        } else if(
            exception instanceof BadCredentialsException ||
            exception instanceof UsernameNotFoundException
        ) {
            String id = prefixId + username;
            if(loginRateLimitService.consume(id)){
                redirectUrl = "/login?error=true";
            } else {
                redirectUrl = "/429.html";
            }
        }

        setDefaultFailureUrl(redirectUrl);
        super.onAuthenticationFailure(request, response, exception);
    }
}
