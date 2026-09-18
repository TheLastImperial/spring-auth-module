package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.NewPassword;
/**
 *
 * NewPasswordService interface to create new password.
*/
public interface NewPasswordService {
    /**
     * Update the user with the new password.
     * @param newPassword Object that have the new password to create.
    */
    public void update(NewPassword newPassword);
}
