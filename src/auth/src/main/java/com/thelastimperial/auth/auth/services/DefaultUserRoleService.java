package com.thelastimperial.auth.auth.services;

import java.util.List;

import com.thelastimperial.auth.domain.entities.UserRoleEntity;

public interface DefaultUserRoleService {
    public List<UserRoleEntity> getDefaultRoles();
}
