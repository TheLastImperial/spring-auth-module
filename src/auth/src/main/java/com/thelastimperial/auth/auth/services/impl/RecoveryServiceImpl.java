package com.thelastimperial.auth.auth.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;
import com.thelastimperial.auth.domain.repositories.UserRecoveryRepository;
import com.thelastimperial.utils.services.UsernameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class RecoveryServiceImpl implements RecoveryService {
    private final UserRecoveryRepository userRecoveryRepository;
    private final UsernameService usernameService;
    private final Optional<NotificationService> recoveryNotificationService;

    @Override
    public void generate(Recovery recovery) {
        Optional<?> userOpt = usernameService.findByUsername(recovery.getUsername());
        UserRecoveryEntity toSave;

        if(userOpt.isEmpty()) {
            log.debug("User doesn't exists: {}", recovery.getUsername());
            toSave = UserRecoveryEntity.builder()
                .username(recovery.getUsername())
                .validUntilAt(LocalDateTime.now())
                .isUsed(true)
                .build();
        } else {
            UserEntity user = (UserEntity)userOpt.get();
            toSave = UserRecoveryEntity.builder()
                .user(user)
                .username(recovery.getUsername())
                .validUntilAt(LocalDateTime.now().plusMinutes(15))
                .isUsed(false)
                .build();
        }
        UserRecoveryEntity saved = userRecoveryRepository.save(toSave);

        if(recoveryNotificationService.isPresent()){
            log.debug("Sending notification to recovery.");
            recoveryNotificationService.get().sendNotification(saved);
        }
    }
    
}
