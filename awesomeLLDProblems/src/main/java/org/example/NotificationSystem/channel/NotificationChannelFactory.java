package org.example.NotificationSystem.channel;

import org.example.NotificationSystem.channel.EmailNotificationChannel;
import org.example.NotificationSystem.channel.NotificationChannel;
import org.example.NotificationSystem.channel.PushNotificationChannel;
import org.example.NotificationSystem.channel.SMSNotificationChannel;
import org.example.NotificationSystem.enums.NotificationType;

import java.util.HashMap;
import java.util.Map;

public class NotificationChannelFactory {
    private final Map<NotificationType, NotificationChannel> channelMap;

    public NotificationChannelFactory() {
        this.channelMap = new HashMap<>();
        buildChannelMap();
    }

    void buildChannelMap() {
        channelMap.put(NotificationType.PUSH, new PushNotificationChannel());
        channelMap.put(NotificationType.SMS, new SMSNotificationChannel());
        channelMap.put(NotificationType.EMAIL, new EmailNotificationChannel());
    }

    public NotificationChannel getNotificationChannel(NotificationType notificationType) {
        if(!channelMap.containsKey(notificationType)) {
            throw new IllegalArgumentException("Invalid notification channel provided");
        }
        return channelMap.get(notificationType);
    }
}
