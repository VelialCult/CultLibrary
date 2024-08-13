package ru.velialcult.library.java.yaml.storage;

import ru.velialcult.library.CultLibrary;
import ru.velialcult.library.bukkit.file.YamlFile;

/**
 * Storage
 * Created by Nilsson03 on 07.08.2024
 */

public interface Storage
{
    
    CultLibrary cultLibrary = CultLibrary.getLibrary();
    
    YamlFile getFile();
    
    void initialize();
    
    void save();
    
    default void logWarning(String message)
    {
        cultLibrary.getLogger()
                   .warning(message);
    }
    
}
