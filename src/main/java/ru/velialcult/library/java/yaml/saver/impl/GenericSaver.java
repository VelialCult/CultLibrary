package ru.velialcult.library.java.yaml.saver.impl;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import ru.velialcult.library.CultLibrary;
import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.annotation.YamlField;
import ru.velialcult.library.java.yaml.saver.Saver;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ListSaverImpl
 * Created by Nilsson03 on 07.08.2024
 */

public class GenericSaver<T> implements Saver<Map<String, T>>
{
    
    @Override
    public void save(Map<String, T> data, YamlFile yamlFile) {
        Map<String, Object> yamlData = new HashMap<>();
        for (Map.Entry<String, T> entry : data.entrySet()) {
            Map<String, Object> instanceData = new HashMap<>();
            T instance = entry.getValue();
            for (Field field : instance.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(YamlField.class)) {
                    YamlField annotation = field.getAnnotation(YamlField.class);
                    String yamlPath = annotation.value();
                    field.setAccessible(true);
                    try {
                        setValueByPath(instanceData, yamlPath, field.get(instance));
                    } catch (IllegalAccessException e) {
                        CultLibrary.getLibrary().getLogger().warning("Произошла ошибка при сохранении значений: " + e.getMessage());
                    }
                }
            }
            yamlData.put(entry.getKey(), instanceData);
        }
        
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer();
        Yaml yaml = new Yaml(representer, options);
        
        try (FileWriter writer = new FileWriter(yamlFile.getFile())) {
            yaml.dump(yamlData, writer);
        } catch (IOException e) {
            CultLibrary.getLibrary().getLogger().warning("Произошла ошибка при сохранении значений: " + e.getMessage());
        }
    }
    
    private void setValueByPath(Map<String, Object> data, String path, Object value) {
        String[] keys = path.split("\\.");
        Map<String, Object> currentMap = data;
        for (int i = 0; i < keys.length - 1; i++) {
            currentMap = (Map<String, Object>) currentMap.computeIfAbsent(keys[i], k -> new HashMap<>());
        }
        currentMap.put(keys[keys.length - 1], value);
    }
}
