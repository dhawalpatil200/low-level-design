package org.example;

import org.example.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieBookingSystem {

    private List<Movie> movies = new ArrayList<>();
    private List<Cinema> cinemas = new ArrayList<>();
    private BookingService bookingService;

    public MovieBookingSystem(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // -------- Admin Setup APIs --------

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    public List<Cinema> getCinemasInCity(City city) {
        return cinemas.stream()
                .filter(c -> c.getCity().equals(city))
                .toList();
    }

    // -------- User Journey APIs --------
    public List<Screening> searchShows(Movie movie, City city, LocalDate date) {
        return bookingService.searchShows(movie, city, date);
    }

    public List<Seat> getAvailableSeats(Screening screening) {
        return bookingService.getAvailableSeats(screening);
    }

    public BookingSession selectSeat(Screening screening, Seat seat) {
        return bookingService.selectSeat(screening, seat);
    }

    public Ticket confirmBooking(BookingSession session) {
        return bookingService.confirmBooking(session);
    }
}