package com.thelastimperial.auth.auth.services.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.auth.services.HandleRegisterService;
import com.thelastimperial.auth.auth.services.RegisterInvitationService;
import com.thelastimperial.auth.auth.services.RegisterService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserInvitationEntity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 *
 * HandleRegisterServiceImpl Implements logic to manage all the register process.
*/
@AllArgsConstructor
@Service
@Slf4j
public class HandleRegisterServiceImpl implements  HandleRegisterService {
    private final RegisterService registerService;
    private final RegisterInvitationService registerInvitationService;

    @Override
    public void register(NewUser newUser) {
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
        UserEntity user = registerService.register(newUser);
        if(isInvitation)
            registerInvitationService.saveGuest(invitationId, user);
    }

}
