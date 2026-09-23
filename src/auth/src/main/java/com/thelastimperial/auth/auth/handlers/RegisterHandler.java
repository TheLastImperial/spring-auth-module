package com.thelastimperial.auth.auth.handlers;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.auth.services.NotificationService;
import com.thelastimperial.auth.auth.services.RegisterInvitationService;
import com.thelastimperial.auth.auth.services.RegisterService;
import com.thelastimperial.auth.domain.entities.UserActivationEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserInvitationEntity;
import com.thelastimperial.auth.domain.repositories.UserActivationRepository;

import lombok.extern.slf4j.Slf4j;
/**
 *
 * HandleRegisterServiceImpl Implements logic to manage all the register process.
*/
@Service
@Slf4j
public class RegisterHandler {
    private final RegisterService registerService;
    private final RegisterInvitationService registerInvitationService;
    private final UserActivationRepository userActivationRepository;
    private final NotificationService registerNotificationService;

    private boolean activeNotification;

    public RegisterHandler(
        RegisterService registerService,
        RegisterInvitationService registerInvitationService,
        UserActivationRepository userActivationRepository,
        NotificationService registerNotificationService
    ){
        this.registerService = registerService;
        this.registerInvitationService = registerInvitationService;
        this.userActivationRepository = userActivationRepository;
        this.registerNotificationService = registerNotificationService;
    }

    /**
     * All the register process
     * @param newUser the user to be created.
    */
    public void register(NewUser newUser) {
        Optional<UserInvitationEntity> invitationOpt = getInvitation(newUser);
        UserEntity user = registerService.register(newUser);
        UserActivationEntity toSaveAct = UserActivationEntity.builder()
            .user(user)
            .build();
        UserActivationEntity activation = userActivationRepository.save(toSaveAct);
        saveInvitation(invitationOpt, user);
        sendNotification(activation);
    }
    /**
     * Get invitation if user request have one.
     * @param newUser Request user to be created.
     * @return Optinal UserInvitationEntity
    */
    public Optional<UserInvitationEntity> getInvitation(NewUser newUser){
        String invitationId = newUser.getInvitation();
        boolean isInvitation = invitationId != null;
        Optional<UserInvitationEntity> invitation = Optional.empty();
        if(isInvitation){
            invitation = registerInvitationService
                .getInvitation(invitationId);
            if(invitation.isPresent()){
                if(invitation.get().isUsed()) {
                    log.info("The invitation is used.");
                    invitation = Optional.empty();
                }
                if(invitation.get().getExpiredAt().isBefore(Instant.now())){
                    log.info("The invitation is expired.");
                    log.info("Invitation limit date: {}", invitation.get().getExpiredAt());
                    log.info("Date: {}", Instant.now());
                    invitation = Optional.empty();
                }
            } else {
                throw new RuntimeException("Not invitation id");
            }
        }

        if(isInvitation){
            return invitation;
        }else{
            return Optional.empty();
        }
    }

    /**
     * Save the invitation if exists.
     * @param invitationOpt Optional UserInvitationEntity
     * @param user UserEntity
    */
    public void saveInvitation(Optional<UserInvitationEntity> invitationOpt, UserEntity user){
        invitationOpt.ifPresent(invitation -> {
            registerInvitationService.saveGuest(invitation.getId().toString(), user);
        });
    }
    /**
     * Send notifications
     * @param activation data to send notification.
    */
    public void sendNotification(UserActivationEntity activation){
        if(isActiveNotification()){
            registerNotificationService.send(activation);
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
