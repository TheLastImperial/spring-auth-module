package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;

/**
 *
 * RegisterService interface to register a new user.
*/
public interface RegisterService {
    /**
     * Register a new user.
     * @param newUser Data of the new user.
    */
    public void register(NewUser newUser);
}
