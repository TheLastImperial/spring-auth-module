package com.thelastimperial.auth.auth.services;
/**
 *
 * NotificationService interface to send notifications.
 * @param <T> The object request to send the notification.
*/
public interface NotificationService<T> {
    /**
     * Method to send notification.
     * @param rq Object with data to send notification.
    */
    public void send(T rq);
}
