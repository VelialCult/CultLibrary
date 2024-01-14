package ru.velialcult.library.bukkit.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cuboid implements Iterable<Block>, Cloneable
{
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int zMin;
    private int zMax;
    private final String world;

    public Cuboid(Location loc1, Location loc2)
    {
        this.world = loc1.getWorld().getName();
        normalize(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(),
                loc2.getBlockZ());
    }

    public Cuboid(Cuboid other)
    {
        this(other.getWorld().getName(), other.xMin, other.yMin, other.zMin, other.xMax, other.yMax, other.zMax);
    }

    public Cuboid(String world, int x1, int y1, int z1, int x2, int y2, int z2)
    {
        this.world = world;
        normalize(x1, y1, z1, x2, y2, z2);
    }

    private void normalize(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        this.xMin = Math.min(x1, x2);
        this.xMax = Math.max(x1, x2);
        this.yMin = Math.min(y1, y2);
        this.yMax = Math.max(y1, y2);
        this.zMin = Math.min(z1, z2);
        this.zMax = Math.max(z1, z2);
    }

    public boolean intersects(Cuboid cuboid)
    {
        return cuboid.xMin <= this.xMax && cuboid.xMax >= this.xMin && cuboid.yMin <= this.yMax
                && cuboid.yMax >= this.yMin && cuboid.zMin <= this.zMax && cuboid.zMax >= this.zMin;
    }

    public boolean contains(Location loc)
    {
        return contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public boolean contains(long x, long y, long z)
    {
        return x >= this.xMin && x <= this.xMax && y >= this.yMin && y <= this.yMax && z >= this.zMin && z <= this.zMax;
    }

    public long getVolume()
    {
        return getWidth() * getHeight() * getDepth();
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

    public Cuboid expand(CuboidDirection dir, int amount)
    {
        switch (dir) {
            case North:
                this.xMin = this.xMin - amount;
                return this;
            case South:
                this.xMax = this.xMax + amount;
                return this;
            case East:
                this.zMin = this.zMin - amount;
                return this;
            case West:
                this.zMax = this.zMax + amount;
                return this;
            case Down:
                this.yMin = this.yMin - amount;
                return this;
            case Up:
                this.yMax = this.yMax + amount;
                return this;
            default:
                throw new IllegalArgumentException("invalid direction " + dir);
        }
    }

    public boolean isInside(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax;
    }

    public Cuboid shift(CuboidDirection dir, int amount)
    {
        return expand(dir, amount).expand(dir.opposite(), -amount);
    }

    public Cuboid outset(int amount)
    {
        return expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount)
                .expand(CuboidDirection.East, amount).expand(CuboidDirection.West, amount)
                .expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
    }

    public Cuboid inset(int amount)
    {
        return outset(-amount);
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

    public void setBiome(Biome biome)
    {
        for (Block block : getBlocks())
        {
            block.setBiome(biome);
        }

        for (Chunk chunk : getChunks())
        {
            chunk.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
        }
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
        Block[] res = new Block[8];
        World w = this.getWorld();

        // Дальний
        res[0] = w.getBlockAt(this.xMax, this.yMax, this.zMax); // левый северный верхний угол
        res[1] = w.getBlockAt(this.xMax, this.yMax, this.zMin); // левый южный верхний угол
        res[2] = w.getBlockAt(this.xMin, this.yMax, this.zMax); // правый северный верхний угол
        res[3] = w.getBlockAt(this.xMin, this.yMax, this.zMin); // Правый южный верхний угол



        // ---------------------------------------------

        res[4] = w.getBlockAt(this.xMax, this.yMin, this.zMax); // левый северный нижний угол
        res[5] = w.getBlockAt(this.xMax, this.yMin, this.zMin); // левый южный нижний угол
        res[6] = w.getBlockAt(this.xMin, this.yMin, this.zMax); //правый северный нижний угол
        res[7] = w.getBlockAt(this.xMin, this.yMin, this.zMin); // Правый южный нижнмй угол
        return res;
    }

    @Override
    public Cuboid clone()
    {
        return new Cuboid(this);
    }


    public enum CuboidDirection
    {
        North, South, East, West, Down, Up, Unknown;

        public CuboidDirection opposite()
        {
            switch (this)
            {
                case North:
                    return South;
                case East:
                    return West;
                case South:
                    return North;
                case West:
                    return East;
                case Up:
                    return Down;
                case Down:
                    return Up;
                default:
                    return Unknown;
            }
        }
    }
}
