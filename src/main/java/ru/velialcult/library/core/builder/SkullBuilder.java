package ru.velialcult.library.core.builder;

import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Nicholas Alexandrov 25.05.2023
 */
public interface SkullBuilder {

    SkullBuilder setTexture(String texture);

    SkullBuilder setDisplayName(String displayName);

    SkullBuilder setLore(List<String> lore);

    SkullBuilder addLine(String line);

    SkullBuilder setOwner(OfflinePlayer offlinePlayer);

    SkullBuilder addEnchant(Enchantment enchantment, int level);

    SkullBuilder removeEnchant(Enchantment enchantment);

    SkullBuilder addFlag(ItemFlag flag);

    SkullBuilder setUnbreakable(boolean bool);

    ItemStack build();
}
