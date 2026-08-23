package com.thelastimperial.auth.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserExpiryEntity;

public interface UserExpiryRepository extends JpaRepository<UserExpiryEntity, UUID>{
}
