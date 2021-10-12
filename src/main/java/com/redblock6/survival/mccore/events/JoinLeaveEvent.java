package com.redblock6.survival.mccore.events;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.Countries;
import com.redblock6.survival.countries.CountriesDatabase;
import com.redblock6.survival.countries.CountriesSetup;
import com.redblock6.survival.countries.CountrySelectorHandoff;
import com.redblock6.survival.mccore.achievements.AchDatabase;
import com.redblock6.survival.mccore.achievements.AchLibrary;
import com.redblock6.survival.mccore.achievements.HAchType;
import com.redblock6.survival.mccore.bot.BotMain;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
                    p.sendTitle(translate("&4&lRUN /connect IN #general"), translate("&fThis is required to play on the &cRedSMP&f!"), 10, 1000000000, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                } else if (Countries.isInTruce(p)) {
                    p.sendTitle(Main.translate("&4&lCLAIM A COUNTRY"), translate("&fClaim a country to settle in!"), 10, 1000000000, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                    CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 1);
                }
            }
        } else {
            if (!Bukkit.getWhitelistedPlayers().contains(p)) {
                p.kickPlayer("You are not whitelisted on this server!");
            }
        }
    }

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
                /*
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if (i == 0) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                        } else if (i == 1) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                        } else if (i == 2) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 0);
                        } else if (i == 3) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
                        } else if (i == 4) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 2);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 2);
                        } else if (i == 5) {
                            ServerConnector.sendServer(p, "PRE-1");
                            cancel();
                        }
                        i++;
                    }
                }.runTaskTimer(pl, delay, 20);

                 */
                } else if (message.equalsIgnoreCase("NO")) {
                    p.sendTitle(translate("&4&lTYPE YOUR IGN IN #general"), translate("&fThis will link your discord account to your mc account!"), 10, 1000000000, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                }
            }
        }



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
