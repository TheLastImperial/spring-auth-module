package com.thelastimperial.auth.auth.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thelastimperial.auth.auth.services.DefaultUserRoleService;
import com.thelastimperial.auth.domain.entities.UserRoleEntity;
import com.thelastimperial.auth.domain.repositories.UserRoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DefaultUserRoleServiceImpl implements DefaultUserRoleService {
    private final UserRoleRepository userRoleRepository;
    @Override
    public List<UserRoleEntity> getDefaultRoles() {
        return userRoleRepository.findByIsDefault(true);
    }
}
