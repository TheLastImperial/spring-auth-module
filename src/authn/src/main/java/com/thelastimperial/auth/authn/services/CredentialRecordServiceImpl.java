package com.thelastimperial.auth.authn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.webauthn.api.CredentialRecord;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CredentialRecordServiceImpl implements CredentialRecordService {
    private final PublicKeyCredentialUserEntityRepository userEntityRepository;
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public List<CredentialRecord> findByUsername(String username) {
        List<CredentialRecord> result = new ArrayList<>();
        PublicKeyCredentialUserEntity userEntity = userEntityRepository.findByUsername(username);
        if(userEntity != null)
            result = userCredentialRepository.findByUserId(userEntity.getId());
        return result;
    }

}
