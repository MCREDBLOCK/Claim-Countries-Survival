package com.redblock6.survival.mccore.events;

import com.redblock6.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveEvent implements Listener {
    private boolean streaming = Main.streaming;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (streaming) {
            if (!Bukkit.getWhitelistedPlayers().contains(p)) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendTitle(Main.translate("&4&lSPECTATING"), Main.translate("&fYou are spectating!"), 10, 20, 10);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                e.setJoinMessage(Main.translate("&4&l> &c" + p.getDisplayName() + " &fis now spectating."));
            } else {
                p.sendTitle(Main.translate("&4&lSTREAMER"), Main.translate("&fStreamer mode is enabled."), 10, 20, 10);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
            }
        }
    }
}
