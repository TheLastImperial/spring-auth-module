package com.thelastimperial.auth.auth.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.thelastimperial.auth.auth.controllers.requests.NewPassword;
import com.thelastimperial.auth.auth.services.NewPasswordService;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;
import com.thelastimperial.auth.domain.repositories.UserRecoveryRepository;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.UUIDUtils;
import com.thelastimperial.utils.services.AuditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class NewPasswordServiceImpl implements NewPasswordService {
    private final UserRecoveryRepository userRecoveryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService<UserEntity> newPasswordAuditServiceImpl;
    private final NotificationService newPasswordNotificationService;

    @Override
    public void update(NewPassword newPassword) {
        log.debug("Creating new password with ID: '{}'", newPassword.getToken());
        if(!UUIDUtils.isValid(newPassword.getToken())){
            log.debug("Bad Recovery ID pattern: '{}'", newPassword.getToken());
            return;
        }
        Optional<UserRecoveryEntity> recoveryOpt = userRecoveryRepository
            .findById(UUID.fromString(newPassword.getToken()));
        if(recoveryOpt.isEmpty()){
            log.debug("Recovery ID doesn't exists: '{}'", newPassword.getToken());
            return;
        }

        UserRecoveryEntity recovery = recoveryOpt.get();
        if(recovery.getValidUntilAt().isBefore(LocalDateTime.now())){
            log.debug("The recovery is expired: '{}'", recovery.getId());
            return;
        }
        UserEntity user = recovery.getUser();
        log.debug("Is updating data.");

        recovery.setLastPassword(user.getPassword());
        recovery.setUsed(true);
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        log.debug("New user password set: " + user.getId());

        userRepository.save(user);
        userRecoveryRepository.save(recovery);
        newPasswordAuditServiceImpl.save(user);
        newPasswordNotificationService.send(user);
    }
    
}
