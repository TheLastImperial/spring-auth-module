package com.thelastimperial.auth.authn.services;

import java.util.List;

import org.springframework.security.web.webauthn.api.CredentialRecord;
/**
 *
 * CredentialRecordService Service to manage the CredentialRecord.
*/
public interface CredentialRecordService {
    /**
     * Get the CredentialRecords by username.
     * @param username The owner of the CredentialRecord.
     * @return A List of CredentialRecord.
    */
    public List<CredentialRecord> findByUsername(String username);
}
