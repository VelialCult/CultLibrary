package ru.velialcult.library.bukkit.utils.text;

import org.bukkit.entity.Player;
import ru.velialcult.library.core.VersionAdapter;

import java.util.Objects;

public class Title {

    public static void sendTitle(Player player, String text, int fadeIn, int stay, int fadeOut) {
        if (text == null) return;
        text = VersionAdapter.TextUtil().colorize(text);
        String[] args = text.split("%new%");
        if (args.length == 1) {
            player.sendTitle(args[0], " ", fadeIn, stay, fadeOut);
        } else if (args.length >= 2) {
            player.sendTitle(args[0], args[1], fadeIn, stay, fadeOut);
        }
    }

    public static void sendTitle(Player player, String text) {

        Objects.requireNonNull(player, "Player can not be null");

        sendTitle(player, text, 10, 20, 10);
    }

    public static void clearTitle(Player player) {

        Objects.requireNonNull(player, "Player can not be null");

        sendTitle(player, "");
    }
}
