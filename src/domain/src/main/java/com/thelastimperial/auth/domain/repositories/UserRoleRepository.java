package com.thelastimperial.auth.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserRoleEntity;
import java.util.List;


public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID>{
    public List<UserRoleEntity> findByIsDefault(boolean isDefault);
    public List<UserRoleEntity> findByNameIn(List<String> names);
}
