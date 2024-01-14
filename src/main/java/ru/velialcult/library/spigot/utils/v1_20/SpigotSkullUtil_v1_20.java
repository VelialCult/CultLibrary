package ru.velialcult.library.spigot.utils.v1_20;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import ru.velialcult.library.core.util.SkullUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * @author Nicholas Alexandrov 17.06.2023
 */
public class SpigotSkullUtil_v1_20 implements SkullUtils {


    @Override
    public Object setTexture(SkullMeta skullMeta, String texture) {

        try {
            PlayerProfile playerProfile = Bukkit.createPlayerProfile(UUID.randomUUID(), "Steve");
            PlayerTextures textures = playerProfile.getTextures();
            textures.setSkin(new URL(texture));
            playerProfile.setTextures(textures);
            skullMeta.setOwnerProfile(playerProfile);
            return skullMeta;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
