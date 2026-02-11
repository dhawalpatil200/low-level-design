package org.example.MovieBookingSystem.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screening {
    private Movie movie;
    private Room room;
    private LocalDateTime startTime;
    private Duration duration;

    private final Map<String, ScreeningSeat> seats = new HashMap<>();

    public Screening(Movie movie, Room room, LocalDateTime startTime, Duration duration) {
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.duration = duration;

        initSeats();
    }

    private void initSeats() {
        for (Seat seat : room.getLayout().getAllSeats()) {
            seats.put(seat.getSeatNumber(), new ScreeningSeat(seat));
        }
    }

    public ScreeningSeat getScreeningSeat(String seatNumber) {
        return seats.get(seatNumber);
    }

    public List<Seat> getAvailableSeats() {
        return seats.values().stream()
                .filter(ScreeningSeat::isAvailable)
                .map(ScreeningSeat::getSeat)
                .toList();
    }

    public Duration getDuration() {
        return duration;
    }

    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getDate() {
        return startTime.toLocalDate();
    }

}
