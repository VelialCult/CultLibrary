package ru.velialcult.library.java.yaml.storage;

import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.loader.ListLoader;
import ru.velialcult.library.java.yaml.saver.ListSaver;

import java.util.List;

/**
 * ListStorage
 * Created by Nilsson03 on 07.08.2024
 */

public interface ListStorage<T> extends Storage
{
    
    List<T> getList();
    
    ListLoader<T> getLoader();
    
    @Override
    YamlFile getFile();
    
    ListSaver<T, List<T>> getSaver();
    
    @Override
    default void initialize()
    {
        List<T> list = getList();
        if (list == null) {
            logWarning("При попытке загрузки хранилище не найдено.");
            return;
        }
        
        List<T> data = getLoader().load(getFile());
        if (data.isEmpty()) {
            logWarning("При попытке загрузки данные не найдены, хранилище будет создано пустым.");
        }
        
        list.addAll(data);
    }
    
    default void add(T value)
    {
        getList().add(value);
    }
    
    default void remove(T key)
    {
        getList().remove(key);
    }
    
}
