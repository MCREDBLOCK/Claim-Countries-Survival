package com.redblock6.survival.mccore.utils;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.Countries;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

import static com.redblock6.survival.Main.translate;

public class Aplayer {
	
	private static Main pl = Main.getInstance();
	private static HashMap<OfflinePlayer, Aplayer> players = new HashMap<>();
	
	private OfflinePlayer player;
	private boolean isTeleporting;
	private boolean pvping;
	private int pvpCooldown = 0;
	private int tpDelay;
	private ArrayList<Countries> countriesInvites = new ArrayList<>();
	
	private Aplayer(OfflinePlayer player) {
		this.player = player;
	}
	
	public static Aplayer getAplayer(OfflinePlayer p) {
		if(players.containsKey(p)) {
			return players.get(p);
		}
		else {
			Aplayer ap = new Aplayer(p);
			players.put(p, ap);
			return ap;
		}
	}
	
	public OfflinePlayer getPlayer() {
		return player;
	}


	public boolean isPvping() {
		return pvping;
	}

	public void setPvping(boolean pvping) {
		this.pvping = pvping;
	}
	
	public void startPvpCountdown() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (pvpCooldown == 0) {
					setPvping(false);
					getPlayer().getPlayer().sendMessage(ChatColor.RED + "Exited PVP");
					cancel();
				}
				else {
					pvpCooldown--;
				}
			}
		}.runTaskTimer(pl, 0, 20);
	}

	public int getTpDelay() {
		return tpDelay;
	}

	public void setTpDelay(int tpDelay) {
		this.tpDelay = tpDelay;
	}

	public Countries getTruce() {
		return Countries.getTruce(player);
	}

	public ArrayList<Countries> getTruceInvites() {
		return countriesInvites;
	}

	public ArrayList<BukkitTask> inviteRunnables = new ArrayList<>();

	public void addTruceInvite(Countries countries) {
		Player p = Bukkit.getPlayer(player.getUniqueId());
		countriesInvites.add(countries);
		if (player.isOnline()) {
			p.sendMessage(translate("&2&l> &fYou've been invited to the" + countries.getName() + "&f! &2&lACCEPT  &4&lDENY"));
		}
		BukkitTask run = new BukkitRunnable() {
			@Override
			public void run() {
				countriesInvites.remove(countries);
				if (player.isOnline()) {
					p.sendMessage(translate("&2&l> &fYour invite from the " + countries.getName() + "&f! &2&lACCEPT  &4&lDENY"));
				}
			}
		}.runTaskLater(pl, 20 * 120);
		inviteRunnables.add(run);
	}

	public void removeTruceInvite(Countries countries) {
		countriesInvites.remove(countries);
	}

	public boolean hasTruceInvite(Countries countries) {
		if (countriesInvites.contains(countries)) {
			return true;
		}
		return false;
	}
	
}
