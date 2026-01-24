package org.example.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScreeningSeat {
    private Seat seat;
    private SeatStatus seatStatus;
    private LocalDateTime holdExpiryTime;
    private String holdToken;

    public ScreeningSeat(Seat seat) {
        this.seat = seat;
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    public Seat getSeat() {
        return seat;
    }

    public synchronized String hold(int holdTimeInMinutes) {
        cleanupIfExpired();

        if (seatStatus != SeatStatus.AVAILABLE) return null;

        seatStatus = SeatStatus.HELD;
        holdExpiryTime = LocalDateTime.now().plusMinutes(holdTimeInMinutes);
        holdToken =  UUID.randomUUID().toString();
        return holdToken;
    }

    public synchronized boolean isAvailable() {
       cleanupIfExpired();
        return seatStatus == SeatStatus.AVAILABLE;
    }

    public synchronized boolean confirmBooking(String token) {
        cleanupIfExpired();

        if (!holdToken.equals(token)) return false;

        seatStatus = SeatStatus.BOOKED;
        holdToken = null;
        return true;
    }

    private void cleanupIfExpired() {
        if (seatStatus == SeatStatus.HELD && holdExpiryTime.isBefore(LocalDateTime.now())) {
            seatStatus = SeatStatus.AVAILABLE;
            holdExpiryTime = null;
            holdToken = null;
        }
    }
}
