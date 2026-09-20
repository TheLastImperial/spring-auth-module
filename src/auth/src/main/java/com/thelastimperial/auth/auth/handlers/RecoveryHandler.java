package com.thelastimperial.auth.auth.handlers;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RecoveryHandler {
    private final RecoveryService recoveryService;
    private final NotificationService recoveryNotificationService;

    public void generate(Recovery recovery){
        UserRecoveryEntity userRecovery = recoveryService.generate(recovery.getUsername());
        recoveryNotificationService.send(userRecovery);
    }
}
