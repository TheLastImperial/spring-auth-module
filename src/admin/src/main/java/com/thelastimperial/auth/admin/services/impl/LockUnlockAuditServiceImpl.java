package com.thelastimperial.auth.admin.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.domain.entities.UserActionEntity;
import com.thelastimperial.auth.domain.entities.UserAuditEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActionRepository;
import com.thelastimperial.auth.domain.repositories.UserAuditRepository;
import com.thelastimperial.utils.entities.AuditWrapper;
import com.thelastimperial.utils.services.AuditService;

@Service
public class LockUnlockAuditServiceImpl implements  AuditService<AuditWrapper<UserEntity>> {
    private final UserAuditRepository userAuditRepository;
    private final UserActionRepository userActionRepository;
    private String ACTIVATION_ID = "ACCOUNT_LOCKED|ACCOUNT_UNLOCKED";

    public LockUnlockAuditServiceImpl(
        UserAuditRepository userAuditRepository, UserActionRepository userActionRepository
    ) {
        this.userAuditRepository = userAuditRepository;
        this.userActionRepository = userActionRepository;
    }

    @Override
    public void save(AuditWrapper<UserEntity> t) {
        boolean unlock = t.getUser().isAccountNonLocked();
        if(unlock) {
            ACTIVATION_ID = "ACCOUNT_UNLOCKED";
        }else{
            ACTIVATION_ID = "ACCOUNT_LOCKED";
        }
        Optional<UserActionEntity> actionOpt = userActionRepository.findById(ACTIVATION_ID);
        if(actionOpt.isEmpty()){
            throw new RuntimeException("There must be the action ID: " + ACTIVATION_ID);
        }
        UserActionEntity action = actionOpt.get();
        UserAuditEntity audit = UserAuditEntity.builder()
            .userId(t.getUser().getId())
            .updatedBy(UUID.fromString(t.getPrincipal().getName()))
            .action(action)
            .comment(t.getComment())
            .build();
        userAuditRepository.save(audit);
    }

}
