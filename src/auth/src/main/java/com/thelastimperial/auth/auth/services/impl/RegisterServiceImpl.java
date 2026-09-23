package com.thelastimperial.auth.auth.services.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.auth.services.DefaultUserRoleService;
import com.thelastimperial.auth.auth.services.RegisterService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserRoleEntity;
import com.thelastimperial.auth.domain.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;
/**
 *
 * RegisterServiceImpl implements logic to register new user.
*/
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DefaultUserRoleService defaultUserRoleService;

    public RegisterServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
        DefaultUserRoleService defaultUserRoleService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.defaultUserRoleService = defaultUserRoleService;
    }

    @Override
    public UserEntity register(NewUser newUser) {
        List<UserRoleEntity> roles = defaultUserRoleService.getDefaultRoles();
        UserEntity toSave = UserEntity.builder()
            .username(newUser.getUsername())
            .password(passwordEncoder.encode(newUser.getPassword()))
            .roles(roles)
            .enabled(false)
            .build();
        UserEntity user = userRepository.save(toSave);
        return user;
    }

}
