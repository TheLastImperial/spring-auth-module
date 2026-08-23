package com.thelastimperial.auth.auth.controllers.requests;

import com.thelastimperial.utils.annotations.EnvPattern;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Recovery {
    @EnvPattern(
        property = "com.thelastimperial.auth.patterns.email",
        message = "Bad email pattern."
    )
    private String username;
}
