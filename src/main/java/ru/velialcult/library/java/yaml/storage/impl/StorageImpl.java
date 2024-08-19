package ru.velialcult.library.java.yaml.storage.impl;

import org.bukkit.plugin.Plugin;
import ru.velialcult.library.bukkit.file.FileRepository;
import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.loader.impl.GenericLoader;
import ru.velialcult.library.java.yaml.saver.Saver;
import ru.velialcult.library.java.yaml.saver.impl.GenericSaver;
import ru.velialcult.library.java.yaml.storage.PairStorage;

import java.util.*;

/**
 * ListStorageImpl
 * Created by Nilsson03 on 07.08.2024
 */

public class StorageImpl<T extends Class<T>> implements PairStorage<T>
{
    
    private final T instance;
    private final YamlFile yamlFile;
    private final Map<String, T> map;
    private final GenericLoader<T> genericLoader;
    private final Saver<Map<String, T>> saver;
    
    public StorageImpl(Plugin plugin, String fileName, T instance) {
        Objects.requireNonNull(plugin, "Plugin cannot be null!");
        Objects.requireNonNull(fileName, "FileName cannot be null!");
        Objects.requireNonNull(instance, "Instance cannot be null!");
        
        this.instance = instance;
        this.map = new HashMap<>();
        this.yamlFile = FileRepository.getByName(plugin, fileName);
        this.saver = new GenericSaver<>();
        this.genericLoader = new GenericLoader<>();
        
        this.map.putAll(genericLoader.load(yamlFile, instance));
    }
    
    
    @Override
    public Map<String, T> getMap()
    {
        return map;
    }
    
    @Override
    public GenericLoader<T> getLoader() {
        return genericLoader;
    }
    
    @Override
    public YamlFile getFile() {
        return yamlFile;
    }
    
    @Override
    public Class<T> getTypeClass()
    {
        return instance;
    }
    
    @Override
    public void save() {
        saver.save(map, yamlFile);
    }
    
    @Override
    public Saver<Map<String, T>> getSaver()
    {
        return saver;
    }
}
