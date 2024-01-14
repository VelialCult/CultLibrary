package ru.velialcult.library.spigot.utils.universal;

import com.cryptomorin.xseries.XSound;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.velialcult.library.bukkit.utils.text.ActionBar;
import ru.velialcult.library.bukkit.utils.text.Title;
import ru.velialcult.library.core.VersionAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Nicholas Alexandrov 20.06.2023
 */
public class SpigotMessageUtil {

    public void sendMessage(CommandSender commandSender, String object) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String[] strings = object.split(";");
            Arrays.stream(strings).forEach(string -> {
                if (string.startsWith("sound:")) {
                    string = string.substring("sound:".length());
                    Optional<XSound> optional = XSound.matchXSound(string.toUpperCase());
                    optional.ifPresent(xSound -> {
                        assert xSound.parseSound() != null;
                        player.playSound(player.getLocation(), xSound.parseSound(), 2, 1);
                    });
                } else if (string.startsWith("title:")) {
                    Title.sendTitle(player, string.substring("title:".length()));
                } else if (string.startsWith("actionbar:")) {
                    ActionBar.sendActionBar(player, string.substring("actionbar:".length()));
                } else if (!string.trim().isEmpty()) { // проверка на пустую строку
                    player.sendMessage(VersionAdapter.TextUtil().colorize(string));
                }
            });
        }
    }

    public void sendMessage(CommandSender commandSender, List<String> text) {
        text.forEach(str -> sendMessage(commandSender, str));
    }

    public void sendMessage(CommandSender commandSender, BaseComponent[] baseComponent) {
        commandSender.spigot().sendMessage(baseComponent);
    }
}
