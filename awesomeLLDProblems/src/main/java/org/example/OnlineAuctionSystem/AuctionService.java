package org.example.OnlineAuctionSystem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AuctionService {
    private static AuctionService instance;
    private final Map<String, User> users;
    private final Map<String, Auction> auctions;
    private final ScheduledExecutorService scheduler;

    private AuctionService() {
        users = new ConcurrentHashMap<>();
        auctions = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public static synchronized AuctionService getInstance() {
        if (instance == null) {
            instance = new AuctionService();
        }
        return instance;
    }

    public User createUser(String name) {
        User user = new User(name);
        users.put(user.getId(), user);
        return user;
    }

    public Auction createAuction(String itemName, String description, BigDecimal startingPrice, LocalDateTime endTime) {
        Auction auction = new Auction(itemName, description, startingPrice, endTime);
        auctions.put(auction.getId(), auction);

        // In a real system, you'd use a scheduler to automatically end auctions.
        // This demonstrates how it would be done.
        long delay = java.time.Duration.between(LocalDateTime.now(), endTime).toMillis();
        scheduler.schedule(() -> endAuction(auction.getId()), delay, TimeUnit.MILLISECONDS);

        System.out.printf("New auction created for '%s' (ID: %s), ending at %s.\n", itemName, auction.getId(), endTime);
        return auction;
    }

    public void placeBid(String auctionId, String bidderId, BigDecimal amount) {
        Auction auction = getAuction(auctionId);
        auction.placeBid(getUser(bidderId), amount);
    }

    public List<Auction> viewActiveAuction() {
        return auctions.values().stream().filter(Auction::isActive).toList();
    }

    public void endAuction(String auctionId) {
        Auction auction = getAuction(auctionId);
        auction.endAuction();
    }

    public Auction getAuction(String auctionId) {
        Auction auction = auctions.get(auctionId);
        if (auction == null) {
            throw new NoSuchElementException("Auction with ID " + auctionId + " not found.");
        }
        return auction;
    }

    public User getUser(String userId) {
        User user = users.get(userId);
        if(userId == null) {
            throw new NoSuchElementException("User with ID " + userId + " not found.");
        }
        return user;
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
