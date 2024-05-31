package ru.velialcult.library.core;

import org.bukkit.Bukkit;
import ru.velialcult.library.bukkit.notification.NotificationService;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.core.builder.*;
import ru.velialcult.library.core.util.SkullUtils;
import ru.velialcult.library.paper.PaperAdapter;
import ru.velialcult.library.spigot.SpigotAdapter;
import ru.velialcult.library.spigot.utils.universal.SpigotInventoryUtil;
import ru.velialcult.library.spigot.utils.universal.SpigotMessageUtil;
import ru.velialcult.library.spigot.utils.universal.SpigotTextUtil;

public class VersionAdapter {

    private static final SpigotAdapter adapter;

    private static final String version = Bukkit.getVersion();

    static {

        adapter = (version.contains("Paper") || version.contains("Purpur")) ? new PaperAdapter() : new SpigotAdapter();
    }

    public static SpigotTextUtil TextUtil(){
        return adapter.getTextUtil();
    }

    public static PotionEffectBuilder getPotionEffectBuilder() {

        return adapter.getPotionEffectBuilder();
    }

    public static NotificationService getNotificationService() {

        return adapter.getNotificationService();
    }

    public static PotionBuilder getPotionBuilder() {

        return adapter.getPotionBuilder();
    }

    public static SkullBuilder getSkullBuilder() {

        return adapter.getSkullBuilder();
    }

    public static ItemBuilder getItemBuilder() {

        return adapter.getItemBuilder();
    }

    public static MessageBuilder getMessageBuilder(String text) {

        return adapter.MessageBuilder();
    }

    public static MessageBuilder getMessageBuilder() {

        return adapter.MessageBuilder();
    }

    public static SkullUtils SkullUtils() {

        return adapter.SkullUtils();
    }

     public static SpigotInventoryUtil InventoryUtils() {

        return adapter.InventoryUtils();
     }

     public static SpigotMessageUtil MessageUtils() {

        return adapter.MessageUtils();
     }
}
