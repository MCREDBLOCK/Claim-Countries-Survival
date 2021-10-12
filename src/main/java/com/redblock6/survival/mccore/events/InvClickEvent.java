package com.redblock6.survival.mccore.events;

import com.redblock6.survival.countries.CountriesDatabase;
import com.redblock6.survival.countries.CountriesPreference;
import com.redblock6.survival.countries.CountrySelectorHandoff;
import com.redblock6.survival.mccore.functions.CreateGameMenu;
import com.redblock6.survival.mccore.utils.VariousUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.redblock6.survival.Main.translate;

public class InvClickEvent implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().contains("Country Claimer")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lNEXT &7(To page 2)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 2);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lNEXT &7(To page 3)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 3);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lNEXT &7(To page 4)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 4);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lNEXT &7(To page 5)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 5);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lNEXT &7(To page 6)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 6);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 1)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 1);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 2)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 2);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 3)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 3);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 4)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 4);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 5)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 5);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lSEARCH"))) {
                ChatEvent.iniSearch(p);
                e.setCancelled(true);
            } else {
                if (item.getItemMeta().getDisplayName().contains(format("&6&l"))) {

                } else {
                    CountriesDatabase.openCountryClaimer(p, CountriesDatabase.valueOf(VariousUtils.reverseFormattedName(item.getItemMeta().getDisplayName())));
                }
            }
        } else if (e.getView().getTitle().contains("Claim") && !e.getView().getTitle().contains("Select from")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&2&lNO SPLIT"))) {
                p.sendMessage(translate("&2&l> &fYou already have &aNo Split &fselected"));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lNO SPLIT"))) {
                CountriesDatabase.openCountryClaimer(p, CountriesDatabase.valueOf(e.getView().getTitle().substring(6)));
                p.sendMessage(translate("&2&l> &fYou selected &aNo Split"));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                CountriesDatabase.preferences.put(p, CountriesPreference.NoSplit_TRUE);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lDOUBLE SPLIT"))) {
                p.sendMessage(translate("&2&l> &fYou already have &aDouble Split &fselected"));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lDOUBLE SPLIT"))) {
                CountriesDatabase.openCountryClaimer(p, CountriesDatabase.valueOf(e.getView().getTitle().substring(6)));
                p.sendMessage(translate("&2&l> &fYou selected &aDouble Split"));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                CountriesDatabase.preferences.put(p, CountriesPreference.NoSplit_TRUE);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lNEXT &7(To page 6)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 6);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 1)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 1);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 2)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 2);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 3)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 3);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 4)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 4);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK &7(To page 5)"))) {
                CountriesDatabase.openCountrySelector(p, CountrySelectorHandoff.Claim, 5);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lSEARCH"))) {
                ChatEvent.iniSearch(p);
                e.setCancelled(true);
            } else {
                if (item.getItemMeta().getDisplayName().contains(format("&6&l"))) {

                } else {
                    CountriesDatabase.openCountryClaimer(p, CountriesDatabase.valueOf(VariousUtils.reverseFormattedName(item.getItemMeta().getDisplayName())));
                }
            }
        }
    }

    public String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
