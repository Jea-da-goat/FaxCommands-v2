package gg.fax.FaxCommands;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    public static ConcurrentHashMap<String, String> list = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> shortcut = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, String> Whitelist = new ConcurrentHashMap<>();
    // first String => command, second String => permission

    public static void ReadTabList() {
        YamlConfig config = new YamlConfig();
        config.SetTargetName("TabListStorage");
        config.CreateStorage();
        FileConfiguration Storage = config.getStorage();
        restoreTabList(Storage);
    }

    public static void ReadShortcut() {
        YamlConfig config = new YamlConfig();
        config.SetTargetName("ShortcutStorage");
        config.CreateStorage();
        FileConfiguration Storage = config.getStorage();
        restoreShortcut(Storage);
    }

    public static void ReadWhitelist() {
        YamlConfig config = new YamlConfig();
        config.SetTargetName("WhitelistStorage");
        config.CreateStorage();
        FileConfiguration Storage = config.getStorage();
        restoreWhitelist(Storage);
    }

    public static void SaveTabList() throws IOException {
        YamlConfig config = new YamlConfig();
        config.SetTargetName("TabListStorage");
        config.CreateStorage();
        config.resetlocalstorage();
        FileConfiguration Storage = config.getStorage();
        saveTabList(Storage);
        config.saveStorage();
    }

    public static void SaveShortcut() throws IOException {
        YamlConfig config = new YamlConfig();
        config.SetTargetName("ShortcutStorage");
        config.CreateStorage();
        config.resetlocalstorage();
        FileConfiguration Storage = config.getStorage();
        saveShortcut(Storage);
        config.saveStorage();
    }

    public static void SaveWhitelist() throws IOException {
        YamlConfig config = new YamlConfig();
        config.SetTargetName("WhitelistStorage");
        config.CreateStorage();
        config.resetlocalstorage();
        FileConfiguration Storage = config.getStorage();
        saveWhitelist(Storage);
        config.saveStorage();
    }

    public static void restoreTabList(FileConfiguration Storage) {
        if(!Storage.contains("TabList.")) {
            return;
        }
        Storage.getConfigurationSection("TabList.").getKeys(false).forEach(key -> {
            String v = Storage.get("TabList." + key).toString();
            list.put(key, v);
        });
    }

    public static void saveTabList(FileConfiguration Storage) {
        if(list.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : list.entrySet()) {
            Storage.set("TabList." + (String)entry.getKey(), entry.getValue());
        }
    }

    public static void restoreShortcut(FileConfiguration Storage) {
        if(!Storage.contains("Shortcut.")) {
            return;
        }
        Storage.getConfigurationSection("Shortcut.").getKeys(false).forEach(key -> {
            String v = Storage.get("Shortcut." + key).toString();
            shortcut.put(key, v);
        });
    }

    public static void saveShortcut(FileConfiguration Storage) {
        if(shortcut.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : shortcut.entrySet()) {
            Storage.set("Shortcut." + (String)entry.getKey(), entry.getValue());
        }
    }

    public static void restoreWhitelist(FileConfiguration Storage) {
        if(!Storage.contains("Whitelist.")) {
            return;
        }
        Storage.getConfigurationSection("Whitelist.").getKeys(false).forEach(key -> {
            String v = Storage.get("Whitelist." + key).toString();
            Whitelist.put(key, v);
        });
    }

    public static void saveWhitelist(FileConfiguration Storage) {
        if(Whitelist.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : Whitelist.entrySet()) {
            Storage.set("Whitelist." + (String)entry.getKey(), entry.getValue());
        }
    }
}
