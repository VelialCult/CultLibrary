package ru.velialcult.library.java.yaml.storage;

import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.loader.impl.GenericLoader;
import ru.velialcult.library.java.yaml.saver.Saver;

import java.util.List;
import java.util.Map;

/**
 * ListStorage
 * Created by Nilsson03 on 07.08.2024
 */

public interface PairStorage<T> extends Storage
{
    
    Map<String, T> getMap();
    
    GenericLoader<T> getLoader();
    
    @Override
    YamlFile getFile();
    
    Class<T> getTypeClass();
    
    
    Saver<Map<String, T>> getSaver();
    
    @Override
    default void initialize()
    {
        Map<String, T> list = getMap();
        if (list == null) {
            logWarning("При попытке загрузки хранилище не найдено.");
            return;
        }
        
        Map<String, T> data = getLoader().load(getFile(), getTypeClass());
        if (data.isEmpty()) {
            logWarning("При попытке загрузки данные не найдены, хранилище будет создано пустым.");
        }
        
        list.putAll(data);
    }
    
    default void add(String key, T value)
    {
        getMap().put(key, value);
    }
    
    default void remove(String key)
    {
        getMap().remove(key);
    }
    
}
