package com.redblock6.survival.mccore.functions;

import com.redblock6.survival.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveVanillaXP {
    private static final Main plugin = Main.getInstance();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();
    private static int currentexp;

    public static void removePlayerExp(Player p, int amount) {
        p.setExp(p.getExp() - amount);
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
                        p.giveExp(amount);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 2);

                        p.sendTitle(CreateGameMenu.translate("&4&l⬞ &c0 &4&l⬞"), CreateGameMenu.translate((currentexp + expgiven) + "&7/&c" + p.getTotalExperience()), 0, 40, 10);
                        if (mysql.getEXP(p.getUniqueId()) >= (mysql.getEXPMax(p.getUniqueId()) - 1)) {
                            levelUp(p);
                        }
                    } else {
                        expgiven++;
                        p.sendTitle(CreateGameMenu.translate("&4&l⬝ &c" + (amount - expgiven) + " &4&l⬝"), CreateGameMenu.translate((currentexp + expgiven) + "&7/&c" + p.getTotalExperience()), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                        // j.incrBy(p.getUniqueId() + "Exp", 1);
                        if (p.getExp() >= (p.getTotalExperience()) - 1) {
                            levelUp(p);
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

    public static void levelUp(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        mysql.resetEXP(p.getUniqueId());
        mysql.updateEXPMax(p.getUniqueId());
        mysql.updateLevel(p.getUniqueId());
        p.sendTitle(CreateGameMenu.translate("&2&lLEVEL UP"), CreateGameMenu.translate("&fYou are now level &a" + p.getLevel()), 0, 20, 10);

        String line = CreateGameMenu.translate("&2&m---------------------------------");
        String levelup = CreateGameMenu.translate("&2&lLEVEL UP");
        String blank = "";
        String nowlevel = CreateGameMenu.translate("&fYou are now &2&lLEVEL &a" + mysql.getLevel(p.getUniqueId()));

        p.sendMessage(line);
        p.sendMessage(levelup);
        p.sendMessage(blank);
        p.sendMessage(blank);
        p.sendMessage(nowlevel);
        p.sendMessage(line);
    }
}
