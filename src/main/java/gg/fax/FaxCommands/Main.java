package gg.fax.FaxCommands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.InternalStructure;
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
import java.util.ArrayList;
import java.util.List;
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
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, PacketType.Play.Server.TAB_COMPLETE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                //event.setCancelled(true);
                //System.out.println(event.getPlayer().getName() + " TABCOMPLETE BLOCKED");
            }
        });
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, PacketType.Play.Server.COMMANDS) {
            @Override
            public void onPacketSending(PacketEvent event) {
                /*StructureModifier<RootCommandNode> root = event.getPacket().getSpecificModifier(RootCommandNode.class);
                System.out.println(event.getPlayer() + " COMMANDS PACKET");
                if(root != null) {
                    CommandNode node = root.read(0);
                    Player p = event.getPlayer();
                    for (Map.Entry entry : Storage.list.entrySet()) {
                        if (p.hasPermission((String)entry.getValue())) {
                            node.addChild(
                                    LiteralArgumentBuilder.literal((String)entry.getKey()).build()
                            );
                        }

                    }
                }

                Player p = event.getPlayer();*/
                //System.out.println(event.getPacket().getLists());
                StructureModifier<List<InternalStructure>> lists = event.getPacket().getLists(InternalStructure.getConverter());
                //System.out.println(lists);
                System.out.println("=====");
                if(lists != null) {
                    System.out.println("NOTNULL");
                } else {
                    System.out.println("NULL");
                }
                int rootIndex = event.getPacket().getIntegers().read(0);
                if(lists != null) {
                    //System.out.println("hasinternalstructures" + lists.read(0));
                    List<InternalStructure> commands = lists.read(0);
                    System.out.println(commands);
                    int[] subCommands = commands.get(rootIndex).getIntegerArrays().read(0);
                    //List<InternalStructure> sm = new ArrayList<>();
                    /*for (int i = 0; i < subCommands.length; i++) {
                        InternalStructure subCommand = commands.get(i);
                        InternalStructure commandInfo = subCommand.getStructures().read(0);
                        String commandName = commandInfo.getStrings().read(0);
                        if (Storage.list.containsKey(commandName) || !p.hasPermission(Storage.list.get(commandName))) {
                            sm.add(subCommand)
                        }
                    }
                    if(subCommands.length >= 1) {
                        InternalStructure cmdobj = commands.get(0);
                        InternalStructure commandinfo = cmdobj.getStructures().read(0);
                        //commandinfo.getStrings().write(0, "yourString");
                        commands.clear();
                        for(Map.Entry<String, String> sets : Storage.list.entrySet()) {
                            if(p.hasPermission(sets.getValue())) {
                                commandinfo.getStrings().write(0, sets.getKey());
                                cmdobj.getStructures().write(0, commandinfo);
                                commands.add(cmdobj);
                            }
                        }
                    }

                    commands.removeAll(sm);
                    for(int i = 0; i < subCommands.length; i++) {
                        InternalStructure subCommand = commands.get(i);
                        subCommand.getStructures().write()
                        commands.add(subCommand);
                    }
                    lists.write(0, commands);*/
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
