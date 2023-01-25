package gg.fax.FaxCommands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("TabList")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("tablist.admin")) {
                    p.sendMessage(Utils.colorize("&c&lERROR &7권한이 없습니다"));
                    return false;
                }
                if (args.length < 1) {
                    //send help message
                    sendhelp(p);
                    return false;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length < 3) {
                        p.sendMessage(Utils.colorize("&c&lINFO &7명령어 사용법 : &r/Tablist add &r<명령어> <권한>"));
                        return false;
                    }
                    String cmd = args[1];
                    if (Storage.list.containsKey(cmd)) {
                        p.sendMessage(Utils.colorize("&c&lERROR &7이미 자동완성 목록에 추가되어 있는 명령어입니다. 권한을 재설정하고 싶으시다면 제거를 한 뒤에 다시 추가해주시기 바랍니다"));
                    } else {
                        String perm = args[2];
                        Storage.list.put(cmd, perm);
                        p.sendMessage(Utils.colorize("&c&lSUCCESS &7성공적으로 /" + cmd + "&r&7 명령어를 " + perm + "&r&7 의 권한으로 자동완성에 추가했습니다"));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length < 2) {
                        p.sendMessage(Utils.colorize("&c&lINFO &7명령어 사용법 : &r/Tablist remove &r<명령어>"));
                        return false;
                    }
                    String cmd = args[1];
                    if (Storage.list.containsKey(cmd)) {
                        Storage.list.remove(cmd);
                        p.sendMessage(Utils.colorize("&c&lSUCCESS &7성공적으로 /" + cmd + "&r&7 명령어를 자동완성 목록에서 제거했습니다"));
                    } else {
                        p.sendMessage(Utils.colorize("&c&lERROR &7자동완성 목록에 존재하지 않는 명령어입니다"));
                    }
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (args.length < 2) {
                        p.sendMessage(Utils.colorize("&c&lINFO &7명령어 사용법 : &r/Tablist info &r<명령어>"));
                        return false;
                    }
                    String cmd = args[1];
                    if (Storage.list.containsKey(cmd)) {
                        p.sendMessage(Utils.colorize("&c&lINFO &7명령어 -> [/" + cmd + "&r&7] , 권한 -> [" + Storage.list.get(cmd) + "&r&7]"));
                    } else {
                        p.sendMessage(Utils.colorize("&c&lERROR &7자동완성 목록에 존재하지 않는 명령어입니다"));
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    String finalmsg = "&r&7&m---------&r&c&l[목록]&r&7&m---------\n";
                    for (String key : Storage.list.keySet()) {
                        finalmsg = finalmsg + "&7명령어 -> [/" + key + "&r&7] , 권한 -> [" + Storage.list.get(key) + "&r&7]" + "\n";
                    }
                    finalmsg = finalmsg + "&r&7&m---------&r&c&l[목록]&r&7&m---------\n";
                    p.sendMessage(Utils.colorize(finalmsg));
                } else {
                    sendhelp(p);
                }
            }
        } else if (label.equalsIgnoreCase("xshortcuts")) {
            if (args.length < 1) {
                sendhelp2((Player) sender);
                return false;
            }
            if (args[0].equalsIgnoreCase("add") && args.length > 2) {
                String shortcut = args[1];
                String target = Utils.Args2String(args, 2);
                if (!Shortcut.ContainsShortcut(shortcut)) {
                    Shortcut.AddShorcutCmd(shortcut, target);
                    sender.sendMessage(Utils.colorize("&c&lSUCCESS &7해당 Shortcut [" + shortcut + "] 추가 완료"));
                    sender.sendMessage(Utils.colorize("&c&INFO &7Shortcut : [" + shortcut + " -> " + target + "]" + " &2&l+"));
                } else {
                    sender.sendMessage(Utils.colorize("&c&lERROR &7이미 존재하는 Shortcut. 기존 Shortcut 제거 후 추가하시기 바랍니다"));
                }
            } else if (args[0].equalsIgnoreCase("remove") && args.length > 1) {
                String shortcut = args[1];
                if (Shortcut.ContainsShortcut(shortcut)) {
                    String target = "";
                    target = Shortcut.GetShortcutCmd(shortcut);
                    Shortcut.RemoveShortcutCmd(shortcut);
                    sender.sendMessage(Utils.colorize("&c&lSUCCESS &7해당 Shortcut [" + shortcut + "] 제거 완료"));
                    sender.sendMessage(Utils.colorize("&c&INFO &7Shortcut : [" + shortcut + " -> " + target + "]" + " &4&l-"));
                } else {
                    sender.sendMessage(Utils.colorize("&c&lERROR &7존재하지 않는 Shortcut"));
                }
            } else if (args[0].equalsIgnoreCase("info") && args.length > 1) {
                String shortcut = args[1];
                if (Shortcut.ContainsShortcut(shortcut)) {
                    String target = "";
                    target = Shortcut.GetShortcutCmd(shortcut);
                    //Shortcut.RemoveShortcutCmd(shortcut);
                    //sender.sendMessage(Utils.colorize("&c&lSUCCESS &7해당 Shortcut [" + shortcut + "] 제거 완료"));
                    sender.sendMessage(Utils.colorize("&c&INFO &7Shortcut : [" + shortcut + " -> " + target + "]" + " &4&l-"));
                } else {
                    sender.sendMessage(Utils.colorize("&c&lERROR &7존재하지 않는 Shortcut"));
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                String finalmsg = "&r&7&m---------&r&c&l[목록]&r&7&m---------\n";
                for (String key : Storage.shortcut.keySet()) {
                    finalmsg = finalmsg + "&7Shortcut -> [/" + key + "&r&7] , Target -> [" + Storage.shortcut.get(key) + "&r&7]" + "\n";
                }
                finalmsg = finalmsg + "&r&7&m---------&r&c&l[목록]&r&7&m---------\n";
                sender.sendMessage(Utils.colorize(finalmsg));

            } else {
                sendhelp2((Player) sender);
            }
        } else if (label.equalsIgnoreCase("잠금")) {
            Player p = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("도움말")) {
                    sendlockhelp(p);
                } else if (args[0].equalsIgnoreCase("이전") && args.length >= 2) {
                    //p.performCommand("blocklocker 2 " + args[1]);
                    Utils.executecommandasop(p, "blocklocker 2 " + args[1]);
                } else if (args[0].equalsIgnoreCase("2")) {
                    if (args.length >= 2) {
                        p.performCommand("blocklocker 3 " + args[1]);
                    } else {
                        p.performCommand("blocklocker 3 ");
                    }
                } else if (args[0].equalsIgnoreCase("3")) {
                    if (args.length >= 2) {
                        p.performCommand("blocklocker 4 " + args[1]);
                    } else {
                        p.performCommand("blocklocker 4 ");
                    }
                } else if (args[0].equalsIgnoreCase("1")) {
                    Utils.sendmsg(p, "&a&l[&f잠금&a&l] &f해당 줄은 설정할수 없습니다. /잠금 이전 &7(이름) &f으로 잠금의 소유를 이전하십시오");
                }

            } else {
                sendlockhelp(p);
            }
        } else if(label.equalsIgnoreCase("xWhitelist")) {
            Player p = (Player) sender;
            if (!p.hasPermission("xwhitelist.admin")) {
                p.sendMessage(Utils.colorize("&c&lERROR &7권한이 없습니다"));
                return false;
            }
            if (args.length < 1) {
                //send help message
                sendhelp3(p);
                return false;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    p.sendMessage(Utils.colorize("&c&lINFO &7명령어 사용법 : &r/xWhitelist add &r<명령어> <권한>"));
                    return false;
                }
                String cmd = args[1];
                if (Storage.Whitelist.containsKey(cmd)) {
                    p.sendMessage(Utils.colorize("&c&lERROR &7이미 Whitelist 목록에 추가되어 있는 명령어입니다. 권한을 재설정하고 싶으시다면 제거를 한 뒤에 다시 추가해주시기 바랍니다"));
                } else {
                    String perm = args[2];
                    Storage.Whitelist.put(cmd, perm);
                    p.sendMessage(Utils.colorize("&c&lSUCCESS &7성공적으로 /" + cmd + "&r&7 명령어를 " + perm + "&r&7 의 권한으로 Whitelist에 추가했습니다"));
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length < 2) {
                    p.sendMessage(Utils.colorize("&c&lINFO &7명령어 사용법 : &r/xWhitelist remove &r<명령어>"));
                    return false;
                }
                String cmd = args[1];
                if (Storage.Whitelist.containsKey(cmd)) {
                    Storage.Whitelist.remove(cmd);
                    p.sendMessage(Utils.colorize("&c&lSUCCESS &7성공적으로 /" + cmd + "&r&7 명령어를 Whitelist 목록에서 제거했습니다"));
                } else {
                    p.sendMessage(Utils.colorize("&c&lERROR &7Whitelist 목록에 존재하지 않는 명령어입니다"));
                }
            } else if (args[0].equalsIgnoreCase("info")) {
                if (args.length < 2) {
                    p.sendMessage(Utils.colorize("&c&lINFO &7명령어 사용법 : &r/xWhitelist info &r<명령어>"));
                    return false;
                }
                String cmd = args[1];
                if (Storage.Whitelist.containsKey(cmd)) {
                    p.sendMessage(Utils.colorize("&c&lINFO &7명령어 -> [/" + cmd + "&r&7] , 권한 -> [" + Storage.Whitelist.get(cmd) + "&r&7]"));
                } else {
                    p.sendMessage(Utils.colorize("&c&lERROR &7Whitelist 목록에 존재하지 않는 명령어입니다"));
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                String finalmsg = "&r&7&m---------&r&c&l[Whitelist 목록]&r&7&m---------\n";
                for (String key : Storage.Whitelist.keySet()) {
                    finalmsg = finalmsg + "&7명령어 -> [/" + key + "&r&7] , 권한 -> [" + Storage.Whitelist.get(key) + "&r&7]" + "\n";
                }
                finalmsg = finalmsg + "&r&7&m---------&r&c&l[목록]&r&7&m---------\n";
                p.sendMessage(Utils.colorize(finalmsg));
            } else {
                sendhelp3(p);
            }

        }
        return false;
    }
    private static void sendlockhelp(Player p) {
        Utils.sendmsg(p, "&7&m--------&a&l[&f잠금 도움말&a&l]&7&m--------");
        Utils.sendmsg(p, "&f/잠금 도움말 &8: &f잠금 명령어를 알려준다");
        Utils.sendmsg(p, "&f/잠금 이전 &7(이름) &8: &f잠금 명령어를 알려준다");
        Utils.sendmsg(p, "&f/잠금 [줄번호] &7(이름) &8: &f잠금 명령어를 알려준다");
        Utils.sendmsg(p, "&c&l(&4&l!&c&l) &f잠금 줄번호 설정은 &c2&f에서 &c3&f까지만 설정이 가능하고 첫번째 줄을 바꾸려면 /잠금 이전 &7(이름) &f을 해야 합니다");
        Utils.sendmsg(p, "&7&m--------&a&l[&f잠금 도움말&a&l]&7&m--------");

    }

    public static void sendhelp(Player p) {
        p.sendMessage(Utils.colorize("&r&7&m---------&r&c&l[도움말]&r&7&m---------\n" +
                "&r/Tablist add &7<명령어> <권한> &8&l:: &7해당 권한을 가진 유저들의 자동완성 목록에 해당 명령어를 추가\n" +
                "&r/Tablist remove &7<명령어> &8&l:: &7해당 명령어를 자동완성 목록에서 제거\n" +
                "&r/Tablist info &7<명령어> &8&l:: &7해당 명령어에 달려 있는 권한을 확인\n" +
                "&r/Tablist list &8&l:: &7자동완성 목록에 추가되어 있는 명령어들을 확인\n"));
    }

    public static void sendhelp2(Player p) {
        p.sendMessage(Utils.colorize("&r&7&m---------&r&c&l[도움말]&r&7&m---------\n" +
                "&r/xShortcuts add &7<Shortcut명령어> <명령어> &8&l:: &7해당 명령어에 대한 Shortcut명령어를 추가\n" +
                "&r/xShortcuts remove &7<Shortcut명령어> &8&l:: &7해당 Shortcut을 목록에서 제거\n" +
                "&r/xShortcuts info &7<Shortcut명령어> &8&l:: &7해당 Shortcut에 대한 정보를 확인\n" +
                "&r/xShortcuts list &8&l:: &7Shortcut 목록에 추가되어 있는 명령어들을 확인\n"));
    }

    public static void sendhelp3(Player p) {
        p.sendMessage(Utils.colorize("&r&7&m---------&r&c&l[도움말]&r&7&m---------\n" +
                "&r/xWhitelist add &7<Whitelist명령어> <명령어> &8&l:: &7해당 명령어에 대한 Whitelist 명령어를 추가\n" +
                "&r/xWhitelist remove &7<Whitelist명령어> &8&l:: &7해당 Shortcut을 목록에서 제거\n" +
                "&r/xWhitelist info &7<Whitelist명령어> &8&l:: &7해당 Whitelist에 대한 정보를 확인\n" +
                "&r/xWhitelist list &8&l:: &7Whitelist 목록에 추가되어 있는 명령어들을 확인\n"));
    }
}
