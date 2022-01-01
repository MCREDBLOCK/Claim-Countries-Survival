package com.redblock6.survival.mccore.events;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.Countries;
import com.redblock6.survival.countries.CountriesDatabase;
import com.redblock6.survival.countries.CountriesSetup;
import com.redblock6.survival.countries.CountrySelectorHandoff;
import com.redblock6.survival.mccore.achievements.AchDatabase;
import com.redblock6.survival.mccore.achievements.AchLibrary;
import com.redblock6.survival.mccore.achievements.HAchType;
import com.redblock6.survival.mccore.achievements.SAchType;
import com.redblock6.survival.mccore.bot.BotMain;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

import static com.redblock6.survival.Main.*;
import static com.redblock6.survival.mccore.utils.TimeChecker.day;

public class JoinLeaveEvent implements Listener {
    public static ArrayList<Player> notConnected = new ArrayList<>();
    private static final Main pl = Main.getInstance();

    /*
    @EventHandler
    public void itemDurability(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        if (p.getName().equals("RiverGod16")) {
            if (e.getItem().getType().equals(Material.TRIDENT)) {
                e.setCancelled(true);
            }
        }
    }

     */

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!Bukkit.getWhitelistedPlayers().contains(p)) {
            p.kickPlayer("You are not whitelisted on this server!");
        }

        /*
        if (p.getName().equals("hotcloud")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (day()) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000000000, 0));
                    } else {
                        p.removePotionEffect(PotionEffectType.WEAKNESS);
                    }
                }
            }.runTaskTimer(Main.getInstance(), 10, 120);

            if (day()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000000000, 0));
            } else {
                p.removePotionEffect(PotionEffectType.WEAKNESS);
            }

         */

        if (streaming) {
            if (!Bukkit.getWhitelistedPlayers().contains(p)) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendTitle(translate("&2&lSPECTATING"), translate("&fYou are a spectator!"), 10, 20, 10);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                e.setJoinMessage(translate("&2&l> &a" + p.getDisplayName() + " &fis now spectating."));
            } else {
                if (!new AchDatabase(p).getHubAch().contains(HAchType.Link_Your_Account_With_Core)) {
                    p.sendTitle(translate("&4&lREDSMP SURVIVAL"), translate("&fmc.redblock6.com"), 5, 20, 5);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                    p.sendMessage(translate("&4&l> &fYou haven't linked your account to Discord! Run &c/connect &fin &c#general &fto start the linking process for extra features!"));
                } else {
                    p.sendTitle(translate("&4&lREDSMP SURVIVAL"), translate("&fmc.redblock6.com"), 5, 20, 5);
                }

                if (!new AchDatabase(p).getSurivalAch().contains(SAchType.Our_SMP_Adventure_Begins)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (p.getLevel() >= 5) {
                                AchLibrary.grantSurvivalAchievement(p, SAchType.Reach_Level_5);
                            }

                            if (p.getLevel() >= 10) {
                                AchLibrary.grantSurvivalAchievement(p, SAchType.Reach_Level_10);
                            }

                            if (p.getLevel() >= 20) {
                                AchLibrary.grantSurvivalAchievement(p, SAchType.Reach_Level_20);
                            }

                            if (p.getLevel() >= 50) {
                                AchLibrary.grantSurvivalAchievement(p, SAchType.Reach_Level_5);
                            }

                            if (p.getLevel() > 100) {
                                AchLibrary.grantSurvivalAchievement(p, SAchType.Surpass_Level_100);
                            }
                            AchLibrary.grantSurvivalAchievement(p, SAchType.Our_SMP_Adventure_Begins);
                        }
                    }.runTaskLater(pl, 20);
                }
            }
        } else {
            if (!Bukkit.getWhitelistedPlayers().contains(p)) {
                p.kickPlayer("You are not whitelisted on this server!");
            }
        }
    }

    /*
    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
            if (!notConnected.contains(p)) {
                if (message.equalsIgnoreCase("YES")) {
                    Jedis j = pool.getResource();
                    p.sendTitle(Main.translate("&fLinking your account..."), "", 10, 1000000000, 10);
                    //j.set(bot.connectQueued.get(p.getName()) + "Discord", bot.connectId.get(p.getName()));
                    j.set(bot.connectId.get(p.getName()) + "Name", p.getName());
                    j.set(p.getUniqueId() + "DiscordID", bot.connectId.get(p.getName()));
                    j.set(p.getUniqueId() + "DiscordTag", bot.connectQueued.get(p.getName()));
                    j.set(bot.connectId.get(p.getName()) + "Unique", String.valueOf(p.getUniqueId()));
                    j.set(p.getUniqueId() + "Discord", "LINKED");
                    j.set(p.getUniqueId() + "PDiscord", "YES");
                    j.close();
                    p.sendTitle(Main.translate("&2&lACCOUNT LINKED"), translate("&fCheck your DM's for a message from the &aRedSMP &fbot"), 10, 1000000000, 10);
                    BotMain.sendMessage(bot.connectUser.get(p.getName()), ":white_check_mark: Your Minecraft Account: " + p.getName() + " is now linked to your Discord Account: " + bot.connectQueued.get(p.getName()));
                    int delay = 40;
                    AchDatabase database = new AchDatabase(p);
                    if (!database.getHubAch().contains(HAchType.Link_Your_Account_With_Core)) {
                        delay = 100;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                AchLibrary.grantHubAchievement(p, HAchType.Link_Your_Account_With_Core);
                            }
                        }.runTaskLater(pl, 40);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.sendTitle(Main.translate("&4&lCLAIM A COUNTRY"), translate("&fClaim a country to settle in!"), 10, 1000000000, 10);
                            CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 1);
                        }
                    }.runTaskLater(pl, delay);

                } else if (message.equalsIgnoreCase("NO")) {
                    p.sendTitle(translate("&4&lTYPE YOUR IGN IN #general"), translate("&fThis will link your discord account to your mc account!"), 10, 1000000000, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                }
            }
        }
        */



    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        /*
        if (streaming) {
            if (!Bukkit.getWhitelistedPlayers().contains(p)) {
                e.setQuitMessage(translate("&2&l> &a" + p.getName() + " &fis no longer spectating."));
            }
        }

         */
    }
}
