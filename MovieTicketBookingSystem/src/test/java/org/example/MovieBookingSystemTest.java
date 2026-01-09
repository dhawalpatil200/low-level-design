package org.example;

import static org.junit.jupiter.api.Assertions.*;


import org.example.delegate.PaymentService;
import org.example.delegate.ScreeningManager;
import org.example.entities.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieBookingSystemE2ETest {

    @Test
    void shouldBookTicket_EndToEndJourney() {

        // -------- Setup Phase (Admin Config) --------

        City bangalore = new City("Bangalore");

        Cinema pvr = new Cinema(bangalore, "PVR Orion");

        Layout layout = new Layout(3, 3); // 9 seats
        Room room1 = new Room("R1", layout);
        pvr.addRoom(room1);

        Movie avengers = new Movie("Avengers", "Fiction", Duration.ofMinutes(120));

        Screening screening = new Screening(
                avengers,
                room1,
                LocalDateTime.now().plusHours(2),
                Duration.ofHours(3)
        );

        ScreeningManager screeningManager = new ScreeningManager();
        screeningManager.addScreening(avengers, screening);

        PaymentService paymentService = new PaymentService();

        BookingService bookingService =
                new BookingService(screeningManager, paymentService, List.of(pvr));

        MovieBookingSystem system = new MovieBookingSystem(bookingService);

        system.addMovie(avengers);
        system.addCinema(pvr);

        // -------- Step 1: User selects movie + city --------

        Movie selectedMovie = avengers;
        City selectedCity = bangalore;

        // -------- Step 2: Search shows --------

        List<Screening> shows =
                system.searchShows(selectedMovie, selectedCity, LocalDate.now());

        assertEquals(1, shows.size());

        Screening selectedShow = shows.get(0);

        // -------- Step 3: Available seats --------

        List<Seat> availableSeats = system.getAvailableSeats(selectedShow);

        assertEquals(9, availableSeats.size());

        // -------- Step 4: Select seat --------

        Seat selectedSeat = availableSeats.get(0);

        BookingSession session =
                system.selectSeat(selectedShow, selectedSeat);

        assertNotNull(session);

        // -------- Step 5 & 6: Payment + Ticket Generation --------

        Ticket ticket = system.confirmBooking(session);

        assertNotNull(ticket);
        assertEquals(selectedSeat, ticket.getSeat());
        assertEquals(selectedShow, ticket.getScreening());

        // -------- Post booking validation --------

        List<Seat> seatsAfterBooking =
                system.getAvailableSeats(selectedShow);

        assertEquals(8, seatsAfterBooking.size());
        assertFalse(seatsAfterBooking.contains(selectedSeat));
    }
}
