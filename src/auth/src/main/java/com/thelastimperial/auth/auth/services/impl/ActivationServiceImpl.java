package com.thelastimperial.auth.auth.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.services.ActivationService;
import com.thelastimperial.auth.auth.services.AuditService;
import com.thelastimperial.auth.domain.entities.UserActivationEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActivationRepository;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.UUIDUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class ActivationServiceImpl implements ActivationService{
    private final UserActivationRepository userActivationRepository;
    private final UserRepository userRepository;
    private final AuditService activationAuditServiceImpl;

    @Override
    public void activate(String tokenId) {
        if(!UUIDUtils.isValid(tokenId)){
            log.error("The token activation ID dont have the right Pattern: {}", tokenId);
            return;
        }
        Optional<UserActivationEntity> activationOpt = userActivationRepository
            .findById(UUID.fromString(tokenId));
        if(activationOpt.isEmpty()){
            log.error("The token activation ID doesn't exists: {}", tokenId);
            return;
        }
        UserActivationEntity activation = activationOpt.get();
        if(activation.isUsed()){
            log.info("The token activation ID is used.",tokenId);
            return;
        }
        UserEntity user = activation.getUser();
        user.setEnabled(true);
        activation.setUsed(true);
        activation.setActivatedAt(LocalDateTime.now());

        
        userRepository.save(user);
        userActivationRepository.save(activation);
        activationAuditServiceImpl.save(user);
    }
    
}
