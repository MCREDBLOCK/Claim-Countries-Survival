package com.redblock6.survival.mccore.achievements;

import com.redblock6.survival.Main;
import com.redblock6.survival.mccore.functions.GiveCoinsXP;
import com.redblock6.survival.mccore.functions.MySQLSetterGetter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.redblock6.survival.Main.translate;
import static com.redblock6.survival.mccore.functions.CreateGameMenu.createGuiItem;

public class AchLibrary implements Listener {
    private static final Main plugin = Main.getInstance();
/*
    private static final KitPvPMain plugin = KitPvPMain.getInstance();

    public static void grantKitAchievement(Player p, KAchType ach) {
        sound(p);
        AchDatabase database = new AchDatabase(p);
        database.addKAch(ach);
        p.sendTitle(translate("&2&l✔"), translate("&f" + getFormattedName(ach.toString())), 10, 20, 10);
        p.sendMessage(translate("&2&m---------------------------------"));
        p.sendMessage(translate("&2&l✔ &a" + getFormattedName(ach.toString())));
        p.sendMessage(translate(""));
        p.sendMessage(translate("&4&l+ &c15 KitXP"));
        p.sendMessage(translate("&6&l+ &e25 KitCoins"));
        p.sendMessage(translate("&2&m---------------------------------"));
        if (Regions.below.contains(p)) {
            try {
                GiveKitCoinsXP.coinsQueued.put(p, GiveKitCoinsXP.coinsQueued.get(p) + 25);
                GiveKitCoinsXP.expQueued.put(p, GiveKitCoinsXP.expQueued.get(p) + 15);
            } catch (NullPointerException ex) {
                GiveKitCoinsXP.coinsQueued.put(p, 25);
                GiveKitCoinsXP.expQueued.put(p, 15);
            }
        } else {
            GiveKitCoinsXP.GivePlayerBoth(p, 25, 15);
        }
    }

    public static void grantKitAchievement(Player p, KAchType ach, boolean bbb) {
        sound(p);
        AchDatabase database = new AchDatabase(p);
        database.addKAch(ach);
        p.sendTitle(translate("&2&l✔"), translate("&f" + getFormattedName(ach.toString())), 10, 20, 10);
        p.sendMessage(translate("&2&m---------------------------------"));
        p.sendMessage(translate("&2&l✔ &a" + getFormattedName(ach.toString())));
        p.sendMessage(translate(""));
        p.sendMessage(translate("&4&l+ &c30 KitXP"));
        p.sendMessage(translate("&6&l+ &e600 KitCoins"));
        p.sendMessage(translate("&2&m---------------------------------"));
        if (Regions.below.contains(p)) {
            try {
                GiveKitCoinsXP.coinsQueued.put(p, GiveKitCoinsXP.coinsQueued.get(p) + 600);
                GiveKitCoinsXP.expQueued.put(p, GiveKitCoinsXP.expQueued.get(p) + 30);
            } catch (NullPointerException ex) {
                GiveKitCoinsXP.coinsQueued.put(p, 600);
                GiveKitCoinsXP.expQueued.put(p, 30);
            }
        } else {
            GiveKitCoinsXP.GivePlayerBoth(p, 600, 30);
        }
    }

 */

    public static void grantHubAchievement(Player p, HAchType ach) {
        sound(p);
        AchDatabase database = new AchDatabase(p);
        database.addHAch(ach);
        p.sendTitle(translate("&2&l✔"), translate("&f" + getFormattedName(ach.toString())), 10, 20, 10);
        p.sendMessage(translate("&2&m---------------------------------"));
        p.sendMessage(translate("&2&l✔ &a" + getFormattedName(ach.toString())));
        p.sendMessage(translate(""));
        p.sendMessage(translate("&4&l+ &c15 XP"));
        p.sendMessage(translate("&5&l+ &d25 Magic Dust"));
        p.sendMessage(translate("&2&m---------------------------------"));
        GiveCoinsXP.GivePlayerBoth(p, 25, 15);
    }

    public static void revokeHubAchievement(Player p, HAchType ach) {
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
        MySQLSetterGetter mysql = new MySQLSetterGetter();
        AchDatabase database = new AchDatabase(p);
        database.revokeHAch(ach);
        p.sendTitle(translate("&4&l✖"), translate("&f" + getFormattedName(ach.toString())), 10, 20, 10);
        p.sendMessage(translate("&4&m---------------------------------"));
        p.sendMessage(translate("&4&l✖ &c" + getFormattedName(ach.toString())));
        p.sendMessage(translate(""));
        p.sendMessage(translate("&4&l- &c15 XP"));
        p.sendMessage(translate("&5&l- &d25 Magic Dust"));
        p.sendMessage(translate(""));
        p.sendMessage(translate("&fAchievement Revoked!"));
        p.sendMessage(translate("&4&m---------------------------------"));
        mysql.updateEXP(p.getUniqueId(), -15);
        mysql.updateDust(p.getUniqueId(), -25);
    }

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().equals("Pick a game")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(format("&4&lHUB"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
                hub(p);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
                kitpvp(p);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&7&lCOMING SOON"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                p.sendMessage(translate("&4&l> &fThis feature is coming soon!"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
            }
        } else if (e.getView().getTitle().equals("KitPvP Achievements")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
            } else if (item.getItemMeta().getDisplayName().contains(format("&7&l"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                p.sendMessage(translate("&4&l> &fYou don't have this achievement"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().contains(format("&2&l"))) {
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
                AchLibrary.achSelector(p);
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("Hub Achievements")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
            } else if (item.getItemMeta().getDisplayName().contains(format("&7&l"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                p.sendMessage(translate("&4&l> &fYou don't have this achievement"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().contains(format("&2&l"))) {
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 2);
                AchLibrary.achSelector(p);
                e.setCancelled(true);
            }
        }
    }

    private String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void sound(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);
            }
        }.runTaskLaterAsynchronously(plugin, 2);
    }

    public static void achSelector(Player p) {
        Inventory i = plugin.getServer().createInventory(null, 27, "Pick a game");

        i.setItem(10, createGuiItem(Material.NETHER_STAR, ChatColor.translateAlternateColorCodes('&', "&4&lHUB"), translate("&4&m----------------------------------"), translate("&4&lCLICK TO VIEW"), translate("&4&m----------------------------------")));
        i.setItem(11, createGuiItem(Material.IRON_SWORD, ChatColor.translateAlternateColorCodes('&', "&4&lKITPVP"), translate("&4&m----------------------------------"), translate("&4&lCLICK TO VIEW"), translate("&4&m----------------------------------")));
        i.setItem(12, createGuiItem(Material.GRAY_WOOL, ChatColor.translateAlternateColorCodes('&', "&7&lCOMING SOON"), translate("&7&m----------------------------------"), translate("&7&lCOMING SOON"), translate("&7&m----------------------------------")));
        i.setItem(13, createGuiItem(Material.GRAY_WOOL, ChatColor.translateAlternateColorCodes('&', "&7&lCOMING SOON"), translate("&7&m----------------------------------"), translate("&7&lCOMING SOON"), translate("&7&m----------------------------------")));

        i.setItem(22, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE")));

        p.openInventory(i);
    }

    public static void hub(Player p) {
        Inventory inv = plugin.getServer().createInventory(null, 54, "Hub Achievements");
        AchDatabase ach = new AchDatabase(p);
        int finali = HAchType.values().length;

        new BukkitRunnable() {
            int i = 0;
            int slot = 0;
            @Override
            public void run() {
                if (i != finali) {
                    if (!ach.getHubAch().contains(HAchType.values()[i])) {
                        inv.setItem(slot, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.translateAlternateColorCodes('&', "&7&l" + getFormattedName(HAchType.values()[i].toString()).toUpperCase())));
                    } else {
                        inv.setItem(slot, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.translateAlternateColorCodes('&', "&2&l" + getFormattedName(HAchType.values()[i].toString()).toUpperCase())));
                    }
                    slot++;
                    i++;
                } else {
                    cancel();
                }

            }
        }.runTaskTimer(plugin, 0, 1);

        inv.setItem(45, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lBACK")));
        inv.setItem(49, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE")));

        p.openInventory(inv);
    }

    public static void kitpvp(Player p) {
        Inventory inv = plugin.getServer().createInventory(null, 54, "KitPvP Achievements");
        AchDatabase ach = new AchDatabase(p);
        int finali = KAchType.values().length;

        new BukkitRunnable() {
            int i = 0;
            int slot = 0;
            @Override
            public void run() {
                if (i != finali) {
                    if (!ach.getKitAch().contains(KAchType.values()[i])) {
                        inv.setItem(slot, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, DyeColor.GRAY.getWoolData(), ChatColor.translateAlternateColorCodes('&', "&7&l" + getFormattedName(KAchType.values()[i].toString()).toUpperCase())));
                    } else {
                        inv.setItem(slot, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, DyeColor.GREEN.getWoolData(), ChatColor.translateAlternateColorCodes('&', "&2&l" + getFormattedName(KAchType.values()[i].toString()).toUpperCase())));
                    }
                    slot++;
                    i++;
                } else {
                    cancel();
                }

            }
        }.runTaskTimer(plugin, 0, 1);

        inv.setItem(45, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lBACK")));
        inv.setItem(49, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE")));

        p.openInventory(inv);
    }

    public static String getFormattedName(String s) {
        return s.replaceAll("_", " ");
    }
}
