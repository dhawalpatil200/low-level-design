package org.example.OnlineAuctionSystem;

public interface AuctionObserver {
    void onUpdate(Auction auction, String message);
}
