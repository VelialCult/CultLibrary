package ru.velialcult.library.nms.universal;

import com.comphenix.protocol.wrappers.WrappedParticle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticlesPackets {

    public static void create(Player player, Particle particle, int countParticles, Location location) {
        WrappedParticle<?> wrappedParticle = WrappedParticle.create(particle, null);
        WrappedParticles wrappedParticles = new WrappedParticles();
        wrappedParticles.setParticleType(wrappedParticle);
        wrappedParticles.setNumberOfParticles(countParticles);
        wrappedParticles.setX(location.getX());
        wrappedParticles.setY(location.getY());
        wrappedParticles.setZ(location.getZ());
        wrappedParticles.sendPacket(player);
    }
}
