package com.redblock6.survival.countries;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onMessageSend(AsyncPlayerChatEvent e) {
        if (Countries.isInTruce(e.getPlayer())) {
            e.setFormat(ChatColor.YELLOW + "" + ChatColor.BOLD + Countries.getTruce(e.getPlayer()).getName()
                    + " " + e.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + ">> " + ChatColor.WHITE + e.getMessage());
        }
    }

}
