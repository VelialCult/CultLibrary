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
