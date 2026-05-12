package org.example.OnlineAuctionSystem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Auction {
    private final String id;
    private final String itemName;
    private final String description;
    private final BigDecimal startingAmount;
    private final List<Bid> bids;
    private final LocalDateTime endTime;
    private AuctionState auctionState;
    private final Set<AuctionObserver> observers;
    private Bid winningBid;

    public Auction(String itemName, String description, BigDecimal startingAmount, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.description = description;
        this.startingAmount = startingAmount;
        this.endTime = endTime;
        bids = new ArrayList<>();
        this.observers = ConcurrentHashMap.newKeySet();
        this.auctionState = AuctionState.ACTIVE;
    }

    public synchronized void placeBid(User bidder, BigDecimal amount) {
        if(this.auctionState != AuctionState.ACTIVE) {
            throw new IllegalStateException("Auction is already ended");
        }

        LocalDateTime timeStamp = LocalDateTime.now();

        if(timeStamp.isAfter(endTime)) {
            endAuction();
            throw new IllegalStateException("The auction is already ended");
        }


        Bid highestBid = getHighestBid();
        BigDecimal currentMaxAmount = (highestBid == null) ? startingAmount : highestBid.getAmount();
        if(amount.compareTo(currentMaxAmount) <= 0) {
            throw new IllegalArgumentException("amount should be greater than starting amount or current winning bid");
        }



        User previousHighestBidder = (highestBid != null) ? highestBid.getUser() : null;

        Bid bid = new Bid(bidder, amount, timeStamp);
        bids.add(bid);
        this.winningBid = bid;
        System.out.printf("SUCCESS: %s placed a bid of $%.2f on '%s'.\n", bidder.getName(), amount, itemName);

        if(previousHighestBidder != null && !previousHighestBidder.equals(bidder)) {
            notifyObserver(previousHighestBidder, String.format("You have been outbid on '%s'! The new highest bid is $%.2f.", itemName, amount));
        }
    }

    public boolean isActive() {
        return auctionState == AuctionState.ACTIVE;
    }

    private void notifyObserver(AuctionObserver observer, String message) {
        observer.onUpdate(this, message);
    }

    public synchronized void endAuction() {
        if(this.auctionState != AuctionState.ACTIVE) {
            return;
        }

        this.auctionState = AuctionState.ENDED;
        this.winningBid = getHighestBid();

        String endMessage;
        if (winningBid != null) {
            endMessage = String.format("Auction for '%s' has ended. Winner is %s with a bid of $%.2f!",
                    itemName, winningBid.getUser().getName(), winningBid.getAmount());
        } else {
            endMessage = String.format("Auction for '%s' has ended. There were no bids.", itemName);
        }

        System.out.println("\n" + endMessage.toUpperCase());
        notifyAllObservers(endMessage);
    }

    private void notifyAllObservers(String message) {
        for (AuctionObserver observer : observers) {
            observer.onUpdate(this, message);
        }
    }

    private Bid getHighestBid() {
        if (bids.isEmpty()) {
            return null;
        }
        return Collections.max(bids);
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getItemName() { return itemName; }
    public List<Bid> getBidHistory() { return Collections.unmodifiableList(bids); }
    public AuctionState getState() { return auctionState; }
    public Bid getWinningBid() { return winningBid; }
}
