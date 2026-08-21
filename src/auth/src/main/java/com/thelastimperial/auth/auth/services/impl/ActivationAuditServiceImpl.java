package com.thelastimperial.auth.auth.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.services.AuditService;
import com.thelastimperial.auth.domain.entities.UserActionEntity;
import com.thelastimperial.auth.domain.entities.UserAuditEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActionRepository;
import com.thelastimperial.auth.domain.repositories.UserAuditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class ActivationAuditServiceImpl implements AuditService {
    private final UserAuditRepository userAuditRepository;
    private final UserActionRepository userActionRepository;
    private final String ACTIVATION_ID = "ACCOUNT_ACTIVATION";

    @Override
    public void save(Object rq) {
        UserEntity user = (UserEntity) rq;
        Optional<UserActionEntity> actionOpt = userActionRepository.findById(ACTIVATION_ID);
        if(actionOpt.isEmpty()){
            throw new RuntimeException("There must be the action ID: " + ACTIVATION_ID);
        }
        UserActionEntity action = actionOpt.get();

        UserAuditEntity toSave = UserAuditEntity.builder()
            .userId(user.getId())
            .updatedBy(user.getId())
            .action(action)
            .build();
        log.info("Audit to save: " + toSave);
        userAuditRepository.save(toSave);
    }
    
}
