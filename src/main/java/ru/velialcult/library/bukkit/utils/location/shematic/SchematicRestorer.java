package ru.velialcult.library.bukkit.utils.location.shematic;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import java.util.HashMap;
import java.util.Map;

public class SchematicRestorer {
    private final Map<Location, BlockState> blocks = new HashMap<>();

    public void saveBlocks(Clipboard clipboard, Location origin) {
        World world = origin.getWorld();
        BlockVector3 min = clipboard.getMinimumPoint();
        BlockVector3 max = clipboard.getMaximumPoint();
        BlockVector3 offset = clipboard.getOrigin();
        for (int x = min.getBlockX(); x <= max.getBlockX(); ++x) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); ++y) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); ++z) {
                    Location location = origin.clone().add(x - offset.getBlockX(), y - offset.getBlockY(), z - offset.getBlockZ());
                    this.blocks.put(location, world.getBlockAt(location).getState());
                }
            }
        }
    }

    public void restoreBlocks() {
        for (Map.Entry<Location, BlockState> entry : this.blocks.entrySet()) {
            Location location = entry.getKey();
            location.getBlock().setType(entry.getValue().getType());
        }
        this.blocks.clear();
    }
}
