package org.example.NotificationSystem.channel;

import org.example.NotificationSystem.entities.Notification;
import org.example.NotificationSystem.entities.User;

public class EmailNotificationChannel implements NotificationChannel{
    @Override
    public void send(Notification notification) {
        User user = notification.getUser();
        if(user.getEmail().isEmpty()) {
            throw new IllegalStateException(String.format("Email is empty for user = %s", user.getId()));
        }
        String email = user.getEmail().get();
        System.out.printf("[EMAIL][%s][%S][%s]\n", user.getName(), email, notification.getMessage());
    }
}
