package ru.velialcult.library.bukkit.file;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class FileRepository {

    private static final List<YamlFile> files;

    static {

        files = new ArrayList<>();

    }

    public static void load(Plugin plugin) {
        files.addAll(Arrays.stream(Objects.requireNonNull(plugin.getDataFolder().listFiles())).filter(File::isFile)
                .filter(file -> file.getName().endsWith(".yml"))
                .map(file -> new YamlFile(plugin, file.getName()))
                .collect(Collectors.toList()));
    }

    public static YamlFile getByName(Plugin plugin, String name) {
        try {
            return get(plugin, name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static YamlFile get(Plugin plugin, String name) throws FileNotFoundException {
        Optional<YamlFile> optional = files.stream()
                .filter(predicate -> predicate.getPlugin().getName().equals(plugin.getName()))
                .filter(predicate -> predicate.getName().equals(name))
                .findFirst();

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new FileNotFoundException("Не удалось найти файл с названием: " + name);
    }

    public static void reload(Plugin plugin) {
        files.stream()
                .filter(yamlFile -> yamlFile.getPlugin().getName().equals(plugin.getName()))
                .forEach(yamlFile -> {
                    yamlFile.reloadConfiguration();
                    yamlFile.saveConfiguration();
                });
    }
}