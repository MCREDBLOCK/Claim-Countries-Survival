package com.redblock6.survival.mccore.functions;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.CountriesReason;
import com.redblock6.survival.mccore.achievements.AchDatabase;
import com.redblock6.survival.mccore.achievements.AchLibrary;
import com.redblock6.survival.mccore.achievements.SAchType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

import static com.redblock6.survival.Main.getBot;
import static com.redblock6.survival.mccore.functions.CreateGameMenu.translate;

public class Voting implements Listener {
    public static ArrayList<Player> hasRequest = new ArrayList<>();
    public static HashMap<Player, Player> queuedTPRequests = new HashMap<>();
    public static ArrayList<Player> voted = new ArrayList<>();
    public static int avotes = 0;
    public static int dvotes = 0;

    public static void startVotingTP(Player starter, Player toTP) {
        queuedTPRequests.put(starter, toTP);
        starter.playSound(starter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
        starter.sendTitle(translate("&4&lARE YOU SURE?"), translate("&fThis will cost &c50 XP&f!"), 10, 1000000000, 10);
    }

    public static void confirmTP(Player starter) {
        hasRequest.add(starter);
        Player toTP = queuedTPRequests.get(starter);
        queuedTPRequests.remove(starter);
        voted.clear();
        avotes = 0;
        dvotes = 0;
        getBot().sendVotingAnnouncement(VoteType.TP, starter, toTP);
        for (Player lp : Bukkit.getOnlinePlayers()) {
            lp.playSound(lp.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            new BukkitRunnable() {
                int i = 10;
                @Override
                public void run() {
                    if (i != 1) {
                        lp.sendTitle(translate("&4&l" + i), translate("&a" + avotes + " &7- " + dvotes));
                        lp.playSound(lp.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
                        i--;
                    } else {
                        if (avotes > dvotes) {
                            lp.sendTitle(translate("&2&lAGREE"), translate("&fMore people agreed to the teleport!"));
                            if (avotes > dvotes) {
                                starter.teleport(toTP.getLocation());
                                starter.playSound(toTP.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                            }
                            cancel();
                        } else if (avotes < dvotes) {
                            lp.sendTitle(translate("&4&lDISAGREE"), translate("&fMore people disagreed to the teleport!"));
                            cancel();
                        } else if (avotes == dvotes) {
                            lp.sendTitle(translate("&7&lTIE"), translate("&fIt was a tie! &a" + avotes + " &7- " + dvotes));
                            starter.teleport(toTP.getLocation());
                            cancel();
                        }
                    }

                }
            }.runTaskTimer(Main.getInstance(), 20, 20);
            if (lp != starter) {
                lp.sendTitle(translate("&4&lVOTE TO ALLOW"), translate("&c" + starter.getName() + " &fwants to TP to &c" + toTP.getName()));
                lp.sendMessage(translate("&4&l> &c" + starter.getName() + " &fwants to TP to &c" + toTP.getName() + "."));
                CreateGameMenu.sendClickableCommand(lp, translate("&2&l> &fClick to &2&lAGREE"), "agree");

            } else {
                lp.sendTitle(translate("&4&lTELEPORT REQUESTED"), translate("&fYour teleport vote has been posted in the chat!"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (new AchDatabase(lp).getSurivalAch().contains(SAchType.Teleport_Request)) {
                            AchLibrary.grantSurvivalAchievement(lp, SAchType.Teleport_Request);
                        }
                    }
                }.runTaskLater(Main.getInstance(), 20);
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                avotes = 0;
                dvotes = 0;
                hasRequest.clear();
                voted.clear();
            }
        }.runTaskLater(Main.getInstance(), 100);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String s = e.getMessage();
        if (queuedTPRequests.containsKey(p)) {
            if (s.equalsIgnoreCase("yes")) {
                confirmTP(p);
            }
        }
    }
}
