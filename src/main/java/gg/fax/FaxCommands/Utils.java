package gg.fax.FaxCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static String PREFIX = "&r&3&l[ &r&f시스템 &3&l]&r";

    public static String Args2String(String[] args, int Start) {
        String FinalString = "";
        for(int k = Start; k < args.length; k++) {
            if(args[k] == null) {
                break;
            }
            if(k == args.length - 1) {
                FinalString = FinalString + args[k];
            } else {
                FinalString = FinalString + args[k] + " ";
            }

        }
        return FinalString;
    }

    public static void executecommandasop(Player p, String command) {
        if(p.isOp()) {
            p.performCommand(command);
        } else {
            p.setOp(true);
            p.performCommand(command);
            p.setOp(false);
        }
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendmsg(Player p, String s) {
        p.sendMessage(colorize(s));
    }
}
