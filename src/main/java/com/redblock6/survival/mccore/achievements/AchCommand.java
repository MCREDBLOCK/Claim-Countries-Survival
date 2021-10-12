package com.redblock6.survival.mccore.achievements;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class AchCommand implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("ach") || command.getName().equals("acheivements") || command.getName().equals("achievements")) {
            if (sender instanceof Player) {
                //AchLibrary.achSelector(((Player) sender).getPlayer());
                AchLibrary.hub(((Player) sender).getPlayer());
                //AchLibrary.grantKitAchievement(((Player) sender).getPlayer(), KAchType.First_Kill);
            } else {
                return true;
            }
        }
        return false;
    }
}
