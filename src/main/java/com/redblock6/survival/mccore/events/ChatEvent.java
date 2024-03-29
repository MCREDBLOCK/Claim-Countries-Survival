package com.redblock6.survival.mccore.events;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.CountriesDatabase;
import com.redblock6.survival.countries.CountrySelectorHandoff;
import com.redblock6.survival.mccore.functions.VoteType;
import com.redblock6.survival.mccore.functions.Voting;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;

import static com.redblock6.survival.Main.getBot;
import static com.redblock6.survival.Main.translate;

public class ChatEvent implements Listener {
    private static ArrayList<Player> confirm = new ArrayList<>();
    private static final ArrayList<Player> aQueue = new ArrayList<>();
    private static final ArrayList<Player> dQueue = new ArrayList<>();
    private static final ArrayList<Player> searching = new ArrayList();

    public static void iniSearch(Player p) {
        searching.add(p);
        p.closeInventory();
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
        p.sendTitle(translate("&4&l↙"), translate("&fType your search in the chat!"), 10, 1000000000, 10);
    }

    public static void addConfirm(Player p) {
        confirm.add(p);
    }

    public static void addAQueue(Player p) {

    }

    public static void addDQueue(Player p) {

    }

    /*
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if (searching.contains(p)) {
            p.sendTitle(Main.translate("&4&lCLAIM A COUNTRY"), translate("&fClaim a country to settle in!"), 10, 1000000000, 10);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
            CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 1, e.getMessage());
            searching.remove(p);
        }
    }

     */

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        String s = e.getMessage();
        if (confirm.contains(p)) {
            if (s.contains("yes")) {
                if (aQueue.contains(p)) {
                    p.sendTitle(translate("&2&lAGREE"), translate("&fYou voted to agree to the teleport!"));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);
                    Voting.avotes += 1;
                    Voting.voted.add(p);
                    confirm.remove(p);
                    getBot().sendVotingStatusAnnouncement(VoteType.Agree, p);
                } else if (dQueue.contains(p)) {
                    p.sendTitle(translate("&4&lDENY"), translate("&fYou deny the teleport!"));
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                    Voting.dvotes += 1;
                    Voting.voted.add(p);
                    confirm.remove(p);
                    getBot().sendVotingStatusAnnouncement(VoteType.Deny, p);
                }
            } else {
                p.sendTitle(translate("&fCanceled"), "");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                confirm.remove(p);
            }
        }
    }
}
