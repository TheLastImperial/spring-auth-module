package com.thelastimperial.auth.auth.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.UUIDUtils;
import com.thelastimperial.utils.services.UsernameService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UsernameService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        log.debug("User to search: {}", username);

        Optional<UserEntity> user = Optional.empty();
        if(UUIDUtils.isValid(username)) {
            user = userRepository.findById(UUID.fromString(username));
        } else {
            user = userRepository.findByUsername(username);
        }
        log.debug("UserFound: {}", user.isPresent());
        return user;
    }
}
