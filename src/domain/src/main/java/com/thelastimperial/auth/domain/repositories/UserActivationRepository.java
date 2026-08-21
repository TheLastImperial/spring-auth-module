package com.thelastimperial.auth.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserActivationEntity;

public interface UserActivationRepository extends JpaRepository<UserActivationEntity, UUID>{
}
