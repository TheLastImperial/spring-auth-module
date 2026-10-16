package com.thelastimperial.auth.admin.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.thelastimperial.auth.admin.handlers.UserHandler;
import com.thelastimperial.auth.admin.services.impl.LockUnlockAuditServiceImpl;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserActionRepository;
import com.thelastimperial.auth.domain.repositories.UserAuditRepository;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.entities.AuditWrapper;
import com.thelastimperial.utils.services.AuditService;
import com.thelastimperial.utils.services.UsernameService;

@AutoConfiguration
public class AdminAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditService<AuditWrapper<UserEntity>> lockUnlockAuditServiceImpl(
        UserAuditRepository userAuditRepository, UserActionRepository userActionRepository
    ) {
        return new LockUnlockAuditServiceImpl(userAuditRepository, userActionRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserHandler userHandler(
        UserRepository userRepository, UsernameService usernameService,
        AuditService<AuditWrapper<UserEntity>> lockUnlockAuditServiceImpl
    ){
        return new UserHandler(userRepository, usernameService, lockUnlockAuditServiceImpl);
    }
}
