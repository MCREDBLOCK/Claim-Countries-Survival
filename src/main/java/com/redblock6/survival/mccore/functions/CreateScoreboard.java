package com.redblock6.survival.mccore.functions;

import com.redblock6.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import redis.clients.jedis.Jedis;

import static com.redblock6.survival.mccore.functions.CreateGameMenu.translate;
import static com.redblock6.survival.Main.pool;

public class CreateScoreboard {

    public static Objective o;
    private static final Main plugin = Main.getInstance();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();


    public static Scoreboard normal(Player p) {
        //get the pool
        Jedis j = pool.getResource();

        //create scoreboard
        Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();

        String s1 = ChatColor.translateAlternateColorCodes('&', "&4&lHUB-" + plugin.getConfig().getInt("hub-identifier"));
        Objective o = b.registerNewObjective("HUB", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(s1);

        //bottom line
        String line = ChatColor.translateAlternateColorCodes('&', "&7&m---------------------");
        Score line1 = o.getScore(line);
        line1.setScore(0);

        //ip
        String ip = ChatColor.translateAlternateColorCodes('&', "&4&lmc.redblock6.com");
        Score ipline = o.getScore(ip);
        ipline.setScore(1);

        //blank
        String blank2 = translate("&4&l&c&m");
        Score blankline2 = o.getScore(blank2);
        blankline2.setScore(2);

        //coins
        Team coins = b.registerNewTeam("c");
        coins.addEntry(ChatColor.YELLOW + "" + ChatColor.YELLOW);
        String coinsprefix = ChatColor.translateAlternateColorCodes('&', "&5&lMAGIC DUST");
        String coinssuffix = ChatColor.translateAlternateColorCodes('&', " &d" + mysql.getDust(p.getUniqueId()));
        coins.setPrefix(coinsprefix);
        coins.setSuffix(coinssuffix);
        o.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(3);

        //blank
        String blank = "";
        Score blankline = o.getScore(blank);
        blankline.setScore(4);

        //exp
        Team exp = b.registerNewTeam("e");
        exp.addEntry(ChatColor.RED + "" + ChatColor.GRAY);
        String expprefix = ChatColor.translateAlternateColorCodes('&', "&4╚═ &c" + mysql.getEXP(p.getUniqueId()));
        String expsuffix = ChatColor.translateAlternateColorCodes('&', "&7/&c" + mysql.getEXPMax(p.getUniqueId()));
        exp.setPrefix(expprefix);
        exp.setSuffix(expsuffix);
        o.getScore(ChatColor.RED + "" + ChatColor.GRAY).setScore(5);

        //level
        Team level = b.registerNewTeam("l");
        level.addEntry(ChatColor.DARK_RED + "" + ChatColor.RED);
        String levelprefix = ChatColor.translateAlternateColorCodes('&', "&4&lLEVEL");
        String levelsuffix = ChatColor.translateAlternateColorCodes('&', " &c" + mysql.getLevel(p.getUniqueId()));
        level.setPrefix(levelprefix);
        level.setSuffix(levelsuffix);
        o.getScore(ChatColor.DARK_RED + "" + ChatColor.RED).setScore(6);

        //top line
        String line2 = ChatColor.translateAlternateColorCodes('&', "&4&l&7&m---------------------");
        Score line2line = o.getScore(line2);
        line2line.setScore(7);

        CreateScoreboard.o = o;
        j.close();
        return b;
    }

    public static void setScoreboard(Player p, String type, Boolean setscoreboard) {
        if (type.equals("Normal")) {
            if (setscoreboard.equals(false)) {
                    //get the pool
                    // Jedis j = pool.getResource();

                    Scoreboard b = p.getScoreboard();
                    // String s1 = ChatColor.translateAlternateColorCodes('&', "&4&lHUB-" + plugin.getConfig().getInt("hub-identifier"));

                    Team dust = b.getTeam("c");
                    Team xp = b.getTeam("e");
                    Team level = b.getTeam("l");

                    dust.setPrefix(ChatColor.translateAlternateColorCodes('&', "&5&lMAGIC DUST"));
                    dust.setSuffix(ChatColor.translateAlternateColorCodes('&', " &d" + mysql.getDust(p.getUniqueId())));
                    o.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(3);

                    String expprefix = ChatColor.translateAlternateColorCodes('&', "&4╚═ &c" + mysql.getEXP(p.getUniqueId()));
                    String expsuffix = ChatColor.translateAlternateColorCodes('&', "&7/&c" + mysql.getEXPMax(p.getUniqueId()));
                    xp.setPrefix(expprefix);
                    xp.setSuffix(expsuffix);
                    o.getScore(ChatColor.RED + "" + ChatColor.GRAY).setScore(5);

                    level.setPrefix(translate("&4&lLEVEL"));
                    level.setSuffix(translate(" &c" + mysql.getLevel(p.getUniqueId())));
                    o.getScore(ChatColor.DARK_RED + "" + ChatColor.RED).setScore(6);

                    // j.close();
            } else if (setscoreboard.equals(true)) {
                p.setScoreboard(new CreateScoreboard().normal(p));
            }
        }
    }

}