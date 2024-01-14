package ru.velialcult.library.bukkit.utils;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Nicholas Alexandrov 18.06.2023
 */
public class Coordinate {

    private final World world;

    private final double x, y, z;

    private final Location location;

    public Coordinate(World world, double x, double y, double z) {

        this.world = world;

        this.x = x;

        this.y = y;

        this.z = z;

        this.location = new Location(world, x, y, z);
    }

    public World getWorld() {
        return world;
    }

    public double getZ() {
        return z;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public Location getLocation() {

        return location;
    }

    @Override
    public String toString() {

        return world.getName() + "x: " + x + " y:" + y + " z: " + z;
    }
}
