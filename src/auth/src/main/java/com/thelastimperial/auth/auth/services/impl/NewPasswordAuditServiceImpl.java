package com.thelastimperial.auth.auth.services.impl;

import java.util.Optional;

import com.thelastimperial.auth.domain.entities.UserActionEntity;
import com.thelastimperial.auth.domain.entities.UserAuditEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActionRepository;
import com.thelastimperial.auth.domain.repositories.UserAuditRepository;
import com.thelastimperial.utils.services.AuditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class NewPasswordAuditServiceImpl implements AuditService<UserEntity> {
    private final UserAuditRepository userAuditRepository;
    private final UserActionRepository userActionRepository;
    private final String ACTIVATION_ID = "RESTART_CREDENTIALS";

    @Override
    public void save(UserEntity user) {
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
        log.debug("Audit to user: " + user.getId());
        userAuditRepository.save(toSave);
    }

}
