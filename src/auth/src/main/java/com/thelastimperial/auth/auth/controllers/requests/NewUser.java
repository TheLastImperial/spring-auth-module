package com.thelastimperial.auth.auth.controllers.requests;

import com.thelastimperial.utils.annotations.EnvPattern;
import com.thelastimperial.utils.annotations.EqualsStrings;
import com.thelastimperial.utils.annotations.UsernameExists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@EqualsStrings(
    field = "password",
    fieldMatch = "passwordConfirmation",
    message = "Password doesn't match"
)
public class NewUser {
    @EnvPattern(
        property = "com.thelastimperial.auth.patterns.email",
        message = "Bad email pattern."
    )
    @UsernameExists
    private String username;

    @EnvPattern(
        property = "com.thelastimperial.auth.patterns.password",
        message = "Bad password pattern."
    )
    private String password;
    private String passwordConfirmation;
}
