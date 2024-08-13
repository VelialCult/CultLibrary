package ru.velialcult.library.java.yaml.saver.impl;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import ru.velialcult.library.CultLibrary;
import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.annotation.YamlField;
import ru.velialcult.library.java.yaml.saver.ListSaver;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ListSaverImpl
 * Created by Nilsson03 on 07.08.2024
 */

public class ListSaverImpl<T> implements ListSaver<T, List<T>>
{
    
    @Override
    public void save(List<T> ts, YamlFile yamlFile) {
        if (ts.isEmpty()) {
            return;
        }
        
        T instance = ts.get(0);
        Map<String, Object> data = new HashMap<>();
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(YamlField.class)) {
                YamlField annotation = field.getAnnotation(YamlField.class);
                String yamlPath = annotation.value();
                field.setAccessible(true);
                try {
                    setValueByPath(data, yamlPath, field.get(instance));
                } catch (IllegalAccessException e) {
                    CultLibrary.getLibrary().getLogger().warning("Произошла ошибка при сохранении значений: " + e.getMessage());
                }
            }
        }
        
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer();
        Yaml yaml = new Yaml(representer, options);
        
        try (FileWriter writer = new FileWriter(yamlFile.getFile())) {
            yaml.dump(data, writer);
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
