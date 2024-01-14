package ru.velialcult.library.bukkit.utils.items.serialize;

import org.bukkit.potion.PotionEffect;

/**
 * @author Nicholas Alexandrov 18.07.2023
 */
public class PotionEffectSerialize implements ObjectSerialize<PotionEffect> {
    @Override
    public String serialize(PotionEffect object) {

        return object.getType().getName() +
                ":" +
                object.getDuration() +
                ":" +
                object.getAmplifier() +
                " ";
    }
}
