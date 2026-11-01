package org.example.MeetingScheduler.entities;

import org.example.MeetingScheduler.exceptions.MeetingSchedulerException;

import java.time.LocalDateTime;

public class TimeSlot {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new MeetingSchedulerException(
                    "End time must be after start time");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    // Two time slots overlap if start1 < end2 AND start2 < end1
    public boolean overlaps(TimeSlot other) {
        return this.startTime.isBefore(other.endTime)
                && other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d-%02d:%02d",
                startTime.getHour(), startTime.getMinute(),
                endTime.getHour(), endTime.getMinute());
    }
}

/*
TimeSlot1 = [A, B)
TimeSlot2 = [C, D)

your task is to write whether these to overlaps with each other or not

[A, B) [C = B, D) not overlap

A < D && B > C -> overlap
if A >= D then we know B also greater so no overlap

 */