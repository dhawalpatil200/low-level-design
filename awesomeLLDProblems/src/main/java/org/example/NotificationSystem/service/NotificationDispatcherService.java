package org.example.NotificationSystem.service;

import org.example.NotificationSystem.channel.NotificationChannel;
import org.example.NotificationSystem.channel.NotificationChannelFactory;
import org.example.NotificationSystem.entities.Notification;
import org.example.NotificationSystem.entities.User;
import org.example.NotificationSystem.enums.NotificationType;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationDispatcherService {
    private static final int MAX_RETRIES = 3;
    private final ChannelPreferenceService channelPreferenceService;
    private final NotificationChannelFactory factory;

    private final ExecutorService executorService;

    public NotificationDispatcherService(ChannelPreferenceService channelPreferenceService, NotificationChannelFactory factory) {
        this.channelPreferenceService = channelPreferenceService;
        this.factory = factory;
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public void sendNotification(User recipient,  String message) {
        // validate arguments
        Objects.requireNonNull(recipient, "Recipient is null");
        if(message.isEmpty()) {
            throw new IllegalArgumentException("Message is null or empty");
        }

        Notification notification = new Notification(recipient, message);

        // extract all notification preferences
        List<NotificationType> preferences = channelPreferenceService.getPreferences(recipient);

        // send notification to channels async
        for(NotificationType notificationType: preferences) {
            NotificationChannel notificationChannel = factory.getNotificationChannel(notificationType);
            executorService.submit(() -> {
                for(int i = 0; i < MAX_RETRIES; i++) {
                    try {
                        notificationChannel.send(notification);
                       return;
                    } catch (IllegalStateException e) {
                        System.out.printf("[SEND][FAILED][BAD REQUEST][%s][%s][%s][%s]\n", notification.getId(), notificationType,  recipient.getId(), e.getMessage());
                        return;
                    } catch (Exception e) {
                        System.out.printf("[RETRY][%s][%s][%s][%d times]\n", notification.getId(), notificationType,  recipient.getId(), i + 1);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }

                System.out.printf("[SEND][FAILED][%s][%s]\n", notification.getId(), notificationType);
            });
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
