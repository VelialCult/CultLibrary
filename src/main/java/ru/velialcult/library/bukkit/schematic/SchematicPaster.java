package ru.velialcult.library.bukkit.schematic;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import ru.velialcult.library.CultLibrary;

import java.util.Objects;

public class SchematicPaster {

    public org.bukkit.World pasteSchematic(Clipboard clipboard, Location location) {
        com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(Objects.requireNonNull(location.getWorld()));
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);
        BlockVector3 pasteLocation = BlockVector3.at(location.getX(), location.getY(), location.getZ());
        Operation operation = new ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(pasteLocation)
                .ignoreAirBlocks(true)
                .build();
        try {
            Operations.complete(operation);
            editSession.flushSession();
        } catch (WorldEditException e) {
            CultLibrary.getLibrary().getLogger().severe("Невозможно вставить схему в мир по причине: " + e.getMessage());
        }
        return location.getWorld();
    }

    public Location pasteSchematicWithGetBlock(Clipboard clipboard, Location location, Material material) {
        Location chestLocation = null;
        BlockVector3 pasteLocation = BlockVector3.at(location.getX(), location.getY(), location.getZ());
        World world = pasteSchematic(clipboard, location);

        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        Location newPasteLocation = new Location(location.getWorld(), pasteLocation.getBlockX(), pasteLocation.getBlockY() - 1, pasteLocation.getBlockZ());

        for (int x = newPasteLocation.getBlockX(); x <= newPasteLocation.getBlockX() + clipboard.getDimensions().getBlockX(); x++) {
            for (int y = newPasteLocation.getBlockY(); y <= newPasteLocation.getBlockY() + clipboard.getDimensions().getBlockY(); y++) {
                for (int z = newPasteLocation.getBlockZ(); z <= newPasteLocation.getBlockZ() + clipboard.getDimensions().getBlockZ(); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == material) {
                        chestLocation = block.getLocation();
                        System.out.println("1");
                        chestLocation.setY(chestLocation.getY() + 1);
                    }
                }
            }
        }
        return chestLocation;
    }
}
