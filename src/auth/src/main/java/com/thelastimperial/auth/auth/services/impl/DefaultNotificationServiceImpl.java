package com.thelastimperial.auth.auth.services.impl;

import com.thelastimperial.auth.auth.services.NotificationService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * DefaultNotificationServiceImpl Default notifications. Just log the rq Object.
*/
@Slf4j
public class DefaultNotificationServiceImpl implements NotificationService<Object> {

    @Override
    public void send(Object rq) {
        log.info("Default notification: {}", rq);
    }

}
