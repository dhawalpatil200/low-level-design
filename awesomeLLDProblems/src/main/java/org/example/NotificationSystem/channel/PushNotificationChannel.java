package org.example.NotificationSystem.channel;

import org.example.NotificationSystem.entities.Notification;
import org.example.NotificationSystem.entities.User;

public class PushNotificationChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) {
        User user = notification.getUser();
        System.out.printf("[PUSH][%s][%S][%s]\n", user.getName(), user.getId(), notification.getMessage());
    }
}
