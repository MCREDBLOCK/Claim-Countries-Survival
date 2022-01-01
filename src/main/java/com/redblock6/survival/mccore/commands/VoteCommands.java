package com.redblock6.survival.mccore.commands;

import com.redblock6.survival.mccore.functions.VoteType;
import com.redblock6.survival.mccore.functions.Voting;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static com.redblock6.survival.Main.getBot;
import static com.redblock6.survival.Main.translate;

public class VoteCommands implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("agree")) {
            Player p = (Player) sender;
            if (!Voting.voted.contains(p)) {
                p.sendTitle(translate("&2&lAGREE"), translate("&fYou voted to agree to the teleport!"));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);
                Voting.avotes += 1;
                Voting.voted.add(p);
                getBot().sendVotingStatusAnnouncement(VoteType.Agree, p);
                p.sendMessage(translate("&2&l> &a" + p + " &fagreed to the vote!"));
            } else {
                p.sendMessage(translate("&4&l> &fYou already voted!"));
            }
        } else if (command.getName().equalsIgnoreCase("deny")) {
            Player p = (Player) sender;
            if (!Voting.voted.contains(p)) {
                p.sendTitle(translate("&4&lDENY"), translate("&fYou deny the teleport!"));
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                Voting.dvotes += 1;
                Voting.voted.add(p);
                getBot().sendVotingStatusAnnouncement(VoteType.Deny, p);
                p.sendMessage(translate("&4&l> &c" + p + " &fdisagreed to the vote!"));
            } else {
                p.sendMessage(translate("&4&l> &fYou already voted!"));
            }

        } else if (command.getName().equalsIgnoreCase("rtp")) {
            if (!(args.length < 1)) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (!Bukkit.getPlayer(args[0]).isOnline()) {
                        sender.sendMessage(translate("&4&l> &fThat player is offline"));
                    } else {
                        Voting.startVotingTP((Player) sender, Bukkit.getPlayer(args[0]));
                    }
                } else {
                    sender.sendMessage(translate("&4&l> &fThat player doesn't exist"));
                }
            } else {
                sender.sendMessage(translate("&4&l> &fPlease specify a player you want to teleport to!"));
            }
        }
        return false;
    }
}
