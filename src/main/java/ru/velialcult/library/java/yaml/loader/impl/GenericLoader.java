package ru.velialcult.library.java.yaml.loader.impl;

import org.yaml.snakeyaml.Yaml;
import ru.velialcult.library.CultLibrary;
import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.annotation.YamlField;
import ru.velialcult.library.java.yaml.loader.Loader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ListLoader
 * Created by Nilsson03 on 07.08.2024
 */

public class GenericLoader<T> implements Loader<T, Map<String, T>> {
    
    @Override
    public Map<String, T> load(YamlFile yamlFile, Class<T> clazz) {
        Yaml yaml = new Yaml();
        Map<String, T> instances = new HashMap<>();
        try (InputStream inputStream = yamlFile.getInputStream()) {
            Map<String, Object> data = yaml.load(inputStream);
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                T instance = clazz.getDeclaredConstructor().newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(YamlField.class)) {
                        YamlField annotation = field.getAnnotation(YamlField.class);
                        String yamlPath = annotation.value();
                        Object value = getValueByPath((Map<String, Object>) entry.getValue(), yamlPath);
                        if (value != null) {
                            field.setAccessible(true);
                            field.set(instance, value);
                        }
                    }
                }
                instances.put(entry.getKey(), instance);
            }
        } catch (Exception e) {
            CultLibrary.getLibrary().getLogger().warning("Произошла ошибка при загрузке данных: " + e.getMessage());
        }
        return instances;
    }
    
    private Object getValueByPath(Map<String, Object> data, String path) {
        String[] keys = path.split("\\.");
        Object value = data;
        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(key);
            } else {
                return null;
            }
        }
        return value;
    }
}
