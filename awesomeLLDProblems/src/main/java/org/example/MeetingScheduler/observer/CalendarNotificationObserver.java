package org.example.MeetingScheduler.observer;

import org.example.MeetingScheduler.entities.Meeting;

public class CalendarNotificationObserver implements MeetingObserver {
    @Override
    public void onMeetingScheduled(Meeting meeting) {
        System.out.println("[Calendar] Meeting added to calendar: \""
                + meeting.getSubject() + "\" in " + meeting.getRoom().getName()
                + " (" + meeting.getTimeSlot() + ")");
    }

    @Override
    public void onMeetingCancelled(Meeting meeting) {
        System.out.println("[Calendar] Meeting removed from calendar: \""
                + meeting.getSubject() + "\" in " + meeting.getRoom().getName());
    }
}
