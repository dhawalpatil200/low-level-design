package org.example.entities;

public class BookingSession {
    private Screening screening;
    private Seat seat;

    public BookingSession(Screening screening, Seat seat) {
        this.screening = screening;
        this.seat = seat;
    }

    public Screening getScreening() { return screening; }
    public Seat getSeat() { return seat; }
}
