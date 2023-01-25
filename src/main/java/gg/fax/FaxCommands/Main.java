package gg.fax.FaxCommands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static  Main instance;

    public static Main getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new TabCompleteManagement(), Main.getInstance());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(Main.getInstance().getCommand("TabList"))).setExecutor(new Command());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(Main.getInstance().getCommand("잠금"))).setExecutor(new Command());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(Main.getInstance().getCommand("xShortcuts"))).setExecutor(new Command());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(Main.getInstance().getCommand("xWhitelist"))).setExecutor(new Command());
        StorageDir.SetupStorage();
        Storage.ReadTabList();
        Storage.ReadShortcut();
        Storage.ReadWhitelist();
        TabCompleteManagement.setupException();
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.COMMANDS) {
            @Override
            public void onPacketSending(PacketEvent event) {
                StructureModifier<RootCommandNode> root = event.getPacket().getSpecificModifier(RootCommandNode.class);
                if(root != null) {
                    CommandNode node = root.read(0);
                    Player p = event.getPlayer();
                    for (Map.Entry entry : Storage.list.entrySet()) {
                        if (p.hasPermission((String) entry.getValue())) {
                            node.addChild(
                                    LiteralArgumentBuilder.literal((String) entry.getKey()).build()
                            );
                        }
                    }
                    root.write(0, (RootCommandNode) node);
                }
            }
        });
        //new PacketListener().Register();
    }

    @Override
    public void onDisable() {
        try {
            Storage.SaveTabList();
            Storage.SaveShortcut();
            Storage.SaveWhitelist();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = null;
    }
}
