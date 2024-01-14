package ru.velialcult.library.bukkit.utils.items.serialize;

/**
 * @author Nicholas Alexandrov 18.07.2023
 */
public interface ObjectSerialize<T> {

    String serialize(T object);
}
