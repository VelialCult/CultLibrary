package ru.velialcult.library;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.velialcult.library.bukkit.inventory.PlayerInputHandler;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.spigot.listener.PlayerPickupExperienceListener;
import ru.velialcult.library.update.UpdateChecker;

public class CultLibrary  extends JavaPlugin {

    private static CultLibrary library;

    public void onEnable() {
        library = this;
        if (!VersionsUtil.getServerVersion().isNewerEqualThan(VersionsUtil.ServerVersion.v1_16)) {
            getLogger().severe("Ваша версия ядра Minecraft не поддерживается. Используйте 1.16 или выше");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        UpdateChecker updateChecker = new UpdateChecker(this, "CultLibrary");
        updateChecker.check();

        Bukkit.getPluginManager().registerEvents(new PlayerPickupExperienceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInputHandler(), this);
    }

    public static CultLibrary getLibrary() {
        return library;
    }
}
