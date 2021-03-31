package com.redblock6.survival;

import com.redblock6.survival.mccore.commands.StreamingCommand;
import com.redblock6.survival.mccore.events.JoinLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Register {
    private static final Main pl = Main.getInstance();

    public static void register() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new JoinLeaveEvent(), pl);

        pl.getCommand("streaming").setExecutor(new StreamingCommand());
    }
}
