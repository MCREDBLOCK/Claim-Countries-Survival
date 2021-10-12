package com.redblock6.survival.countries;

import com.redblock6.survival.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.redblock6.survival.Main.translate;
import static com.redblock6.survival.mccore.functions.CreateGameMenu.createGuiItem;
import static com.redblock6.survival.mccore.utils.VariousUtils.isDirEmpty;

public enum CountriesDatabase {
    Afghanistan,
    Albania,
    Algeria,
    Andorra,
    Angola,
    Antigua,
    Barbuda,
    Argentina,
    Armenia,
    Australia,
    Austria,
    Austrian_Empire,
    Azerbaijan,
    Bahamas,
    Bahrain,
    Bangladesh,
    Barbados,
    Bavaria,
    Belarus,
    Belgium,
    Belize,
    Benin,
    Bolivia,
    Bosnia,
    Botswana,
    Brazil,
    Brunei,
    Brunswick,
    Bulgaria,
    Burkina_Faso,
    Burma,
    Burundi,
    Cabo_Verde,
    Cambodia,
    Cameroon,
    Canada,
    Cayman_Islands,
    Central_African_Republic,
    Chad,
    Chile,
    China,
    Colombia,
    Comoros,
    Congo_Free_State,
    Costa_Rica,
    Croatia,
    Cuba,
    Cyprus,
    Czechia,
    Czechoslovakia,
    Democratic_Republic_of_the_Congo,
    Denmark,
    Djibouti,
    Dominica,
    Dominican_Republic,
    Ecuador,
    Egypt,
    El_Salvador,
    Equatorial_Guinea,
    Eritrea,
    Estonia,
    Eswatini,
    Ethiopia,
    Fiji,
    Finland,
    France,
    Gabon,
    Gambia,
    Georgia,
    Germany,
    Ghana,
    Greece,
    Grenada,
    Guatemala,
    Guinea,
    Guinea_Bissau,
    Guyana,
    Haiti,
    Holy_See,
    Honduras,
    Hungary,
    Iceland,
    India ,
    Indonesia,
    Iran,
    Iraq,
    Ireland,
    Israel,
    Italy,
    Jamaica,
    Japan,
    Jordan,
    Kazakhstan,
    Kenya,
    Kiribati,
    Korea,
    Kosovo,
    Kuwait,
    Kyrgyzstan,
    Laos,
    Latvia,
    Lebanon,
    Lesotho,
    Liberia,
    Libya,
    Liechtenstein,
    Lithuania,
    Luxembourg,
    Madagascar,
    Malawi,
    Malaysia,
    Maldives,
    Mali,
    Malta,
    Marshall_Islands,
    Mauritania,
    Mauritius,
    Mexico,
    Micronesia,
    Moldova,
    Monaco,
    Mongolia,
    Montenegro,
    Morocco,
    Mozambique,
    Namibia,
    Nauru,
    Nepal,
    Netherlands,
    New_Zealand,
    Nicaragua,
    Niger,
    Nigeria,
    North_Macedonia,
    Norway,
    Oman,
    Pakistan,
    Palau,
    Panama,
    Papua_New_Guinea,
    Paraguay,
    Peru,
    Philippines,
    Poland,
    Portugal,
    Qatar,
    South_Korea,
    Republic_of_the_Congo,
    Romania,
    Russia,
    Rwanda,
    Saint_Lucia,
    Samoa,
    San_Marino,
    Saudi_Arabia,
    Senegal,
    Serbia,
    Seychelles,
    Sierra_Leone,
    Singapore,
    Slovakia,
    Slovenia,
    Solomon_Islands,
    Somalia,
    South_Africa,
    South_Sudan,
    Spain,
    Sri_Lanka,
    Sudan,
    Suriname,
    Sweden,
    Switzerland,
    Syria,
    Tajikistan,
    Tanzania,
    Texas,
    Thailand,
    Timor_Leste,
    Togo,
    Tonga,
    Trinidad_and_Tobago,
    Tunisia,
    Turkey,
    Turkmenistan,
    Tuvalu,
    Uganda,
    Ukraine,
    United_Arab_Emirates,
    United_Kingdom,
    Uruguay,
    United_States,
    Uzbekistan,
    Vanuatu,
    Venezuela,
    Vietnam,
    Yemen,
    Zambia,
    Zimbabwe;
    private static final Main pl = Main.getInstance();
    public static Inventory inv = pl.getServer().createInventory(null, 54, "Select from 0 countries");
    public static int slot = 0;
    public static int i = 0;
    static int finali = CountriesDatabase.values().length;
    public static HashMap<Player, CountriesPreference> preferences = new HashMap<>();
    public static HashMap<Player, Integer> radius = new HashMap<>();

    public static void openCountryClaimer(Player p, CountriesDatabase country) {
        Inventory i = pl.getServer().createInventory(null, 54, "Claim " + getFormattedName(country.toString()));
        String color = "&4&l";
        String selected = "&4&lCLICK TO SELECT";

        if (preferences.get(p).equals(CountriesPreference.NoSplit_TRUE)) {
            i.setItem(10, createGuiItem(Material.PLAYER_HEAD, ChatColor.translateAlternateColorCodes('&', "&2&lNO SPLIT"), translate("&2&m-----------------------"), translate("&2&lSELECTED"), translate("&2&m-----------------------")));
        }
        i.setItem(16, createGuiItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&2&LCLAIM " + getFormattedName(country.toString())), translate("&2&m-----------------------"), translate("&4&lCLICK TO CLAIM"), translate("&2&m-----------------------")));
        i.setItem(19, createGuiItem(Material.SKELETON_SKULL, ChatColor.translateAlternateColorCodes('&', "&4&lDOUBLE SPLIT"), translate("&4&m-----------------------"), translate("&fYour country will be split"), translate("&fwith &c1 other player&f!"), "",  translate("&4&lCLICK TO SELECT"), translate("&4&m-----------------------")));
        // i.setItem(15, createGuiItem(Material.WOODEN_SWORD, ChatColor.translateAlternateColorCodes('&', "&4&lMOB WARS"), translate("&4&m-----------------------"), translate("&fTry to kill all mobs. If you do so, your team"), translate("&fwins the game"), "", translate("&c%{players.mw}% &4&lPLAYERS"), translate("&4&m-----------------------")));
        i.setItem(22, createGuiItem(Material.SPYGLASS, ChatColor.translateAlternateColorCodes('&', "&2&lRADIUS: 1000 BLOCKS"), translate("&2&m-----------------------"), translate("&2&lCLICK TO CHANGE"), translate("&2&m-----------------------")));
        i.setItem(25, createGuiItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&2&LCLAIM " + getFormattedName(country.toString())), translate("&2&m-----------------------"), translate("&4&lCLICK TO CLAIM"), translate("&2&m-----------------------")));
        i.setItem(28, createGuiItem(Material.WITHER_SKELETON_SKULL, ChatColor.translateAlternateColorCodes('&', "&4&lTRIPPLE SPLIT" + getFormattedName(country.toString())), translate("&4&m-----------------------"), translate("&fYour country will be split"), translate("&fwith &c2 other players&f!"), "",  translate("&4&lCLICK TO SELECT"), translate("&4&m-----------------------")));
        i.setItem(34, createGuiItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&2&LCLAIM " + getFormattedName(country.toString())), translate("&2&m-----------------------"), translate("&4&lCLICK TO CLAIM"), translate("&2&m-----------------------")));
        inv.setItem(36, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lBACK")));
        p.openInventory(i);
    }

    public static void openCountrySelector(Player p, CountrySelectorHandoff handoff, int page, String search) {
        if (handoff.equals(CountrySelectorHandoff.Select)) {
            inv = pl.getServer().createInventory(null, 54, "Select from " + finali + " countries - Country Selector");
        } else if (handoff.equals(CountrySelectorHandoff.Claim))  {
            inv = pl.getServer().createInventory(null, 54, "Select from " + finali + " countries - Country Claimer");
        }
        slot = 0;
        if (page == 1) {
            i = 0;
        } else if (page == 2) {
            i = 45;
        } else if (page == 3) {
            i = 90;
        } if (page == 4) {
            i = 135;
        } if (page == 5) {
            i = 180;
        } if (page == 6) {
            i = 225;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (!(slot == 45)) {
                        if (i != finali) {
                                ArrayList<CountriesDatabase> countries = new ArrayList<>();
                                for (CountriesDatabase countryloop : CountriesDatabase.values()) {
                                    if (countryloop.toString().contains(getFormattedName(search))) {
                                        countries.add(countryloop);
                                    }
                                }
                                finali = countries.size();
                                CountriesDatabase country = countries.get(i);
                                if (!isDirEmpty(Countries.getConfigPath().toPath())) {
                                    for (File file : Countries.getConfigPath().listFiles()) {
                                        if (file.getName().equals(country.toString())) {
                                            inv.setItem(slot, createGuiItem(Material.ORANGE_STAINED_GLASS, ChatColor.translateAlternateColorCodes('&', "&6&l" + getFormattedName(country.toString()).toUpperCase())));
                                        } else {
                                            inv.setItem(slot, createGuiItem(Material.GREEN_STAINED_GLASS, ChatColor.translateAlternateColorCodes('&', "&2&l" + getFormattedName(country.toString()).toUpperCase())));
                                        }
                                    }
                                } else {
                                    inv.setItem(slot, createGuiItem(Material.GREEN_STAINED_GLASS, ChatColor.translateAlternateColorCodes('&', "&2&l" + getFormattedName(country.toString()).toUpperCase())));
                                }


                            slot++;
                            i++;
                        } else {
                            cancel();
                        }
                    } else {
                        if (i !=  CountriesDatabase.values().length) {
                            inv.setItem(53, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lNEXT &7(To page " + (page + 1) + ")")));
                        }
                    }
                } catch (Exception ex) {
                    //ignored
                }

            }
        }.runTaskTimer(pl, 0, 0);

        if (page > 1) {
            inv.setItem(45, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lBACK &7(To page " + (page - 1) + ")")));
        } if (page < 6) {
            if (i !=  CountriesDatabase.values().length) {
                inv.setItem(53, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lNEXT &7(To page " + (page + 1) + ")")));
            }
        }
        inv.setItem(46, createGuiItem(Material.COMPASS, ChatColor.translateAlternateColorCodes('&', "&4&lSEARCH")));
        inv.setItem(49, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE")));

        p.openInventory(inv);
    }

    public static void openCountrySelector(Player p, CountrySelectorHandoff handoff, int page) {
        if (handoff.equals(CountrySelectorHandoff.Select)) {
            inv = pl.getServer().createInventory(null, 54, "Select from " + finali + " countries - Country Selector");
        } else if (handoff.equals(CountrySelectorHandoff.Claim))  {
            inv = pl.getServer().createInventory(null, 54, "Select from " + finali + " countries - Country Claimer");
        }
        slot = 0;
        finali = CountriesDatabase.values().length;
        if (page == 1) {
            i = 0;
        } else if (page == 2) {
            i = 45;
        } else if (page == 3) {
            i = 90;
        } if (page == 4) {
            i = 135;
        } if (page == 5) {
            i = 180;
        } if (page == 6) {
            i = 225;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (!(slot == 45)) {
                        if (i != finali) {
                            CountriesDatabase country = CountriesDatabase.values()[i];
                            if (!isDirEmpty(Countries.getConfigPath().toPath())) {
                                p.sendMessage("There are files");
                                for (File file : Countries.getConfigPath().listFiles()) {
                                    if (file.getName().equals(country.toString())) {
                                        inv.setItem(slot, createGuiItem(Material.ORANGE_STAINED_GLASS, ChatColor.translateAlternateColorCodes('&', "&6&l" + getFormattedName(country.toString()).toUpperCase())));
                                    } else {
                                        inv.setItem(slot, createGuiItem(Material.GREEN_STAINED_GLASS, ChatColor.translateAlternateColorCodes('&', "&2&l" + getFormattedName(country.toString()).toUpperCase())));
                                    }
                                }
                            } else {
                                inv.setItem(slot, createGuiItem(Material.GREEN_STAINED_GLASS, ChatColor.translateAlternateColorCodes('&', "&2&l" + getFormattedName(country.toString()).toUpperCase())));
                            }

                            slot++;
                            i++;
                        } else {
                            cancel();
                        }
                    } else {
                        if (i !=  CountriesDatabase.values().length) {
                            inv.setItem(53, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lNEXT &7(To page " + (page + 1) + ")")));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }.runTaskTimer(pl, 0, 0);

        if (page > 1) {
            inv.setItem(45, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lBACK &7(To page " + (page - 1) + ")")));
        } if (page < 6) {
            if (i !=  CountriesDatabase.values().length) {
                inv.setItem(53, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lNEXT &7(To page " + (page + 1) + ")")));
            }
        }
        inv.setItem(46, createGuiItem(Material.COMPASS, ChatColor.translateAlternateColorCodes('&', "&4&lSEARCH")));
        inv.setItem(49, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE")));

        p.openInventory(inv);
    }

    public static String getFormattedName(String s) {
        return s.replaceAll("_", " ");
    }
}
