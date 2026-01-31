package org.example.entities;

import java.sql.Timestamp;
import java.time.Instant;

public class AccessToken {
    private final String code;
    private final Timestamp expiration;
    private final Locker locker;

    public AccessToken(String code, Timestamp expiration, Locker locker) {
        this.code = code;
        this.expiration = expiration;
        this.locker = locker;
    }

    public boolean isExpired() {
        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        return currentTimestamp.after(expiration);
    }

    public String getCode() {
        return code;
    }

    public Locker getLocker() {
        return locker;
    }
}
