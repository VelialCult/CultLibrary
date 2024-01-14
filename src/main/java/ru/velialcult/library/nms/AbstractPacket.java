package ru.velialcult.library.nms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import org.bukkit.entity.Player;

public class AbstractPacket {

    protected PacketContainer handle;


    protected AbstractPacket(PacketContainer handle, PacketType type) {
        // Make sure we're given a valid packet
        if (handle == null)
            throw new IllegalArgumentException("Packet handle cannot be NULL.");
        if (!Objects.equal(handle.getType(), type))
            throw new IllegalArgumentException(handle.getHandle()
                    + " is not a packet of type " + type);

        this.handle = handle;
    }

    public PacketContainer getHandle() {
        return handle;
    }

    public void sendPacket(Player receiver) {
        ProtocolLibrary.getProtocolManager().sendServerPacket(receiver,
                getHandle());
    }

    public void broadcastPacket() {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
    }

    public void receivePacket(Player sender) {
        try {
            ProtocolLibrary.getProtocolManager().receiveClientPacket(sender, getHandle());
        } catch (Exception e) {
            throw new RuntimeException("Cannot receive packet.", e);
        }
    }
}
