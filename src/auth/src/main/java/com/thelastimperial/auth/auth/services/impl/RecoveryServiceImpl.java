package com.thelastimperial.auth.auth.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;
import com.thelastimperial.auth.domain.repositories.UserRecoveryRepository;
import com.thelastimperial.utils.services.UsernameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * RecoveryServiceImpl implements logic to recovery account.
*/
@AllArgsConstructor
@Slf4j
public class RecoveryServiceImpl implements RecoveryService {
    private final UserRecoveryRepository userRecoveryRepository;
    private final UsernameService usernameService;

    @Override
    public UserRecoveryEntity generate(String username) {
        Optional<?> userOpt = usernameService.findByUsername(username);
        UserRecoveryEntity toSave;

        if(userOpt.isEmpty()) {
            log.debug("User doesn't exists: {}", username);
            toSave = UserRecoveryEntity.builder()
                .username(username)
                .validUntilAt(LocalDateTime.now())
                .isUsed(true)
                .build();
        } else {
            UserEntity user = (UserEntity)userOpt.get();
            toSave = UserRecoveryEntity.builder()
                .user(user)
                .username(username)
                .validUntilAt(LocalDateTime.now().plusMinutes(15))
                .isUsed(false)
                .build();
        }
        return userRecoveryRepository.save(toSave);
    }

}
