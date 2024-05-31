package ru.velialcult.library.bukkit.schematic;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.bukkit.Location;
import org.bukkit.Material;

public class SchematicService {

    public static Location pasteSchematicWithGetBlock(Clipboard clipboard, Location location, Material material) {
        SchematicPaster schematicPaster = new SchematicPaster();
        schematicPaster.pasteSchematicWithGetBlock(clipboard, location, material);
        return schematicPaster.getChestLocation();
    }

    public static org.bukkit.World performPaste(Clipboard clipboard, Location location) {
        SchematicPaster schematicPaster = new SchematicPaster();
        return schematicPaster.pasteSchematic(clipboard, location);
    }

    public static Clipboard loadSchematic(String filaPath) {
        File file = new File(filaPath);
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try {
            assert format != null;
            try (ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()))) {
                return reader.read();
            }
        } catch (IOException e) {
            System.out.println("Не удалось загрузить схематику: " + e.getMessage());
        }

        throw new NullPointerException("Не удалось загрузить схематику");
    }
}

