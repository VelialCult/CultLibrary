package ru.velialcult.library.bukkit.notification;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

public class Notification {

    private final UUID uuid;

    private final Map<String, TimerTask> notificationReasonTimerTaskMap;

    public Notification(UUID uuid) {
        this.uuid = uuid;
        this.notificationReasonTimerTaskMap = new HashMap<>();
    }

    public Map<String, TimerTask> getNotificationReasonTimerTaskMap() {
        return notificationReasonTimerTaskMap;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addNotification(String reason, TimerTask timerTask) {
        this.notificationReasonTimerTaskMap.put(reason, timerTask);
    }

    @Nullable
    public TimerTask getTimerByReason(String notificationReason) {
        return getNotificationReasonTimerTaskMap().getOrDefault(notificationReason, null);
    }

    public void removeNotification(String reason) {
        this.notificationReasonTimerTaskMap.remove(reason);
    }
}
