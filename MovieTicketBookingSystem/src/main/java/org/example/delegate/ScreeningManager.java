package org.example.delegate;

import org.example.entities.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ScreeningManager {
    private Map<Movie, List<Screening>> screeningsByMovie;
    private Map<Screening, Set<Seat>> bookedSeatsByScreening;

    public ScreeningManager() {
        this.screeningsByMovie = new HashMap<>();
        this.bookedSeatsByScreening = new HashMap<>();
    }

    public void addScreening(Movie movie, Screening screening) {
        screeningsByMovie
        .computeIfAbsent(movie, k -> new ArrayList<>())
        .add(screening);
    }

    public List<Screening> findScreening(Movie movie, City city, LocalDate date, List<Cinema> cinemas) {
        Set<Room> roomsInCity = cinemas.stream()
                .filter(c -> c.getCity().equals(city))
                .flatMap(c -> c.getRooms().stream())
                .collect(Collectors.toSet());

        return screeningsByMovie.getOrDefault(movie, new ArrayList<>()).stream()
                .filter(s -> roomsInCity.contains(s.getRoom()))
                .filter(s -> s.getDate().equals(date))
                .toList();
    }

    public List<Seat> getAvailableSeats(Screening screening) {
        List<Seat> allSeats = screening.getRoom().getLayout().getAllSeats();
        Set<Seat> bookedSeats = bookedSeatsByScreening.getOrDefault(screening, Set.of());
        return allSeats.stream().filter(s -> !bookedSeats.contains(s)).toList();
    }

    public void markSeatBooked(Screening screening, Seat seat) {
        bookedSeatsByScreening
            .computeIfAbsent(screening, k -> new HashSet<>())
            .add(seat);
    }
}
