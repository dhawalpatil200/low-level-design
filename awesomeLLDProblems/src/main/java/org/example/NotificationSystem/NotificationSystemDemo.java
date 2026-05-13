package org.example.NotificationSystem;

import org.example.NotificationSystem.channel.NotificationChannelFactory;
import org.example.NotificationSystem.entities.User;
import org.example.NotificationSystem.enums.NotificationType;
import org.example.NotificationSystem.service.ChannelPreferenceService;
import org.example.NotificationSystem.service.NotificationDispatcherService;

public class NotificationSystemDemo {
    public static void main(String[] args) {
        User user1 = new User("Dhawal", "email@.com", "+223423");
        ChannelPreferenceService channelPreferenceService = new ChannelPreferenceService();
        channelPreferenceService.addChannelPreference(user1, NotificationType.EMAIL);
        channelPreferenceService.addChannelPreference(user1, NotificationType.PUSH);

        NotificationChannelFactory factory = new NotificationChannelFactory();

        NotificationDispatcherService service = new NotificationDispatcherService(channelPreferenceService, factory);
        service.sendNotification(user1, "First message");
        service.shutdown();
    }
}
