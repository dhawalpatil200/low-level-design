package org.example.MovieBookingSystem.entities;

public class BookingSession {
    private final Screening screening;
    private final Seat seat;
    private final String holdToken;

    public BookingSession(Screening screening, Seat seat, String token) {
        this.screening = screening;
        this.seat = seat;
        this.holdToken = token;
    }

    public Screening getScreening() { return screening; }
    public Seat getSeat() { return seat; }

    public String getHoldToken() {
        return holdToken;
    }
}
