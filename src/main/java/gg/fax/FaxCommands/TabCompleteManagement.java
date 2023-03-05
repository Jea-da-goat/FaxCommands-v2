package gg.fax.FaxCommands;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TabCompleteManagement implements Listener {


    private static ArrayList<String> Exception = new ArrayList<>();

    public static void setupException() {
        Exception.add("gwarp");
        Exception.add("warpmenu");
        Exception.add("menu");
    }

    @EventHandler
    public void onTab(PlayerCommandSendEvent e) {
        System.out.println("PlayerCommandSendEvent");
        Player p = e.getPlayer();
        if(p.hasPermission("tabcomplete.bypass")) {
            return;
        }
        e.getCommands().clear();
        for(Map.Entry entry : Storage.list.entrySet()) {
            if(p.hasPermission((String) entry.getValue()))  {
                e.getCommands().add((String) entry.getKey());
            }
        }
    }

    @EventHandler
    public void onTabEvent(TabCompleteEvent e) {
        System.out.println("TabCompleteEvent");
        Player p = (Player) e.getSender();
        if(e.getCompletions().contains("국가")) {
            List<String> complete = new ArrayList<>();
            for(Map.Entry entry : Storage.list.entrySet()) {
                if(p.hasPermission((String) entry.getValue()))  {
                    complete.add((String) entry.getKey());
                }
            }
            e.setCompletions(complete);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandExecute(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage().replaceFirst("/", "");
        String shortcut = null;
        if(cmd.contains(" ")) {
            String[] label = cmd.split(" ");
            shortcut = Shortcut.GetShortcutCmd(label[0]);
            if(shortcut != null) {

                if(p.isOp()) {
                    p.performCommand(shortcut);
                } else {
                    p.setOp(true);
                    p.performCommand(shortcut);
                    p.setOp(false);
                }
                e.setCancelled(true);
                return;
            }
            if(Storage.list.containsKey(label[0].toLowerCase(Locale.ROOT))) {
                if (p.hasPermission(Storage.list.get(label[0].toLowerCase(Locale.ROOT)))) {
                    return;
                }
            }
            if(Storage.Whitelist.containsKey(cmd.toLowerCase(Locale.ROOT))) {
                if(p.hasPermission(Storage.Whitelist.get(cmd.toLowerCase(Locale.ROOT)))) {
                    return;
                }
            }
            if(Exception.contains(label[0].toLowerCase(Locale.ROOT))) {
                return;
            }

        } else {
            shortcut = Shortcut.GetShortcutCmd(cmd);
            if(shortcut != null) {
                if(p.isOp()) {
                    p.performCommand(shortcut);
                } else {
                    p.setOp(true);
                    p.performCommand(shortcut);
                    p.setOp(false);
                }
                e.setCancelled(true);
                return;
            }
            if(Storage.list.containsKey(cmd.toLowerCase(Locale.ROOT))) {
                if(p.hasPermission(Storage.list.get(cmd.toLowerCase(Locale.ROOT)))) {
                    return;
                }
            }
            if(Storage.Whitelist.containsKey(cmd.toLowerCase(Locale.ROOT))) {
                if(p.hasPermission(Storage.Whitelist.get(cmd.toLowerCase(Locale.ROOT)))) {
                    return;
                }
            }
            if(Exception.contains(cmd.toLowerCase(Locale.ROOT))) {
                return;
            }

        }
        if(p.hasPermission("tablist.bypass")) {
            return;
        }
        p.sendMessage(Utils.colorize(Utils.PREFIX + "&r 해당 명령어는 존재하지 않습니다"));
        e.setCancelled(true);


    }
}
