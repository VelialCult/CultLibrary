package ru.velialcult.library.java.yaml.loader;

import ru.velialcult.library.bukkit.file.YamlFile;

import java.util.List;

/**
 * ListLoader
 * Created by Nilsson03 on 07.08.2024
 */

public interface ListLoader<T> extends Loader<List<T>>
{
    
    @Override
    List<T> load(YamlFile yamlFile);
    
}
