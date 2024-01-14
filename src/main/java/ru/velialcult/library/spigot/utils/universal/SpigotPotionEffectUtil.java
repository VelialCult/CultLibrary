package ru.velialcult.library.spigot.utils.universal;

import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Nicholas Alexandrov 17.06.2023
 */
public class SpigotPotionEffectUtil {

    private static final Class<?> potionEffect;

    private static final Constructor<?> constructor_v1_12;

    static {

        try {

            potionEffect = Class.forName("org.bukkit.potion.PotionEffect");

            constructor_v1_12 = potionEffect.getConstructor(PotionEffectType.class, int.class, int.class, boolean.class, boolean.class, Color.class);

        } catch (ClassNotFoundException | NoSuchMethodException e) {

            throw new RuntimeException(e);
        }
    }

    public static PotionEffect newPotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, Color color) {

        try {

            return (PotionEffect) constructor_v1_12.newInstance(type, duration, amplifier, ambient, particles, color);

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {

            throw new RuntimeException(e);
        }
    }
}
