package com.redblock6.survival;

import com.redblock6.survival.countries.NationCommand;
import com.redblock6.survival.mccore.commands.Gamemode;
import com.redblock6.survival.mccore.commands.StreamingCommand;
import com.redblock6.survival.mccore.commands.VoteCommands;
import com.redblock6.survival.mccore.commands.WarnReboot;
import com.redblock6.survival.mccore.events.ChatEvent;
import com.redblock6.survival.mccore.events.InvClickEvent;
import com.redblock6.survival.mccore.events.JoinLeaveEvent;
import com.redblock6.survival.countries.FriendlyFireListener;
import com.redblock6.survival.countries.PlayerChatListener;
import com.redblock6.survival.mccore.functions.Voting;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Register {
    private static final Main pl = Main.getInstance();

    public static void register() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new JoinLeaveEvent(), pl);
        pm.registerEvents(new Voting(), pl);
        pm.registerEvents(new PlayerChatListener(), pl);
        pm.registerEvents(new FriendlyFireListener(), pl);
        pm.registerEvents(new InvClickEvent(), pl);
        pm.registerEvents(new ChatEvent(), pl);

        pl.getCommand("streaming").setExecutor(new StreamingCommand());
        pl.getCommand("gmc").setExecutor(new Gamemode());
        pl.getCommand("gms").setExecutor(new Gamemode());
        pl.getCommand("gma").setExecutor(new Gamemode());
        pl.getCommand("gmsp").setExecutor(new Gamemode());
        pl.getCommand("warnreboot").setExecutor(new WarnReboot());
        pl.getCommand("rtp").setExecutor(new VoteCommands());
        pl.getCommand("agree").setExecutor(new VoteCommands());
        pl.getCommand("deny").setExecutor(new VoteCommands());
    }
}
