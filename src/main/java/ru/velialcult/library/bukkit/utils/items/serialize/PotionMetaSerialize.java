package ru.velialcult.library.bukkit.utils.items.serialize;

import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

/**
 * @author Nicholas Alexandrov 18.07.2023
 */
public class PotionMetaSerialize implements ObjectSerialize<PotionMeta> {

    @Override
    public String serialize(PotionMeta object) {

        StringBuilder stringBuilder = new StringBuilder();

        for (PotionEffect potionEffect : object.getCustomEffects()) {
                    stringBuilder.append(potionEffect.getType())
                    .append(":")
                    .append(potionEffect.getDuration())
                    .append(":")
                    .append(potionEffect.getAmplifier())
                    .append(" ");
        }

        return stringBuilder.toString();
    }
}
