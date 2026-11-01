package org.example.MeetingScheduler.entities;

import org.example.MeetingScheduler.enums.MeetingStatus;
import org.example.MeetingScheduler.exceptions.MeetingSchedulerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Meeting {
    private final String id;
    private final String subject;
    private final User organizer;
    private final List<User> participants;
    private final Room room;
    private final TimeSlot timeSlot;
    private MeetingStatus status;

    public Meeting(String id, String subject, User organizer,
                   List<User> participants, Room room, TimeSlot timeSlot) {
        this.id = id;
        this.subject = subject;
        this.organizer = organizer;
        this.participants = Collections.unmodifiableList(
                new ArrayList<>(participants));
        this.room = room;
        this.timeSlot = timeSlot;
        this.status = MeetingStatus.SCHEDULED;
    }

    public String getId() { return id; }
    public String getSubject() { return subject; }
    public User getOrganizer() { return organizer; }
    public List<User> getParticipants() { return participants; }
    public Room getRoom() { return room; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public MeetingStatus getStatus() { return status; }

    public void cancel() {
        if (status != MeetingStatus.SCHEDULED) {
            throw new MeetingSchedulerException(
                    "Can only cancel SCHEDULED meetings. Current status: " + status);
        }
        this.status = MeetingStatus.CANCELLED;
    }

    public void complete() {
        if (status != MeetingStatus.SCHEDULED) {
            throw new MeetingSchedulerException(
                    "Can only complete SCHEDULED meetings. Current status: " + status);
        }
        this.status = MeetingStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return "Meeting{id=" + id + ", subject='" + subject + "', room="
                + room.getName() + ", time=" + timeSlot + ", status=" + status + "}";
    }
}