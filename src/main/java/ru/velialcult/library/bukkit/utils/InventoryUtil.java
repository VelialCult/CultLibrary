package ru.velialcult.library.bukkit.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.java.text.ReplaceData;

import java.util.List;

public class InventoryUtil {

    public static ItemStack createItem(FileConfiguration fileConfiguration, OfflinePlayer offlinePlayer, String path, ReplaceData... replaceData) {
        ItemStack itemStack;
        String materialName = fileConfiguration.getString(path + ".item.material");
        String url = fileConfiguration.getString(path + ".item.head");
        String displayName = VersionAdapter.TextUtil().colorize(VersionAdapter.TextUtil().setReplaces(fileConfiguration.getString(path + ".displayName"), replaceData));
        List<String> lore = VersionAdapter.TextUtil().colorize(VersionAdapter.TextUtil().setReplaces(fileConfiguration.getStringList(path + ".displayName"), replaceData));
        int customModelData = fileConfiguration.getInt(path + ".customModelData", 0);

        if (materialName.equalsIgnoreCase("head")) {
            itemStack = VersionAdapter.getSkullBuilder()
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .setTexture(url)
                    .build();

        } else {
            itemStack  = VersionAdapter.getItemBuilder()
                    .setType(materialName)
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .setCustomModelData(customModelData)
                    .build();
        }

        return itemStack;
    }
}
