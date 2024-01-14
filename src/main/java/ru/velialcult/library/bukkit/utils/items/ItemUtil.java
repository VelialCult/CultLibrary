package ru.velialcult.library.bukkit.utils.items;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.bukkit.utils.items.deserialize.ItemStackDeserialize;
import ru.velialcult.library.bukkit.utils.items.serialize.ItemStackSerialize;
import ru.velialcult.library.bukkit.utils.items.serialize.PotionEffectSerialize;

import java.util.Map;

/**
 * @author Nicholas Alexandrov 01.06.2023
 */
public class ItemUtil {

    public static String potionEffectSerialize(PotionEffect potionEffect) {
        return new PotionEffectSerialize().serialize(potionEffect);
    }

    public static String itemStackSerialize(ItemStack itemStack) {
        return new ItemStackSerialize().serialize(itemStack);
    }

    public static ItemStack itemStackDeserialize(String string) {
        return new ItemStackDeserialize().deserialize(string);
    }

    public static boolean hasEnchants(ItemStack itemStack) {

        return getEnchants(itemStack).isEmpty();
    }

    public static Map<Enchantment, Integer> getEnchants(ItemStack itemStack) {

        return itemStack.getEnchantments();
    }

    public static boolean areSimilar(ItemStack item1, ItemStack item2, NamespacedKey... ignoredKeys) {
        // Сравнение типов предметов
        if (!item1.getType().equals(item2.getType())) {
            return false;
        }

        // Сравнение ItemMeta
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();

        if (meta1 == null || meta2 == null) {
            return meta1 == meta2;
        }

        if (VersionsUtil.getServerVersion().isNewerEqualThanV1_14()) {
            if (meta1.hasCustomModelData() && meta2.hasCustomModelData()) {
                return meta1.getCustomModelData() == meta2.getCustomModelData();
            }
        }

        if (!meta1.getDisplayName().equals(meta2.getDisplayName())) {
            return false;
        }

        // Сравнение PersistentDataContainer
        PersistentDataContainer data1 = meta1.getPersistentDataContainer();
        PersistentDataContainer data2 = meta2.getPersistentDataContainer();

        for (NamespacedKey key : data1.getKeys()) {
            if (!isIgnored(key, ignoredKeys) && !data1.get(key, PersistentDataType.STRING).equals(data2.get(key, PersistentDataType.STRING))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isIgnored(NamespacedKey key, NamespacedKey... ignoredKeys) {
        for (NamespacedKey ignoredKey : ignoredKeys) {
            if (key.equals(ignoredKey)) {
                return true;
            }
        }
        return false;
    }
}