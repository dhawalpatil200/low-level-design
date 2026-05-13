package org.example.NotificationSystem.entities;

import java.util.UUID;

public class Notification {
    private final String id;
    private final User user;
    private final String message;

    public Notification(User user, String message) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
