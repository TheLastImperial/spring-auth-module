package com.thelastimperial.auth.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserInvitationEntity;

public interface UserInvitationRepository extends JpaRepository<UserInvitationEntity, UUID>{
}
