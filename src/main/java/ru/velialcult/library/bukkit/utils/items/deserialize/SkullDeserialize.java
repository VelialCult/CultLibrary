package ru.velialcult.library.bukkit.utils.items.deserialize;

import org.bukkit.inventory.ItemStack;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.core.builder.SkullBuilder;

/**
 * Класс для десериализации текста в головы
 *
 * @author Nicholas Alexandrov 04.06.2023
 */
public class SkullDeserialize implements StringDeserialize<ItemStack> {

    private final SkullBuilder skullBuilder = VersionAdapter.getSkullBuilder();

    @Override
    public ItemStack deserialize(String str) {

        String[] array = str.split(":");

        if (array.length == 1) {

            return skullBuilder.setTexture(array[0]).build();
        }

        throw new IllegalArgumentException("The string could not be deserialized to the ItemStack object because the wrong number of aggregates was specified");
    }
}
