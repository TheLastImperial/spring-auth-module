package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;
import com.thelastimperial.auth.domain.entities.UserEntity;

/**
 *
 * RegisterService interface to register a new user.
*/
public interface RegisterService {
    /**
     * Register a new user.
     * @param newUser Data of the new user.
     * @return the user created.
    */
    public UserEntity register(NewUser newUser);
}
