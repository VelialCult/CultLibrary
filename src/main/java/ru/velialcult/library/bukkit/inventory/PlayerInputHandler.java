package ru.velialcult.library.bukkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.velialcult.library.CultLibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerInputHandler implements Listener {

    private static final Map<UUID, Consumer<String>> settingMap = new HashMap<>();

    public static void addPlayer(Player player, Consumer<String> callback) {
        UUID playerId = player.getUniqueId();
        settingMap.put(playerId, callback);
    }

    public static void applySetting(Player player, String message) {
        Consumer<String> callback = settingMap.get(player.getUniqueId());
        if (callback != null) {
            callback.accept(message);
            settingMap.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();

        if (settingMap.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().runTask(CultLibrary.getLibrary(), () -> {
                applySetting(player, message);

            });
            e.setCancelled(true);
        }
    }
}

