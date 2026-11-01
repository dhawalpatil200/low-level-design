package org.example.MeetingScheduler;

import org.example.MeetingScheduler.entities.Meeting;
import org.example.MeetingScheduler.entities.Room;
import org.example.MeetingScheduler.entities.TimeSlot;
import org.example.MeetingScheduler.entities.User;
import org.example.MeetingScheduler.enums.RoomType;
import org.example.MeetingScheduler.observer.CalendarNotificationObserver;
import org.example.MeetingScheduler.observer.EmailNotificationObserver;
import org.example.MeetingScheduler.strategy.BestFitStrategy;
import org.example.MeetingScheduler.strategy.FirstAvailableStrategy;

import java.util.*;
import java.time.*;

public class MeetingSchedulerDemo {
    public static void main(String[] args) {
        MeetingScheduler scheduler = MeetingScheduler.getInstance();

        // Register observers
        scheduler.addObserver(new EmailNotificationObserver());
        scheduler.addObserver(new CalendarNotificationObserver());

        // Add rooms
        Room everest = new Room("R1", "Everest", RoomType.CONFERENCE, 10);
        Room alps = new Room("R2", "Alps", RoomType.BOARD_ROOM, 20);
        Room nook = new Room("R3", "Nook", RoomType.HUDDLE_SPACE, 4);

        scheduler.addRoom(everest);
        scheduler.addRoom(alps);
        scheduler.addRoom(nook);

        // Create users
        User alice = new User("U1", "Alice", "alice@example.com");
        User bob = new User("U2", "Bob", "bob@example.com");
        User charlie = new User("U3", "Charlie", "charlie@example.com");
        User diana = new User("U4", "Diana", "diana@example.com");

        LocalDateTime now = LocalDateTime.of(2025, 1, 15, 9, 0);

        // === Scenario 1: Schedule a meeting with first-available strategy ===
        System.out.println("========== SCENARIO 1: Schedule Meeting (First Available) ==========");
        scheduler.setRoomSelectionStrategy(new FirstAvailableStrategy());
        TimeSlot slot1 = new TimeSlot(now, now.plusHours(1));
        Meeting meeting1 = scheduler.scheduleMeeting(
                "Sprint Planning", alice, Arrays.asList(bob, charlie), slot1, 3);
        System.out.println("Scheduled: " + meeting1);

        // === Scenario 2: Overlapping time slot (conflict detection) ===
        // Slot2 overlaps with slot1 (Everest is booked), but Alps and Nook are still available
        System.out.println("\n========== SCENARIO 2: Overlapping Slot (Another Room Available) ==========");
        TimeSlot slot2 = new TimeSlot(now.plusMinutes(30), now.plusHours(2));
        Meeting meeting2 = scheduler.scheduleMeeting(
                "Design Review", bob, Arrays.asList(alice), slot2, 3);
        System.out.println("Scheduled: " + meeting2);

        // === Scenario 3: Switch to best-fit strategy ===
        System.out.println("\n========== SCENARIO 3: Schedule Meeting (Best Fit) ==========");
        scheduler.setRoomSelectionStrategy(new BestFitStrategy());
        TimeSlot slot3 = new TimeSlot(now.plusHours(2), now.plusHours(3));
        Meeting meeting3 = scheduler.scheduleMeeting(
                "1-on-1 Sync", alice, Arrays.asList(diana), slot3, 2);
        System.out.println("Scheduled: " + meeting3);

        // === Scenario 4: Cancel a meeting ===
        System.out.println("\n========== SCENARIO 4: Cancel Meeting ==========");
        scheduler.cancelMeeting(meeting1.getId());
        System.out.println("Meeting cancelled: " + meeting1.getSubject());
        System.out.println("Status: " + meeting1.getStatus());

        // === Scenario 5: Check available rooms ===
        System.out.println("\n========== SCENARIO 5: Check Available Rooms ==========");
        List<Room> available = scheduler.getAvailableRooms(slot1, 2);
        System.out.println("Available rooms for " + slot1 + ":");
        for (Room room : available) {
            System.out.println("  - " + room);
        }

        // === Scenario 6: Schedule in the now-free slot ===
        System.out.println("\n========== SCENARIO 6: Schedule in Freed Slot ==========");
        Meeting meeting4 = scheduler.scheduleMeeting(
                "Retrospective", charlie, Arrays.asList(alice, bob, diana), slot1, 5);
        System.out.println("Scheduled: " + meeting4);
    }
}
