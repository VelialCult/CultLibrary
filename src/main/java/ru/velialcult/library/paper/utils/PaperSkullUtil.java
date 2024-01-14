package ru.velialcult.library.paper.utils;

import org.bukkit.inventory.meta.SkullMeta;
import ru.velialcult.library.core.util.SkullUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author Nicholas Alexandrov 17.06.2023
 */

public class PaperSkullUtil implements SkullUtils {

    private static final Class<?> bukkit;
    private static final Class<?> profilePropertyСlass;
    private static final Method createProfile;
    private static final Class<?> playerProfileClass;
    private static final Method setPlayerProfile;
    private static final Method setProperty;

    static {

        try {

            bukkit = Class.forName("org.bukkit.Bukkit");
            playerProfileClass = Class.forName("com.destroystokyo.paper.profile.PlayerProfile");
            profilePropertyСlass = Class.forName("com.destroystokyo.paper.profile.ProfileProperty");
            Class<?> skullMeta = SkullMeta.class;

            createProfile = bukkit.getMethod("createProfile", UUID.class, String.class);
            setPlayerProfile = skullMeta.getMethod("setPlayerProfile", playerProfileClass);
            setProperty = playerProfileClass.getMethod("setProperty", profilePropertyСlass);

        } catch (ClassNotFoundException | NoSuchMethodException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public Object setTexture(SkullMeta skullMeta, String texture) {
        try {
            Object playerProfileObject = createProfile.invoke(null, UUID.randomUUID(), "Steve");
            setProperty.invoke(playerProfileObject, profilePropertyСlass.getConstructor(String.class, String.class).newInstance("textures", texture));
            setPlayerProfile.invoke(skullMeta, playerProfileObject);
            return skullMeta;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {

            throw new RuntimeException(e);
        }
    }
}
