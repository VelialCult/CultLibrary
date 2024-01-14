package ru.velialcult.library.bukkit.utils.items.deserialize;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.core.builder.PotionEffectBuilder;

import java.util.Optional;

public class ItemStackDeserialize implements StringDeserialize<ItemStack> {

    @Override
    public ItemStack deserialize(String str) {
        String[] array = str.split(" ");
        String[] item = array[0].split(":");
        ItemStack itemStack;
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(item[0]);
        if (xMaterial.isPresent()) {
            Material material = xMaterial.get().parseMaterial();
            if (item.length > 1) {
                itemStack = new ItemStack(material, Integer.parseInt(item[1]));
            } else {
                itemStack = new ItemStack(material);
            }
        } else {
            throw new NullPointerException("Не удалось опознать материал " + item[0]);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof Damageable && item.length > 2) {
            ((Damageable) itemMeta).setDamage(Integer.parseInt(item[2]));
        }

        if (VersionsUtil.getServerVersion().isNewerThan(VersionsUtil.ServerVersion.v1_14)) {
            if (array[0].split(":").length > 3) {
                itemMeta.setCustomModelData(Integer.parseInt(item[3]));
            }
        }

        if (itemStack.getType() == Material.POTION || itemStack.getType() == Material.SPLASH_POTION) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;

            for (int i = 1; i < array.length; i++) {
                String effect = array[i];
                PotionEffectBuilder potionEffectBuilder = VersionAdapter.getPotionEffectBuilder();
                String[] arrayEffect = effect.split(":");
                potionEffectBuilder.setType(arrayEffect[0].toUpperCase());
                if (arrayEffect.length > 1) {
                    potionEffectBuilder.setDuration(Integer.parseInt(arrayEffect[1]) * 20);
                    if (arrayEffect.length > 2) {
                        potionEffectBuilder.setAmplifier(Integer.parseInt(arrayEffect[2]));
                    }
                }

                potionMeta.addCustomEffect(potionEffectBuilder.build(), true);
            }

            itemStack.setItemMeta(potionMeta);
        } else {
            for (int i = 1; i < array.length; i++) {
                String[] enchantmentData = array[i].split(":");
                if (enchantmentData.length == 2) {
                    Enchantment enchantment = XEnchantment.matchXEnchantment(enchantmentData[0]).get().getEnchant();
                    int level = Integer.parseInt(enchantmentData[1]);
                    itemStack.addEnchantment(enchantment, level);
                }
            }
        }

        return itemStack;
    }
}
