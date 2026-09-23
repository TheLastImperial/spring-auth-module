package com.thelastimperial.auth.admin.handlers;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.domain.entities.UserAuditEntity;
import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.repositories.UserRepository;
import com.thelastimperial.utils.entities.AuditWrapper;
import com.thelastimperial.utils.services.AuditService;
import com.thelastimperial.utils.services.UsernameService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserHandler {
    private final UserRepository userRepository;
    private final UsernameService usernameService;
    private final AuditService<AuditWrapper<UserEntity>> lockUnlockAuditServiceImpl;

    public void lockUnlock(String username, Principal principal){
        Optional<?> userOpt = usernameService.findByUsername(username);
        userOpt.ifPresent(userT -> {
            UserEntity user = (UserEntity) userT;
            user.setAccountNonLocked(
                !user.isAccountNonLocked()
            );
            userRepository.save(user);
            lockUnlockAuditServiceImpl.save(
                AuditWrapper.<UserEntity>builder()
                    .user(user)
                    .principal(principal)
                    .build()
            );
        });
    }
    public UserEntity getUser(String username){
        return (UserEntity)usernameService.findByUsername(username).get();
    }
}
