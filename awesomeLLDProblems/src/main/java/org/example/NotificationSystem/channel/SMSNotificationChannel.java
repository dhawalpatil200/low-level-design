package org.example.NotificationSystem.channel;

import org.example.NotificationSystem.entities.Notification;
import org.example.NotificationSystem.entities.User;

public class SMSNotificationChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) {
        User user = notification.getUser();
        if(user.getPhoneNumber().isEmpty()) {
            throw new IllegalStateException(String.format("Phone is empty for user = %s", user.getId()));
        }

        String phone = user.getPhoneNumber().get();
        System.out.printf("[SMS][%s][%S][%s]\n", user.getName(), phone, notification.getMessage());
    }
}
