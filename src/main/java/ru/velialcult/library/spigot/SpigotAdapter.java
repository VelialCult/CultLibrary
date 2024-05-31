package ru.velialcult.library.spigot;

import ru.velialcult.library.bukkit.notification.NotificationService;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.core.builder.*;
import ru.velialcult.library.core.util.SkullUtils;
import ru.velialcult.library.spigot.builder.universal.SpigotMessageBuilder;
import ru.velialcult.library.spigot.builder.universal.SpigotPotionBuilder;
import ru.velialcult.library.spigot.builder.universal.SpigotSkullBuilder;
import ru.velialcult.library.spigot.builder.universal.SpigotItemBuilder;
import ru.velialcult.library.spigot.builder.universal.SpigotPotionEffectBuilder;
import ru.velialcult.library.spigot.utils.universal.SpigotInventoryUtil;
import ru.velialcult.library.spigot.utils.universal.SpigotMessageUtil;
import ru.velialcult.library.spigot.utils.universal.SpigotTextUtil;
import ru.velialcult.library.spigot.utils.v1_19.SpigotSkullUtil_v1_19;
import ru.velialcult.library.spigot.utils.v1_20.SpigotSkullUtil_v1_20;

/**
 * @author Nicholas Alexandrov 27.06.2023
 */
public class SpigotAdapter {

    private final SkullUtils skullUtils;

    private final SpigotInventoryUtil inventoryUtils;

    private final SpigotMessageUtil messageUtils;

    private final SpigotTextUtil textUtil;

    private final Class<? extends MessageBuilder> messageBuilder;

    private final Class<? extends ItemBuilder> itemBuilder;

    private final Class<? extends PotionBuilder> potionBuilder;

    private final Class<? extends SkullBuilder> skullBuilder;

    private final Class<? extends PotionEffectBuilder> potionEffectBuilder;

    private final NotificationService notificationService;

    {
        textUtil = new SpigotTextUtil();


        this.itemBuilder = SpigotItemBuilder.class;

        skullBuilder = SpigotSkullBuilder.class;

        this.potionEffectBuilder = SpigotPotionEffectBuilder.class;

        this.potionBuilder = SpigotPotionBuilder.class;


        skullUtils = VersionsUtil.getServerVersion().isNewerEqualThanV1_20() ? new SpigotSkullUtil_v1_20() : new SpigotSkullUtil_v1_19();

        inventoryUtils = new SpigotInventoryUtil();

        messageUtils = new SpigotMessageUtil();

        messageBuilder = SpigotMessageBuilder.class;

        notificationService = new NotificationService();
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public SpigotTextUtil getTextUtil() {
        return textUtil;
    }

    public ItemBuilder getItemBuilder() {

        try {

            return itemBuilder.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    public  PotionBuilder getPotionBuilder() {
        try {

            return potionBuilder.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    public  PotionEffectBuilder getPotionEffectBuilder() {
        try {

            return potionEffectBuilder.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    public  SkullBuilder getSkullBuilder() {
        try {

            return skullBuilder.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    public SpigotMessageUtil MessageUtils() {
        return messageUtils;
    }

    public SkullUtils SkullUtils() {
        return skullUtils;
    }

    public SpigotInventoryUtil InventoryUtils() {
        return inventoryUtils;
    }

    public MessageBuilder MessageBuilder() {

        try {

            return messageBuilder.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }
}
