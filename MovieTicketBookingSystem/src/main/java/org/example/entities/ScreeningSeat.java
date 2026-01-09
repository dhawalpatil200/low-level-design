package org.example.entities;

import java.time.LocalDateTime;

public class ScreeningSeat {
    private Seat seat;
    private SeatStatus seatStatus;
    private LocalDateTime holdExpiryTime;

    public ScreeningSeat(Seat seat) {
        this.seat = seat;
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    public Seat getSeat() {
        return seat;
    }

    public synchronized boolean hold(int holdTimeInMinutes) {
        if (seatStatus == SeatStatus.HELD && holdExpiryTime.isBefore(LocalDateTime.now())) {
            seatStatus = SeatStatus.AVAILABLE;
            holdExpiryTime = null;
        }

        if (seatStatus != SeatStatus.AVAILABLE) return false;

        seatStatus = SeatStatus.HELD;
        holdExpiryTime = LocalDateTime.now().plusMinutes(holdTimeInMinutes);
        return true;
    }

    public synchronized boolean isAvailable() {
        if(seatStatus == SeatStatus.HELD && holdExpiryTime.isBefore(LocalDateTime.now())) {
            seatStatus = SeatStatus.AVAILABLE;
        }
        return seatStatus == SeatStatus.AVAILABLE;
    }

    public synchronized boolean confirmBooking() {
        if(seatStatus != SeatStatus.HELD) return false;
        if(holdExpiryTime.isBefore(LocalDateTime.now())) {
            seatStatus = SeatStatus.AVAILABLE;
            return false;
        }

        seatStatus = SeatStatus.BOOKED;
        return true;
    }
}
