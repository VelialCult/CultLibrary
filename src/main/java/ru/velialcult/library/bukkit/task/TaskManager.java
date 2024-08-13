package ru.velialcult.library.bukkit.task;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private final Map<String, Task> tasks = new HashMap<>();
    private final Plugin plugin;

    public TaskManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void scheduleTask(String taskId, Runnable runnable, long delayInTicks) {
        Task task = new Task(runnable, delayInTicks);
        task.schedule(plugin);
        tasks.put(taskId, task);
    }

    public long getTimeRemaining(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            return 0;
        }
        return task.getTimeRemaining();
    }
    
    public Task getTask(String taskId) {
        return tasks.getOrDefault(taskId, null);
    }
    
    public Map<String, Task> getTasks()
    {
        return tasks;
    }
}
