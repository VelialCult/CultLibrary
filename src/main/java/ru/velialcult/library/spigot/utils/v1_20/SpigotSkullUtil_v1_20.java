package ru.velialcult.library.spigot.utils.v1_20;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.core.util.SkullUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author Nicholas Alexandrov 17.06.2023
 */
public class SpigotSkullUtil_v1_20 implements SkullUtils {


    @Override
    public Object setTexture(SkullMeta skullMeta, String texture) {

        try {
            Object profile = Class.forName("org.bukkit.craftbukkit." + VersionsUtil.SERVER_VERSION  + ".CraftProfile").newInstance();
            Method m = profile.getClass().getDeclaredMethod("setTextures", URL.class);
            m.setAccessible(true);
            m.invoke(profile, new URL(texture));

            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);

            return skullMeta;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
