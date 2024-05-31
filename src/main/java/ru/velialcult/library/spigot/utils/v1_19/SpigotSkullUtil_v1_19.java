package ru.velialcult.library.spigot.utils.v1_19;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.inventory.meta.SkullMeta;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.core.util.SkullUtils;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Nicholas Alexandrov 17.06.2023
 */
public class SpigotSkullUtil_v1_19 implements SkullUtils {

    private static final Field profileField;

    static {

        try {
            profileField = Class.forName("org.bukkit.craftbukkit." + VersionsUtil.SERVER_VERSION + ".inventory.CraftMetaSkull").getDeclaredField("profile");
            profileField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object setTexture(SkullMeta meta, String texture) {
        try {
            profileField.set(meta, createProfile(texture));
            return meta;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object createProfile(String texture) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Steve");
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
        gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        return gameProfile;
    }

    public String getTexture(SkullMeta skullMeta) {
        try {
            GameProfile profile = (GameProfile) profileField.get(skullMeta);
            Property property = profile.getProperties().get("textures").iterator().next();
            return property.getValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
