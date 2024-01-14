package ru.velialcult.library.bukkit.utils.items.serialize;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.bukkit.utils.items.ItemUtil;

import java.util.Map;

/**
 * @author Nicholas Alexandrov 18.07.2023
 */
public class ItemStackSerialize implements ObjectSerialize<ItemStack> {

    @Override
    public String serialize(ItemStack object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object.getType()).append(":").append(object.getAmount()).append(":");

        if (object.hasItemMeta()) {
            ItemMeta itemMeta = object.getItemMeta();
            assert itemMeta != null;
            if (itemMeta instanceof Damageable) {
                stringBuilder.append(((Damageable) object.getItemMeta()).getDamage()).append(":");
            }

            if (VersionsUtil.getServerVersion().isNewerThan(VersionsUtil.ServerVersion.v1_14)) {
                if (itemMeta.hasCustomModelData()) {
                    stringBuilder.append(":")
                            .append(itemMeta.getCustomModelData())
                            .append(":");
                }
            }

            if (itemMeta instanceof PotionMeta) {
                PotionMeta potionMeta = (PotionMeta) itemMeta;
                PotionType potionType = potionMeta.getBasePotionData().getType();
                if (potionType != PotionType.AWKWARD && potionType != PotionType.WATER) {
                    PotionEffectType potionEffectType = potionType.getEffectType();
                    PotionEffect potionEffect = potionEffectType.createEffect(potionMeta.getBasePotionData().isExtended() ? 9600 : 3600, potionMeta.getBasePotionData().isUpgraded() ? 1 : 0);
                    stringBuilder.append(potionEffectType.getName())
                            .append(":")
                            .append(potionEffect.getDuration())
                            .append(":")
                            .append(potionEffect.getAmplifier())
                            .append(":");
                }
                if (potionMeta.hasCustomEffects()) {
                    for (PotionEffect potionEffect : potionMeta.getCustomEffects()) {
                        stringBuilder.append(potionEffect.getType().getName())
                                .append(":")
                                .append(potionEffect.getDuration())
                                .append(":")
                                .append(potionEffect.getAmplifier())
                                .append(":");
                    }
                }
            }
        }
        stringBuilder.append(object.getAmount()).append(" ");

        if (ItemUtil.hasEnchants(object)) {
            for (Map.Entry<Enchantment, Integer> entry : object.getEnchantments().entrySet()) {
                stringBuilder.append(entry.getKey().getKey())
                        .append(":")
                        .append(entry.getValue())
                        .append(":");
            }
        }
        return stringBuilder.toString();
    }
}
