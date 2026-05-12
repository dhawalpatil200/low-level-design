package org.example.OnlineAuctionSystem;

import java.util.UUID;

public class User implements AuctionObserver{
    private final String id;
    private final String name;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onUpdate(Auction auction, String message) {
        System.out.printf("[User={%s}][Received notification][auction={%s}][message={%s}]\n", getName(), auction.getItemName(), message);
    }
}
