package com.thelastimperial.auth.auth.services;
/**
 *
 * ActivationService interface to activate a new account.
*/
public interface ActivationService {
    /**
     * Method to activate the account.
     * @param tokenId ID of the activation register.
    */
    public void activate(String tokenId);
}
