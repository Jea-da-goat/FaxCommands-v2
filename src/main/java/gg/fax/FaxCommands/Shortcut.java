package gg.fax.FaxCommands;

public class Shortcut {

    public static void AddShorcutCmd(String label, String target) {
        Storage.shortcut.put(label, target);
    }

    public static void RemoveShortcutCmd(String label) {
        Storage.shortcut.remove(label);
    }

    public static String GetShortcutCmd(String label) {
        if(Storage.shortcut.containsKey(label)) {
            return Storage.shortcut.get(label);
        }
        return null;
    }

    public static Boolean ContainsShortcut(String label) {
        return Storage.shortcut.containsKey(label);
    }

}
