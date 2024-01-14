package ru.velialcult.library.core.builder;

import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Nicholas Alexandrov 27.05.2023
 */
public interface PotionEffectBuilder {

    PotionEffectBuilder setAmbient(boolean bool);

    PotionEffectBuilder setAmplifier(int amplifier);

    PotionEffectBuilder setDuration(int duration);

    PotionEffectBuilder setIcon(boolean icon);

    PotionEffectBuilder setParticles(boolean particles);

    PotionEffectBuilder setType(PotionEffectType type);

    PotionEffectBuilder setType(String type);

    PotionEffectBuilder setColor(Color color);

    PotionEffect build();
}
