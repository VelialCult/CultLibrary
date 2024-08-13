package ru.velialcult.library.java.yaml.loader;

import ru.velialcult.library.bukkit.file.YamlFile;

/**
 * Loader
 * Created by Nilsson03 on 07.08.2024
 */

public interface Loader<T>
{
    
    T load(YamlFile yamlFile);
}
