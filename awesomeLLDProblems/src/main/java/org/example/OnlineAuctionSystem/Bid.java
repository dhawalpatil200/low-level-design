package org.example.OnlineAuctionSystem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bid implements Comparable<Bid>{
    private final User user;
    private final BigDecimal amount;
    private final LocalDateTime time;

    public Bid(User user, BigDecimal amount, LocalDateTime time) {
        this.user = user;
        this.amount = amount;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Bid other) {
        int amountComparison = this.amount.compareTo(other.amount);
        if (amountComparison != 0) {
            return amountComparison;
        }
        return other.time.compareTo(this.time);
    }

    @Override
    public String toString() {
        return String.format("Bidder: %s, Amount: %.2f, Time: %s", user.getName(), amount, time);
    }
}
