package org.example.NotificationSystem.entities;

import java.util.Optional;
import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private Optional<String> email;
    private Optional<String> phoneNumber;

    public User(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = Optional.ofNullable(email);
        this.phoneNumber = Optional.ofNullable(phoneNumber);
    }

    public String getId() {
        return id;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }
}
