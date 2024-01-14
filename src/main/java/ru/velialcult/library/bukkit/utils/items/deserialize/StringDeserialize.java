package ru.velialcult.library.bukkit.utils.items.deserialize;

/**
 * @author Nicholas Alexandrov 15.06.2023
 */
public interface StringDeserialize<T> {

    T deserialize(String str);
}
