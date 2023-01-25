package gg.fax.FaxCommands.Wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.mojang.brigadier.tree.RootCommandNode;

public class WrapperPlayServerCommands extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.COMMANDS;

    public WrapperPlayServerCommands() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerCommands(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Mojang's brigadier library isn't versioned inside craftbukkit,
     * so it should be safe to use here.
     */
    public RootCommandNode getRoot() {
        return (RootCommandNode) handle.getModifier().read(0);
    }

    public void setRoot(RootCommandNode node) {
        handle.getModifier().write(0, node);
    }
}
