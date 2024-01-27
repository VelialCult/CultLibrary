package ru.velialcult.library.bukkit.utils.location.shematic;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.velialcult.library.CultLibrary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class SchematicService {

    public static void pasteSchematic(Clipboard clipboard, Location location) {
        org.bukkit.World bukkitWorld = SchematicService.performPaste(clipboard, location);
        if (bukkitWorld == null) {
            throw new IllegalStateException("World is null");
        }
    }

    public static Location pasteSchematic(Clipboard clipboard, Location location, Material material) {
        org.bukkit.World bukkitWorld = SchematicService.performPaste(clipboard, location);
        if (bukkitWorld == null) {
            throw new IllegalStateException("World is null");
        }
        BlockVector3 pasteLocation = BlockVector3.at(location.getX(), location.getY(), location.getZ());
        for (int x = pasteLocation.getBlockX(); x <= pasteLocation.getBlockX() + clipboard.getDimensions().getBlockX(); ++x) {
            for (int y = pasteLocation.getBlockY(); y <= pasteLocation.getBlockY() + clipboard.getDimensions().getBlockY(); ++y) {
                for (int z = pasteLocation.getBlockZ(); z <= pasteLocation.getBlockZ() + clipboard.getDimensions().getBlockZ(); ++z) {
                    Block block = bukkitWorld.getBlockAt(x, y, z);
                    if (block.getType() != material) continue;
                    Location chestLocation = block.getLocation();
                    chestLocation.setY(chestLocation.getY() + 1.0);
                    return chestLocation;
                }
            }
        }
        throw new IllegalStateException("Не удалось найти блок, который возвращает свою координату для дальнейших действий.");
    }

    private static org.bukkit.World performPaste(Clipboard clipboard, Location location) {
        World adaptedWorld = BukkitAdapter.adapt(Objects.requireNonNull(location.getWorld()));
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);
        BlockVector3 pasteLocation = BlockVector3.at((double)location.getX(), (double)location.getY(), (double)location.getZ());
        Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(pasteLocation).ignoreAirBlocks(true).build();
        try {
            Operations.complete((Operation)operation);
            editSession.flushSession();
        } catch (WorldEditException e) {
            CultLibrary.getLibrary().getLogger().severe("Невозможно вставить схему в мир по причине: " + e.getMessage());
        }
        return location.getWorld();
    }

    public static Clipboard loadSchematic(String filePath) {
        File file = new File(filePath);
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try {
            assert format != null;
            try (ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()))) {
                return reader.read();
            }
        } catch (IOException e) {
            System.out.println("Couldn't load the schematic " + e.getMessage());
        }

        throw new NullPointerException("Couldn't load the schematic");
    }
}
