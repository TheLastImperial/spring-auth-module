package com.thelastimperial.auth.auth.services;

import java.util.Optional;

import com.thelastimperial.auth.domain.entities.UserEntity;
import com.thelastimperial.auth.domain.entities.UserInvitationEntity;
/**
 *
 * RegisterInvitationService Process invitation to register.
*/
public interface RegisterInvitationService {
    /**
     * Get invitation by id.
     * @param id String that will be converted to UUID.
     * @return Optional<UserInvitationEntity> The Optional will be empty when:
     * Id dont have UUID format.
     * Id dont exists.
     * The invitation is used.
     * The invitation is expired.
    */
    public Optional<UserInvitationEntity> getInvitation(String id);
    /**
     * Save the Invitation when is used.
     * @param id Invitation Id
     * @param guest The new user created.
    */
    public void saveGuest(String id, UserEntity guest);
    /**
     * Create a new Invitation.
     * @param host Who create the invitation.
     * @param username Email that going to create use account.
    */
    public void save(UserEntity host, String username);
}
