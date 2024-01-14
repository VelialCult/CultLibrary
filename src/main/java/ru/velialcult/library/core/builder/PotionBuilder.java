package ru.velialcult.library.core.builder;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.List;

/**
 * @author Nicholas Alexandrov 27.05.2023
 */
public interface PotionBuilder {

    PotionBuilder setDisplayName(String displayName);

    PotionBuilder setLore(List<String> lore);

    PotionBuilder addLine(String line);

    PotionBuilder setColor(Color color);

    PotionBuilder setAmount(int amount);

    PotionBuilder addCustomEffect(PotionEffect effect);

    PotionBuilder setType(PotionType type, boolean extended, boolean upgraded);

    ItemStack build();
}
