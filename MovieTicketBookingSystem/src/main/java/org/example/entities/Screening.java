package org.example.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Screening {
    private Movie movie;
    private Room room;
    private LocalDateTime startTime;
    private Duration duration;

    public Screening(Movie movie, Room room, LocalDateTime startTime, Duration duration) {
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.duration = duration;
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
