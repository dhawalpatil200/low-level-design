package org.example.MovieBookingSystem.entities;

import java.time.Duration;

public class Movie {
    private String title;
    private String genre;
    private Duration durationInMinutes;

    public Movie(String title, String genre, Duration durationInMinutes) {
        this.title = title;
        this.genre = genre;
        this.durationInMinutes = durationInMinutes;
    }

    public Duration getDuration() {
        return durationInMinutes;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }
}
