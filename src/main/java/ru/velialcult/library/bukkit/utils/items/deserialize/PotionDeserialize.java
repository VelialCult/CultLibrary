package ru.velialcult.library.bukkit.utils.items.deserialize;

import org.bukkit.potion.PotionEffect;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.core.builder.PotionEffectBuilder;

import java.util.Arrays;

/**
 * Класс для десериализации текста в зелья
 *
 * @author Nicholas Alexandrov 03.06.2023
 */
public class PotionDeserialize implements StringDeserialize<PotionEffect> {

    @Override
    public PotionEffect deserialize(String str) {

        PotionEffectBuilder potionEffectBuilder = VersionAdapter.getPotionEffectBuilder();

        String[] array = str.split(":");

        if (Arrays.asList(array).isEmpty()) throw new IllegalArgumentException("Не удалось десериализовать зелье из строки: " + str);

        potionEffectBuilder.setType(array[0].toUpperCase());
        if (array.length > 1) {
            potionEffectBuilder.setDuration(Integer.parseInt(array[1]) * 20);
            if (array.length > 2) {
                potionEffectBuilder.setAmplifier(Integer.parseInt(array[2].trim()));
            }
        }

        return potionEffectBuilder.build();
    }
}
