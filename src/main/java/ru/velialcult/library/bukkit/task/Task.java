package ru.velialcult.library.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Task {

    private final Runnable runnable;
    private final long delayInTicks;
    private BukkitTask bukkitTask;
    private long taskStartTime;

    public Task(Runnable runnable, long delayInTicks) {
        this.runnable = runnable;
        this.delayInTicks = delayInTicks;
    }

    public void schedule(Plugin plugin) {
        taskStartTime = System.currentTimeMillis();
        bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, runnable, delayInTicks);
    }

    public long getTimeRemaining() {
        if (bukkitTask == null) {
            return 0;
        }
        long elapsedTime = System.currentTimeMillis() - taskStartTime;
        return (delayInTicks * 50 - elapsedTime) / 1000; // переводим миллисекунды в секунды
    }
}
