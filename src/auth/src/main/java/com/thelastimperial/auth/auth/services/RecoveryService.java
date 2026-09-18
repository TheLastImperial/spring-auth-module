package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;
/**
 *
 * RecoveryService interface to create recovery request.
*/
public interface RecoveryService {
    /**
     * Generate recovery credentials.
     * @param recovery data needed to create recovery request.
    */
    public void generate(Recovery recovery);
}
