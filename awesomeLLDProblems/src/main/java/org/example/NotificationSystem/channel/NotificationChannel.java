package org.example.NotificationSystem.channel;

import org.example.NotificationSystem.entities.Notification;

public interface NotificationChannel {
    void send(Notification notification);
}
