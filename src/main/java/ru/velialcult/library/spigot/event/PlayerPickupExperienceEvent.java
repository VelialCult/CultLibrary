package ru.velialcult.library.spigot.event;

import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerPickupExperienceEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final ExperienceOrb experienceOrb;

    public PlayerPickupExperienceEvent(Player player, ExperienceOrb experienceOrb) {
        super(player);
        this.experienceOrb = experienceOrb;
    }

    public ExperienceOrb getExperienceOrb() {
        return experienceOrb;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}