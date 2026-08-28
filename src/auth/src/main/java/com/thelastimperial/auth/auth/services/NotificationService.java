package com.thelastimperial.auth.auth.services;

public interface NotificationService<T> {
    public void send(T rq);
}
