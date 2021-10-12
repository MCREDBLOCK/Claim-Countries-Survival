package com.redblock6.survival.mccore.functions;

import com.redblock6.survival.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveCoinsXP {
    private static final Main plugin = Main.getInstance();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();
    private static int currentcoins;
    private static int currentexp;

    public static void GivePlayerDust(Player p, int amount) {
        int ticks;

        try {
            //play the giving coins sound thingy
            new BukkitRunnable() {
                int coinsgiven = 0;

                @Override
                public void run() {
                    if (coinsgiven == amount || coinsgiven > amount) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        mysql.updateDust(p.getUniqueId(), amount);
                        p.sendMessage(CreateGameMenu.translate("&5&l> &fYou now have &d" + mysql.getDust(p.getUniqueId()) + " Magic Dust"));

                        p.sendTitle(CreateGameMenu.translate("&5&l☆ &d0 &5&l☆"), CreateGameMenu.translate("&fYou now have &d" + mysql.getDust(p.getUniqueId()) + " Magic Dust"), 0, 40, 10);
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                        //Bukkit.getLogger().info("FINAL Coins Given: " + coinsgiven + ", Amount " + amount);
                    } else {
                        //Bukkit.getLogger().info("Coins Given: " + coinsgiven + ", Amount " + amount);
                        coinsgiven++;
                        currentcoins = mysql.getDust(p.getUniqueId());
                        p.sendTitle(CreateGameMenu.translate("&5&l★ &d" + (amount - coinsgiven) + " &5&l★"), CreateGameMenu.translate("&fYou now have &d" + (currentcoins + coinsgiven) + " Magic Dust"), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, 1);

            //plugin.getServer().getLogger().info("> Added " + amount + " coins for " + p.getName() + " in MySQL.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD"), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 140, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p.getName() + " " + amount + " coins with MySQL.");
        }
    }

    public static void GivePlayerEXP(Player p, int amount) {
        try {
            //play the giving coins sound thingy
            new BukkitRunnable() {
                int expgiven = 0;

                @Override
                public void run() {
                    if (expgiven == amount) {
                        cancel();
                        mysql.updateEXP(p.getUniqueId(), amount);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 2);
                        p.sendMessage(CreateGameMenu.translate("&4&l> &fYou now have &c" + mysql.getEXP(p.getUniqueId()) + "&7/&c" + mysql.getEXPMax(p.getUniqueId()) + " &fexperience."));

                        p.sendTitle(CreateGameMenu.translate("&4&l⬞ &c0 &4&l⬞"), CreateGameMenu.translate((currentexp + expgiven) + "&7/&c" + mysql.getEXPMax(p.getUniqueId())), 0, 40, 10);
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                        if (mysql.getEXP(p.getUniqueId()) >= (mysql.getEXPMax(p.getUniqueId()) - 1)) {
                            levelUp(p);
                            GivePlayerDust(p, 50);
                        }
                    } else {
                        expgiven++;
                        currentcoins = mysql.getDust(p.getUniqueId());
                        p.sendTitle(CreateGameMenu.translate("&4&l⬝ &c" + (amount - expgiven) + " &4&l⬝"), CreateGameMenu.translate((currentexp + expgiven) + "&7/&c" + mysql.getEXPMax(p.getUniqueId())), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                        // j.incrBy(p.getUniqueId() + "Exp", 1);
                        if (mysql.getEXP(p.getUniqueId()) >= (mysql.getEXPMax(p.getUniqueId()) - 1)) {
                            levelUp(p);
                            GivePlayerDust(p, 50);
                        }
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, 1);

            //plugin.getServer().getLogger().info("> Added " + amount + " exp for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amount + " exp with redis.");
        }
    }

    public static void GivePlayerBoth(Player p, int amountcoins, int amountexp) {
        try {
            //play the giving coins sound thingy
            new BukkitRunnable() {
                int coinsgiven = 0;
                int expgiven = 0;

                @Override
                public void run() {
                    if (coinsgiven == amountcoins && expgiven == amountexp) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        mysql.updateDust(p.getUniqueId(), amountcoins);
                        mysql.updateEXP(p.getUniqueId(), amountexp);
                        p.sendMessage(CreateGameMenu.translate("&4&l> &fYou now have &d" + mysql.getDust(p.getUniqueId()) + " Magic Dust &fand &c" + mysql.getEXP(p.getUniqueId()) + "&7&l/&c" + mysql.getEXPMax(p.getUniqueId()) + " &fexperience."));
                        CreateScoreboard.setScoreboard(p, "Normal", false);

                        p.sendTitle(CreateGameMenu.translate("&5&l★ &5" + "0" + " &5&l★ &7&l- &4&l⬝ &c" + "0" + " &4&l⬝"), CreateGameMenu.translate("&fYou now have &d" + mysql.getDust(p.getUniqueId()) + " Magic Dust &fand &c" + mysql.getEXP(p.getUniqueId()) + "&7&l/&c" + mysql.getEXPMax(p.getUniqueId()) + " &fexperience."), 0, 40, 10);
                    } else {
                        p.sendTitle(CreateGameMenu.translate("&5&l★ &5" + (amountcoins - coinsgiven) + " &5&l★ &7&l- &4&l⬝ &c" + (amountexp - expgiven) + " &4&l⬝"), CreateGameMenu.translate("&fYou now have &d" + (coinsgiven + amountcoins) + " Magic Dust &fand &c" + (expgiven + amountexp) + "&7&l/&c" + mysql.getEXPMax(p.getUniqueId()) + " &fexperience."), 0, 20, 0);
                        if (coinsgiven != amountcoins) {
                            coinsgiven++;
                            // j.incrBy(p.getUniqueId() + "Coins", 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                        }
                        if (expgiven != amountexp) {
                            expgiven++;
                            // j.incrBy(p.getUniqueId() + "Exp", 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                            if (mysql.getEXP(p.getUniqueId()) >= (mysql.getEXPMax(p.getUniqueId()) - 1)) {
                                levelUp(p);
                                coinsgiven -= 50;
                            }
                        }
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, 1);

            plugin.getServer().getLogger().info("> Added " + amountcoins + " Magic Dust and " + amountexp + " exp for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amountcoins + " Magic Dust and " + amountexp + " exp with redis.");
        }
    }

    public static void levelUp(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        mysql.resetEXP(p.getUniqueId());
        mysql.updateEXPMax(p.getUniqueId());
        mysql.updateLevel(p.getUniqueId());
        p.sendTitle(CreateGameMenu.translate("&2&lLEVEL UP"), CreateGameMenu.translate("&fYou are now level &a" + mysql.getLevel(p.getUniqueId()) + " &fand need &a" + mysql.getEXPMax(p.getUniqueId()) + " &fmore exp to level up."), 0, 20, 10);

        String line = CreateGameMenu.translate("&2&m---------------------------------");
        String levelup = CreateGameMenu.translate("&2&lLEVEL UP");
        String blank = "";
        String reward = CreateGameMenu.translate("&6&l+ &e50 coins");
        String newchallenge = CreateGameMenu.translate("&fNow you have to get &a" + mysql.getEXPMax(p.getUniqueId()));
        String newchallenge2 = CreateGameMenu.translate("&fmore exp to levelup");
        String nowlevel = CreateGameMenu.translate("&fYou are now &2&lLEVEL &a" + mysql.getLevel(p.getUniqueId()));

        p.sendMessage(line);
        p.sendMessage(levelup);
        p.sendMessage(blank);
        p.sendMessage(reward);
        p.sendMessage(blank);
        p.sendMessage(nowlevel);
        p.sendMessage(newchallenge);
        p.sendMessage(newchallenge2);
        p.sendMessage(line);
    }
}
