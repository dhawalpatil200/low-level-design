package org.example.MovieBookingSystem.entities;


import org.example.MovieBookingSystem.strategy.NormalRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Layout {
    int rows;
    int columns;
    Map<String, Seat> seatByNumber;
    Map<Integer, Map<Integer, Seat>> seatByPosition;

    public Layout(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.seatByNumber = new HashMap<>();
        this.seatByPosition = new HashMap<>();
        initializeLayout();
    }

    private void initializeLayout() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                String seatNumber = i + "-" + j;
                addSeat(seatNumber, i, j, new Seat(seatNumber, new NormalRate()));
            }
        }
    }

    void addSeat(String seatNumber, int row, int col, Seat seat) {
        seatByNumber.put(seatNumber, seat);

        seatByPosition
        .computeIfAbsent(row, k -> new HashMap<>())
        .put(col, seat);
    }

    public Seat getSeatByNumber(String seatNumber) {
        return seatByNumber.get(seatNumber);
    }

    public Seat getSeatByPosition(int row, int col) {
        Map<Integer, Seat> rowSeats = seatByPosition.get(row);
        return rowSeats != null ? rowSeats.get(col) : null;
    }

    public List<Seat> getAllSeats() {
        return List.copyOf(seatByNumber.values());
    }
}
