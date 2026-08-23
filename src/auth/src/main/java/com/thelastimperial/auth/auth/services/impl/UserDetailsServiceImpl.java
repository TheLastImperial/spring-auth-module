package com.thelastimperial.auth.auth.services.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.utils.services.UsernameService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsernameService usernameService;

    public UserDetailsServiceImpl(UsernameService usernameService){
        this.usernameService = usernameService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<?> userOpt = usernameService.findByUsername(username);

        if(userOpt.isEmpty()){
            log.debug("User not found: {}", username);
            throw new UsernameNotFoundException(username);
        }

        UserEntity user = (UserEntity)userOpt.get();
        Set<GrantedAuthority> authorities = user
            .getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toSet());
        
        User userDetails =  new User(
            user.getId().toString(),
            user.getPassword(),
            user.isEnabled(),
            user.isAccountNonExpired(),
            user.isCredentialsNonExpired(),
            user.isAccountNonLocked(),
            authorities
        );

        log.debug("UserDetails: {}", userDetails);
        return userDetails;
    }
    
}
