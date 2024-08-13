package ru.velialcult.library.java.yaml.loader.impl;

import org.yaml.snakeyaml.Yaml;
import ru.velialcult.library.CultLibrary;
import ru.velialcult.library.bukkit.file.YamlFile;
import ru.velialcult.library.java.yaml.loader.ListLoader;
import ru.velialcult.library.java.yaml.annotation.YamlField;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * ListLoaderImpl
 * Created by Nilsson03 on 07.08.2024
 */

public class ListLoaderImpl<T> implements ListLoader<T>
{
    
    private final T instance;
    
    public ListLoaderImpl(T instance) {
        this.instance = instance;
    }
    
    @Override
    public List<T> load(YamlFile yamlFile) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = yamlFile.getInputStream()) {
            Map<String, Object> data = yaml.load(inputStream);
            T instance = this.instance;
            for (Field field : instance.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(YamlField.class)) {
                    YamlField annotation = field.getAnnotation(YamlField.class);
                    String yamlPath = annotation.value();
                    Object value = getValueByPath(data, yamlPath);
                    if (value != null) {
                        field.setAccessible(true);
                        field.set(instance, value);
                    }
                }
            }
            return List.of(instance);
        } catch (Exception e) {
            CultLibrary.getLibrary().getLogger().warning("Произошла ошибка при загрузке данных в ListLoader: " + e.getMessage());
            return List.of();
        }
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
