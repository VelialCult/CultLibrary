package ru.velialcult.library.bukkit.notification;

import java.util.*;

public class NotificationService {

    private final List<Notification> notifications;
    private final Timer timer;

    public NotificationService() {
        this.notifications = new ArrayList<>();
        this.timer = new Timer();
    }

    private Notification getNotification(UUID uuid) {
        Notification notification = getNotificationByUUID(uuid);
        if (notification == null) {
            notifications.add(new Notification(uuid));
        }

        return getNotificationByUUID(uuid);
    }

    private Notification getNotificationByUUID(UUID uuid) {
        return notifications.stream()
                .filter(notification -> notification.getUuid().equals(uuid))
                .findAny()
                .orElse(null);
    }

    public void sendMessage(UUID uuid, String notificationReason, long delayInSeconds, Runnable runnable) {
        Notification notification = getNotification(uuid);
        TimerTask timerTask = notification.getTimerByReason(notificationReason);
        if (timerTask == null) {
            runnable.run();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    notification.removeNotification(notificationReason);
                }
            };
            notification.addNotification(notificationReason, timerTask);
            timer.schedule(timerTask, delayInSeconds * 1000);
        }
    }
}
