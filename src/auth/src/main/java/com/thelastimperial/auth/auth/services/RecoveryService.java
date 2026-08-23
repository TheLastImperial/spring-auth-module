package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.Recovery;

public interface RecoveryService {
    public void generate(Recovery recovery);
}
