package ru.velialcult.library.bukkit.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cuboid implements Iterable<Block>, Cloneable
{
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final String world;

    public Cuboid(Location one, Location two)
    {
        this.world = one.getWorld().getName();
        this.xMin  = (int) Math.min(one.getX(), two.getX());
        this.yMin = (int) Math.floor(Math.min(one.getY(), two.getY()));
        this.zMin = (int) Math.min(one.getZ(), two.getZ());
        this.xMax = (int) (Math.ceil(Math.max(one.getX(), two.getX())) + 1);
        this.yMax  = (int) (Math.ceil(Math.max(one.getY(), two.getY())) + 1);
        this.zMax = (int) Math.ceil(Math.max(one.getZ(), two.getZ()) + 1);
    }

    public boolean contains(Location loc)
    {
        return contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public boolean contains(long x, long y, long z)
    {
        return x >= this.xMin && x <= this.xMax && y >= this.yMin && y <= this.yMax && z >= this.zMin && z <= this.zMax;
    }

    public long getWidth()
    {
        return this.xMax - this.xMin + 1;
    }

    public long getHeight()
    {
        return this.yMax - this.yMin + 1;
    }

    public long getDepth()
    {
        return this.zMax - this.zMin + 1;
    }

    public World getWorld()
    {
        return Bukkit.getWorld(this.world);
    }

    public boolean isInside(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax;
    }

    public List<Chunk> getChunks()
    {
        List<Chunk> chunks = new ArrayList<>();

        for (Block block : getBlocks())
        {
            chunks.add(block.getChunk());
        }

        return chunks;
    }

    public List<Block> getBlocks() {

        List<Block> blocks = new ArrayList<>();

        for (int i = xMin; i < xMax; i++) {
            for (int j = yMin; j < yMax; j++) {
                for (int k = zMin; k < zMax; k++) {
                    blocks.add(getWorld().getBlockAt(i, j, k));
                }
            }
        }

        return blocks;
    }

    public Location getCenter()
    {
        return new Location(this.getWorld(), (double) ((this.xMax - this.xMin) / 2 + this.xMin),
                (double) ((this.yMax - this.yMin) / 2 + this.yMin), (double) ((this.zMax - this.zMin) / 2 + this.zMin));
    }

    @Override
    public Iterator<Block> iterator()
    {
        return this.getBlocks().listIterator();
    }

    @Override
    public String toString()
    {
        return "Cuboid: " + this.xMin + "," + this.yMin + "," + this.zMin + "=>" + this.xMax + "," + this.yMax + ","
                + this.zMax;
    }

    public boolean hasPlayerInside(Player player)
    {
        Location loc = player.getLocation();
        return xMin <= loc.getX() && xMax >= loc.getX() && yMin <= loc.getY() && yMax >= loc.getY() && zMin <= loc.getZ() && zMax >= loc.getX() && world.equals(loc.getWorld().getName());
    }

    public boolean hasBlockInside(Block block)
    {
        Location loc = block.getLocation();
        return xMin <= loc.getX() && xMax >= loc.getX() && yMin <= loc.getY() && yMax >= loc.getY() && zMin <= loc.getZ() && zMax >= loc.getX() && world.equals(loc.getWorld().getName());
    }

    public Block[] corners() {
        World world = this.getWorld();
        return new Block[] {
                world.getBlockAt(xMin, yMin, zMin),
                world.getBlockAt(xMax, yMin, zMin),
                world.getBlockAt(xMin, yMax, zMin),
                world.getBlockAt(xMax, yMax, zMin),
                world.getBlockAt(xMin, yMin, zMax),
                world.getBlockAt(xMax, yMin, zMax),
                world.getBlockAt(xMin, yMax, zMax),
                world.getBlockAt(xMax, yMax, zMax)
        };
    }
}
