package org.example;

import org.example.delegate.PaymentService;
import org.example.delegate.ScreeningManager;
import org.example.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BookingService {

    private final ScreeningManager screeningManager;
    private final PaymentService paymentService;
    private List<Cinema> cinemas;

    public BookingService(ScreeningManager screeningManager, PaymentService paymentService, List<Cinema> cinemas) {
        this.screeningManager = screeningManager;
        this.paymentService = paymentService;
        this.cinemas = cinemas;
    }

    public List<Screening> searchShows(Movie movie, City city, LocalDate date) {
        return screeningManager.findScreening(movie, city, date, cinemas);
    }

    public List<Seat> getAvailableSeats(Screening screening) {
        return screeningManager.getAvailableSeats(screening);
    }

    public BookingSession selectSeat(Screening screening, Seat seat) {
        ScreeningSeat ss = screening.getScreeningSeat(seat.getSeatNumber());
        String token = ss.hold(5);
        if(token == null) {
            throw new RuntimeException("Seat already taken");
        }
        return new BookingSession(screening, seat, token);
    }

    public Ticket confirmBooking(BookingSession session) {
        Seat seat = session.getSeat();
        Screening screening = session.getScreening();
        BigDecimal price = seat.getPricingStrategy().getPrice();
        if (!paymentService.pay(price)) {
            throw new RuntimeException("Payment failed");
        }
        ScreeningSeat ss = screening.getScreeningSeat(seat.getSeatNumber());
        boolean confirmed = ss.confirmBooking(session.getHoldToken());
        if(!confirmed) {
            throw new RuntimeException("Seat hold expired");
        }

        return new Ticket(screening, seat, price);
    }
}
