package com.thelastimperial.auth.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.auth.domain.entities.UserActionEntity;

public interface UserActionRepository extends JpaRepository<UserActionEntity, String>{
}
