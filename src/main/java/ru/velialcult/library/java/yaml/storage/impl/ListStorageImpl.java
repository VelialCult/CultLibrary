package ru.velialcult.library.java.yaml.storage.impl;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ru.velialcult.library.bukkit.file.FileRepository;
import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.loader.ListLoader;
import ru.velialcult.library.java.yaml.loader.impl.ListLoaderImpl;
import ru.velialcult.library.java.yaml.saver.ListSaver;
import ru.velialcult.library.java.yaml.saver.impl.ListSaverImpl;
import ru.velialcult.library.java.yaml.storage.ListStorage;

import java.util.*;

/**
 * ListStorageImpl
 * Created by Nilsson03 on 07.08.2024
 */

public class ListStorageImpl<T> implements ListStorage<T> {
    
    private final YamlFile yamlFile;
    private final List<T> list;
    private final ListLoader<T> listLoader;
    private final ListSaver<T, List<T>> saver;
    
    public ListStorageImpl(Plugin plugin, String fileName, T instance) {
        Objects.requireNonNull(plugin, "Plugin cannot be null!");
        Objects.requireNonNull(fileName, "FileName cannot be null!");
        Objects.requireNonNull(instance, "Instance cannot be null!");
        
        this.list = new ArrayList<>();
        this.yamlFile = FileRepository.getByName(plugin, fileName);
        this.saver = new ListSaverImpl<>();
        this.listLoader = new ListLoaderImpl<>(instance);
        
        this.list.addAll(listLoader.load(yamlFile));
    }
    
    @Override
    public List<T> getList() {
        return list;
    }
    
    @Override
    public ListLoader<T> getLoader() {
        return listLoader;
    }
    
    @Override
    public YamlFile getFile() {
        return yamlFile;
    }
    
    @Override
    public void save() {
        saver.save(list, yamlFile);
    }
    
    @Override
    public ListSaver<T, List<T>> getSaver() {
        return saver;
    }
}
