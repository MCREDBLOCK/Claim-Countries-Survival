package com.redblock6.survival.mccore.bot;

import net.dv8tion.jda.api.entities.User;
import org.bukkit.entity.Player;

import static com.redblock6.survival.Main.getBot;
import static com.redblock6.survival.mccore.bot.BotMain.sendMessage;

public class IssueTracker {
    static String reportTo = "600485655541317672";

    public static void reportIssue(Player p, String issue) {
        User reportToUser = getBot().bot.getUserById(reportTo);
        sendMessage(reportToUser, "❗ " + p.getName() + " had issues while connecting to MCREDBLOCK: "  + issue);
    }

    public static void reportSolvedIssue(Player p, String issue, String server) {
        User reportToUser = getBot().bot.getUserById(reportTo);
        sendMessage(reportToUser, "<:success:865587082650845195> " + p.getName() + " had issues while playing " + server + " on MCREDBLOCK but were solved: "  + issue);
    }

    public static void reportIssue(Player p, String issue, String server) {
        User reportToUser = getBot().bot.getUserById(reportTo);
        sendMessage(reportToUser, "❗ " + p.getName() + " had issues while playing " + server + " on MCREDBLOCK: "  + issue);
    }
}
