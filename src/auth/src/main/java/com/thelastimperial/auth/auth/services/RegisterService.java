package com.thelastimperial.auth.auth.services;

import com.thelastimperial.auth.auth.controllers.requests.NewUser;

public interface RegisterService {
    public void register(NewUser newUser);    
}
