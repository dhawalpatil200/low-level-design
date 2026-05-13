package org.example.NotificationSystem.service;

import org.example.NotificationSystem.enums.NotificationType;
import org.example.NotificationSystem.entities.User;

import java.util.*;

public class ChannelPreferenceService {
    private final Map<String, List<NotificationType>> channelPreferences;

    public ChannelPreferenceService() {
        this.channelPreferences = new HashMap<>();
    }

    public synchronized void addChannelPreference(User user, NotificationType notificationType) {
        addChannelValidator(user, notificationType);
        String userId = user.getId();
        channelPreferences.computeIfAbsent(userId, k -> new ArrayList<>()).add(notificationType);
        System.out.printf("[CHANNEL][ADDED][%s][%s]\n", user.getId(), notificationType);
    }

    public synchronized void removeChannelPreference(User user, NotificationType notificationType) {
        Objects.requireNonNull(user, "User is null");
        Objects.requireNonNull(notificationType, "Notification type is null");

        String userId = user.getId();
        if(!channelPreferences.containsKey(userId)) return;
        channelPreferences.get(userId).remove(notificationType);
        System.out.printf("[CHANNEL][REMOVED][%s][%s]\n", user.getId(), notificationType);
    }

    public List<NotificationType> getPreferences(User user) {
        Objects.requireNonNull(user, "User is null");
        return channelPreferences.getOrDefault(user.getId(), new ArrayList<>());
    }

    private void addChannelValidator(User user, NotificationType notificationType) {
        Objects.requireNonNull(user, "User is null");
        Objects.requireNonNull(notificationType, "Notification type is null");


        if(notificationType == NotificationType.EMAIL && user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email is not registered, can't be registered");
        }

        if(notificationType == NotificationType.SMS && user.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("User phone is not present, can't be registered");
        }
    }
}
