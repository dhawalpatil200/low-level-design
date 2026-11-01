package org.example.MeetingScheduler;

import org.example.MeetingScheduler.entities.Meeting;
import org.example.MeetingScheduler.entities.Room;
import org.example.MeetingScheduler.entities.TimeSlot;
import org.example.MeetingScheduler.entities.User;
import org.example.MeetingScheduler.exceptions.MeetingSchedulerException;
import org.example.MeetingScheduler.observer.MeetingObserver;
import org.example.MeetingScheduler.strategy.FirstAvailableStrategy;
import org.example.MeetingScheduler.strategy.RoomSelectionStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MeetingScheduler {
    private static volatile MeetingScheduler instance;
    private static final Object lock = new Object();

    private final ConcurrentHashMap<String, Room> rooms;
    private final ConcurrentHashMap<String, Meeting> meetings;
    // Maps room ID to list of active (non-cancelled) meetings in that room
    private final ConcurrentHashMap<String, List<Meeting>> roomMeetings;
    private final CopyOnWriteArrayList<MeetingObserver> observers;
    private RoomSelectionStrategy roomSelectionStrategy;
    private final AtomicInteger meetingCounter;

    private MeetingScheduler() {
        this.rooms = new ConcurrentHashMap<>();
        this.meetings = new ConcurrentHashMap<>();
        this.roomMeetings = new ConcurrentHashMap<>();
        this.observers = new CopyOnWriteArrayList<>();
        this.roomSelectionStrategy = new FirstAvailableStrategy();
        this.meetingCounter = new AtomicInteger(0);
    }

    public static MeetingScheduler getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MeetingScheduler();
                }
            }
        }
        return instance;
    }

    public synchronized void addRoom(Room room) {
        rooms.put(room.getId(), room);
        roomMeetings.putIfAbsent(room.getId(), new ArrayList<>());
    }

    public synchronized Meeting scheduleMeeting(String subject, User organizer,
                                                List<User> participants,
                                                TimeSlot timeSlot,
                                                int requiredCapacity) {
        // Find rooms that are available during this time slot and meet capacity
        List<Room> availableRooms = getAvailableRooms(timeSlot, requiredCapacity);

        // Use the strategy to select the best room
        Room selectedRoom = roomSelectionStrategy.selectRoom(
                availableRooms, requiredCapacity);

        if (selectedRoom == null) {
            throw new MeetingSchedulerException(
                    "No available room found for capacity " + requiredCapacity
                            + " during " + timeSlot);
        }

        // Create the meeting
        String meetingId = "MTG-" + meetingCounter.incrementAndGet();
        Meeting meeting = new Meeting(meetingId, subject, organizer,
                participants, selectedRoom, timeSlot);

        // Register the meeting
        meetings.put(meetingId, meeting);
        roomMeetings.get(selectedRoom.getId()).add(meeting);

        // Notify observers
        notifyMeetingScheduled(meeting);

        return meeting;
    }

    public synchronized void cancelMeeting(String meetingId) {
        Meeting meeting = meetings.get(meetingId);
        if (meeting == null) {
            throw new MeetingSchedulerException(
                    "Meeting not found: " + meetingId);
        }

        // Cancel the meeting (enforces state machine)
        meeting.cancel();

        // Remove from room's meeting list so the slot is freed
        roomMeetings.get(meeting.getRoom().getId()).remove(meeting);

        // Notify observers
        notifyMeetingCancelled(meeting);
    }

    public synchronized List<Room> getAvailableRooms(TimeSlot timeSlot,
                                                     int requiredCapacity) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.getCapacity() < requiredCapacity) {
                continue;
            }
            // Check if room has any conflicting meetings
            List<Meeting> existingMeetings = roomMeetings
                    .getOrDefault(room.getId(), Collections.emptyList());
            boolean hasConflict = existingMeetings.stream()
                    .anyMatch(m -> m.getTimeSlot().overlaps(timeSlot));
            if (!hasConflict) {
                available.add(room);
            }
        }
        return available;
    }

    public synchronized void setRoomSelectionStrategy(RoomSelectionStrategy strategy) {
        this.roomSelectionStrategy = strategy;
    }

    public void addObserver(MeetingObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MeetingObserver observer) {
        observers.remove(observer);
    }

    private void notifyMeetingScheduled(Meeting meeting) {
        for (MeetingObserver observer : observers) {
            try {
                observer.onMeetingScheduled(meeting);
            } catch (Exception e) {
                System.err.println("Observer notification failed: " + e.getMessage());
            }
        }
    }

    private void notifyMeetingCancelled(Meeting meeting) {
        for (MeetingObserver observer : observers) {
            try {
                observer.onMeetingCancelled(meeting);
            } catch (Exception e) {
                System.err.println("Observer notification failed: " + e.getMessage());
            }
        }
    }
}