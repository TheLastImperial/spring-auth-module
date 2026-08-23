package com.thelastimperial.auth.auth.controllers.requests;

import com.thelastimperial.utils.annotations.EnvPattern;
import com.thelastimperial.utils.annotations.EqualsStrings;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@EqualsStrings(
    field = "password",
    fieldMatch = "passwordConfirmation",
    message = "Password doesn't match"
)
public class NewPassword {
    @EnvPattern(
        property = "com.thelastimperial.auth.patterns.password",
        message = "Bad password pattern."
    )
    private String password;
    private String passwordConfirmation;
    private String token;
}
