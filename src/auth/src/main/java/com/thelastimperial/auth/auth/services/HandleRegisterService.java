package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;

/**
 *
 * HandleRegisterService handle all the process to register a new user.
*/
public interface HandleRegisterService {
    /**
     * Do the process for a new user.
     * @param newUser Attributes to create the new user.
    */
    public void register(NewUser newUser);
}
