package ru.velialcult.library.spigot.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import ru.velialcult.library.spigot.event.PlayerPickupExperienceEvent;

import java.util.Comparator;

/**
 * @author Nicholas Alexandrov 28.07.2023
 */
public class PlayerPickupExperienceListener implements Listener {

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        // Найти ближайший опытный шарик к игроку
        ExperienceOrb experienceOrb = player.getWorld().getEntitiesByClass(ExperienceOrb.class).stream()
                .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(player.getLocation())))
                .orElse(null);
        if (experienceOrb != null) {
            // Вызвать событие PlayerPickupExperienceEvent
            PlayerPickupExperienceEvent pickupEvent = new PlayerPickupExperienceEvent(player, experienceOrb);
            Bukkit.getPluginManager().callEvent(pickupEvent);
            if (pickupEvent.isCancelled()) {
                event.setAmount(0);
            }
        }
    }
}
