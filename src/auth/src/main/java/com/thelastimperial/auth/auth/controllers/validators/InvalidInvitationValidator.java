package com.thelastimperial.auth.auth.controllers.validators;

import java.time.Instant;
import java.util.Optional;

import com.thelastimperial.auth.auth.controllers.annotations.InvalidInvitation;
import com.thelastimperial.auth.auth.services.RegisterInvitationService;
import com.thelastimperial.auth.domain.entities.UserInvitationEntity;
import com.thelastimperial.utils.UUIDUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidInvitationValidator implements ConstraintValidator<InvalidInvitation, String>{
    private final RegisterInvitationService registerInvitationService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        if(!value.isBlank() && !UUIDUtils.isValid(value)){
            context
                .buildConstraintViolationWithTemplate("Invalid UUID format.")
                .addConstraintViolation();
            return false;
        }
        Optional<UserInvitationEntity> invitationOpt = registerInvitationService
            .getInvitation(value);
        String message = "";
        boolean result = true;

        if(invitationOpt.isEmpty()){
            message = "Invitation dont exists.";
            result = false;
        }

        if(invitationOpt.isPresent() && invitationOpt.get().isUsed()) {
            message = "Invitation is used.";
            result = false;
        }

        if(invitationOpt.isPresent() && invitationOpt.get().getExpiredAt().isBefore(Instant.now())){
            message = "Invitation is expired.";
            result = false;
        }

        if(!result){
            context
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        }
        return result;
    }

}
