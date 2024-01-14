package ru.velialcult.library.bukkit.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import ru.velialcult.library.bukkit.utils.ConfigurationUtil;

import java.io.File;

/**
 * @author Nicholas Alexandrov 18.06.2023
 */
public class YamlFile {

    private final String name;
    private final File file;
    private FileConfiguration configuration;
    private final Plugin plugin;

    public YamlFile(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(fileName);
        this.name = fileName;
        this.configuration = ConfigurationUtil.loadConfiguration(plugin, name);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public void saveConfiguration() {
        ConfigurationUtil.saveFile(configuration, plugin.getDataFolder().getAbsolutePath(), name);
    }

    public void reloadConfiguration() {
        this.configuration = ConfigurationUtil.loadConfiguration(plugin, name);
    }
}
