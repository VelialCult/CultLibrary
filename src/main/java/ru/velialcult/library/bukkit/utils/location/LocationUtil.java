package ru.velialcult.library.bukkit.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LocationUtil {

    public static Location[] getCorners(Location one, Location two) {

        if (one.getWorld() != two.getWorld()) return null;

        World world = one.getWorld();

        Location[] res = new Location[8];

        double minX = Math.min(one.getX(), two.getX());
        double minY = Math.min(one.getY(), two.getY());
        double minZ = Math.min(one.getZ(), two.getZ());
        double maxX = Math.max(one.getX(), two.getX());
        double maxY = Math.max(one.getY(), two.getY());
        double maxZ = Math.max(one.getZ(), two.getZ());

        res[0] = new Location(world, minX, minY, minZ);
        res[1] = new Location(world, minX, maxY, minZ);
        res[2] = new Location(world, maxX, minY, minZ);
        res[3] = new Location(world, maxX, maxY, minZ);
        res[4] = new Location(world, minX, minY, maxZ);
        res[5] = new Location(world, minX, maxY, maxZ);
        res[6] = new Location(world, maxX, minY, maxZ);
        res[7] = new Location(world, maxX, maxY, maxZ);

        return res;
    }

    public static Location updateHologramHeight(Location location, List<String> lines, double heightIncreasePerLine) {
        int lineCount = lines.size();
        double newHeight = location.getY() + lineCount * heightIncreasePerLine;
        Location newLocation = location.clone();
        newLocation.setY(newHeight);
        newLocation.setX(location.getX() + 0.5);
        newLocation.setZ(location.getZ() + 0.5);
        return newLocation;
    }

    public static Location stringToLocation(String s) {
        String[] split = s.split(";");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String locationToString(Location location) {
        World world = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return world.getName() + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
    }

    public static Location getTarget(LivingEntity entity) {

        Location location = entity.getLocation();

        try {

            Block block = entity.getTargetBlockExact(300);

            if (block != null) {

                location = block.getLocation();

            }

        } catch (Exception ignored) {}

        return location;
    }

    public static boolean isDistance(Location location1, Location location2, double distance) {
        return location1.distance(location2) <= distance;
    }

    public static Player getFirstNearPlayer(Player player, double radius) {

        Collection<Entity> near = Objects.requireNonNull(player.getLocation().getWorld()).getNearbyEntities(player.getLocation(), radius, radius, radius);

        double finalRadius = radius * radius;

        Optional<Player> optional = near.stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .filter(target -> target != player)
                .filter(target -> player.getLocation().distanceSquared(target.getLocation()) < finalRadius)
                .findFirst();

        return optional.orElse(null);
    }
}
