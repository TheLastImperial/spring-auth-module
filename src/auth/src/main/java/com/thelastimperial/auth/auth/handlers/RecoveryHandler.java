package com.thelastimperial.auth.auth.handlers;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.auth.services.RecoveryService;
import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;
/**
 *
 * RecoveryHandler handle the process to recovery an account.
*/
@Service
public class RecoveryHandler {
    private final RecoveryService recoveryService;
    private final NotificationService recoveryNotificationService;
    private boolean activeNotification = true;

    public RecoveryHandler(
        RecoveryService recoveryService,
        NotificationService recoveryNotificationService
    ){
        this.recoveryService = recoveryService;
        this.recoveryNotificationService = recoveryNotificationService;
    }

    /**
     * Generate a new UserRecoveryEntity
     * @param recovery the recovery request
    */
    public void generate(Recovery recovery){
        UserRecoveryEntity userRecovery = recoveryService.generate(recovery.getUsername());
        sendNotification(userRecovery);
    }
    /**
     * Send notifications
     * @param userRecoveryEntity data to send notification.
    */
    public void sendNotification(UserRecoveryEntity userRecoveryEntity) {
        if(isActiveNotification() && !userRecoveryEntity.isUsed()) {
            recoveryNotificationService.send(userRecoveryEntity);
        }
    }
    /**
     * Know if the notification is active.
     * @return if the notification is active
    */
    public boolean isActiveNotification() {
        return activeNotification;
    }
    /**
     * Activate or desactivate notifications.
     * @param activeNotification set activation value
    */
    public void setActiveNotification(boolean activeNotification) {
        this.activeNotification = activeNotification;
    }
}
