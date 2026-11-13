package com.thelastimperial.auth.auth.config.filters;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thelastimperial.utils.services.RateLimitService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * LoginRateLimitFilter Filter to set a rate limit for login requests.
*/
@Slf4j
public class LoginRateLimitFilter extends OncePerRequestFilter {
    private final RequestMatcher requestMatcher;
    private final RateLimitService rateLimitService;
    private String prefixId = "loginRateLimit::";

    public LoginRateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
        requestMatcher = PathPatternRequestMatcher
            .withDefaults()
            .matcher(HttpMethod.POST, "/login");
    }

    public LoginRateLimitFilter(
        RateLimitService rateLimitService, RequestMatcher requestMatcher
    ) {
        this.rateLimitService = rateLimitService;
        this.requestMatcher = requestMatcher;
    }

    public void setIdPrefix(String prefixId) {
        this.prefixId = prefixId;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        if(!requestMatcher.matches(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String username = request.getParameter("username");
        String id = prefixId + username;
        log.debug("Searching id: {}", id);

        if(rateLimitService.consume(id))
            filterChain.doFilter(request, response);
        else {
            response.sendError(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                HttpStatus.TOO_MANY_REQUESTS.name()
            );
        }
    }

}
