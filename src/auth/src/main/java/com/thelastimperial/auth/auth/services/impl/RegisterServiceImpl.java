package com.thelastimperial.auth.auth.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.auth.services.RegisterService;
import com.thelastimperial.auth.domain.entities.UserActivationEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActivationRepository;
import com.thelastimperial.auth.domain.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserActivationRepository userActivationRepository;
    private final NotificationService registerNotificationService;
    
    public RegisterServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
        UserActivationRepository userActivationRepository,
        NotificationService registerNotificationService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userActivationRepository = userActivationRepository;
        this.registerNotificationService = registerNotificationService;
    }

    @Override
    public void register(NewUser newUser) {
        UserEntity toSave = UserEntity.builder()
            .username(newUser.getUsername())
            .password(passwordEncoder.encode(newUser.getPassword()))
            .enabled(false)
            .build();
        UserEntity user = userRepository.save(toSave);
        UserActivationEntity toSaveAct = UserActivationEntity.builder()
            .user(user)
            .build();
        UserActivationEntity activation = userActivationRepository.save(toSaveAct);
        registerNotificationService.send(activation);
    }

}
