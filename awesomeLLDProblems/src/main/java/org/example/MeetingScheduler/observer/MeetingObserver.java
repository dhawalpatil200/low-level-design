package org.example.MeetingScheduler.observer;

import org.example.MeetingScheduler.entities.Meeting;

public interface MeetingObserver {
    void onMeetingScheduled(Meeting meeting);
    void onMeetingCancelled(Meeting meeting);
}
