package com.redblock6.survival;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public static boolean streaming;

    @Override
    public void onEnable() {
        instance = this;

        streaming = false;

        Register.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
