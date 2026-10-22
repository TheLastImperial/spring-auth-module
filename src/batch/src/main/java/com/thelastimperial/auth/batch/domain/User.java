package com.thelastimperial.auth.batch.domain;

import java.util.UUID;

public record User(UUID id, String action) {
}
