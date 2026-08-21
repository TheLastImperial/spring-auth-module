package com.thelastimperial.auth.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID>{
}
