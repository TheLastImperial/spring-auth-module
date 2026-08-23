package com.thelastimperial.auth.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    public Optional<UserEntity> findByUsername(String username);
}
