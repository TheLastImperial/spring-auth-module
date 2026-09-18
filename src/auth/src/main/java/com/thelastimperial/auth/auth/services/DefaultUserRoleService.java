package com.thelastimperial.auth.auth.services;

import java.util.List;

import com.thelastimperial.auth.domain.entities.UserRoleEntity;
/**
 *
 * DefaultUserRoleService interface to get default roles to new users.
*/
public interface DefaultUserRoleService {
    /**
     * Default roles for new users as List.
     * @return List of UserRoleEntity.
    */
    public List<UserRoleEntity> getDefaultRoles();
}
