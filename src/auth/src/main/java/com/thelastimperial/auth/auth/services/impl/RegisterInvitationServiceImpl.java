package com.thelastimperial.auth.auth.services.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.services.RegisterInvitationService;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserInvitationEntity;
import com.thelastimperial.auth.domain.repositories.UserInvitationRepository;
import com.thelastimperial.utils.UUIDUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 *
 * RegisterInvitationServiceImpl process registration invitation.
*/
@AllArgsConstructor
@Service
@Slf4j
public class RegisterInvitationServiceImpl implements RegisterInvitationService {
    private final UserInvitationRepository userInvitationRepository;

    @Override
    public Optional<UserInvitationEntity> getInvitation(String id) {
        if(!UUIDUtils.isValid(id)){
            log.error("UUID bad format: {}", id);
            return Optional.empty();
        }

        UUID uuid = UUID.fromString(id);
        return userInvitationRepository.findById(uuid);
    }

    @Override
    public void saveGuest(String id, UserEntity guest) {
        Optional<UserInvitationEntity> invitationOpt = getInvitation(id);
        invitationOpt.ifPresent(invitation -> {
            invitation.setGuest(guest);
            invitation.setUsed(true);
            userInvitationRepository.save(invitation);
        });
    }

    @Override
    public void save(UserEntity host, String username) {
        UserInvitationEntity userInvToSave = UserInvitationEntity.builder()
            .host(host)
            .username(username)
            .isUsed(false)
            .expiredAt(Instant.now().plus(Duration.ofDays(5)))
            .build();
        userInvitationRepository.save(userInvToSave);
    }

}
