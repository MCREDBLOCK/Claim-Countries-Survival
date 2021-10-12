package com.redblock6.survival.mccore.events;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.CountriesDatabase;
import com.redblock6.survival.countries.CountrySelectorHandoff;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;

import static com.redblock6.survival.Main.translate;

public class ChatEvent implements Listener {
    private static final ArrayList<Player> searching = new ArrayList();

    public static void iniSearch(Player p) {
        searching.add(p);
        p.closeInventory();
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
        p.sendTitle(translate("&4&l↙"), translate("&fType your search in the chat!"), 10, 1000000000, 10);
    }

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
}
