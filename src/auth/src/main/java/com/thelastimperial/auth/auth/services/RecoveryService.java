package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.domain.entities.UserRecoveryEntity;
/**
 *
 * RecoveryService interface to create recovery request.
*/
public interface RecoveryService {
    /**
     * Generate recovery credentials.
     * @param username to create recovery.
     * @return UserRecoveryEntity
    */
    public UserRecoveryEntity generate(String username);
}
