package com.redblock6.survival.countries;

import com.redblock6.survival.Main;
import com.redblock6.survival.mccore.utils.Aplayer;
import com.redblock6.survival.mccore.utils.SaveReloadConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import static com.redblock6.survival.Main.getBot;
import static com.redblock6.survival.Main.translate;

public class Countries {

    private static Main pl = Main.getInstance();
    private static HashMap<String, Countries> truceNames = new HashMap<>();
    private static HashMap<Aplayer, Countries> playerTruces = new HashMap<>();
    private static ArrayList<OfflinePlayer> leaders = new ArrayList<>();
    public static final int MAX_MEMBERS = 11;
    public static final String DEFAULT_DESCRIPTION = "A RedSMP Country";

    private String name;
    private File f;
    private OfflinePlayer leader;
    private String desc;
    private ArrayList<UUID> members = new ArrayList<>();

    private Countries(String name, OfflinePlayer leader) {
        this.name = name;
        this.leader = leader;
    }

    private Countries(File loadedFile) {
        f = loadedFile;
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(loadedFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        name = config.getString("Name");
        desc = config.getString("Description");
        ArrayList<UUID> members = new ArrayList<>();
        for (String s: config.getConfigurationSection("Members").getKeys(false)) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(s));
            members.add(op.getUniqueId());
            if (config.get("Members." + s + ".Role").equals("Leader")) {
                leader = op;
            }
        }
        this.members = members;
    }

    public static Countries createTruce(String name, Player owner) {
        Countries countries = new Countries(name, owner);
        truceNames.put(name, countries);
        playerTruces.put(Aplayer.getAplayer(owner), countries);

        countries.getConfigFile().getParentFile().mkdirs();
        try {
            countries.getConfigFile().createNewFile();
        } catch (IOException e) {
            // Don't print stack trace
        }
        countries.members = new ArrayList<>();
        countries.setName(name);
        countries.setDescription(DEFAULT_DESCRIPTION);
        countries.addMember(owner);
        countries.setRole(owner, CountriesRole.Leader);
        owner.sendMessage(translate("&2&l> &fSuccessfully created &a" + name + "&f!"));
        owner.playSound(owner.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
        owner.playSound(owner.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
        getBot().sendNationsAnnouncement(owner.getName(), name, CountriesReason.CREATED);
        return countries;
    }

    public void disbandTruce() {
        truceNames.remove(name);
        for (OfflinePlayer off : getMembers()) {
            playerTruces.remove(Aplayer.getAplayer(off));
        }
        getBot().sendNationsAnnouncement(getLeadersString(), name, CountriesReason.DISBANDED);
    }

    public static Countries loadTruce(File f) {
        Countries countries = new Countries(f);
        truceNames.put(countries.getName(), countries);
        return countries;
    }

    public static ArrayList<Countries> getAllTruces() {
        ArrayList<Countries> ar = new ArrayList<>();
        ar.addAll(truceNames.values());
        return ar;
    }

    public static Countries getTruce(String name) {
        return truceNames.get(name);
    }

    public static Countries getTruce(OfflinePlayer p) {
        return playerTruces.get(Aplayer.getAplayer(p));
    }

    public static boolean exists(String name) {
        if (truceNames.containsKey(name)) {
            return true;
        }
        return false;
    }

    public static boolean isInTruce(OfflinePlayer player) {
        if (playerTruces.containsKey(Aplayer.getAplayer(player))) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void registerTruces() {
        if (!getConfigPath().exists()) {
            getConfigPath().mkdirs();
        }
        for (File f: getConfigPath().listFiles()) {
            Countries countries = loadTruce(f);
            for (OfflinePlayer op : countries.getMembers()) {
                playerTruces.put(Aplayer.getAplayer(op), countries);
                truceNames.put(countries.getName(), countries);
                YamlConfiguration config = countries.getConfig();
                config.set("Members." + op.getUniqueId() + ".Name", op.getName());
            }
        }
    }

    public ArrayList<OfflinePlayer> getLeaders() {
        return leaders;
    }

    public String getLeadersString() {
        return leaders.toString();
    }

    public String getDescription() {
        if (desc == null) {
            desc = getConfig().getString("Description");
        }
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
        YamlConfiguration config = getConfig();
        config.set("Description", desc);
        SaveReloadConfig.saveAndReload(config, getConfigFile());
    }

    public ArrayList<OfflinePlayer> getMembers() {
        ArrayList<OfflinePlayer> members = new ArrayList<>();
        for (UUID id : this.members) {
            members.add(Bukkit.getOfflinePlayer(id));
        }
        return members;
    }

    public ArrayList<Player> getOnlineMembers() {
        ArrayList<Player> members = new ArrayList<>();
        for (OfflinePlayer off : getMembers()) {
            if (off.isOnline()) {
                members.add(Bukkit.getPlayer(off.getUniqueId()));
            }
        }
        return members;
    }

    public void addMember(OfflinePlayer p) {
        members.add(p.getUniqueId());
        playerTruces.put(Aplayer.getAplayer(p), this);
        YamlConfiguration config = getConfig();
        config.set("Members." + p.getUniqueId() + ".Name", p.getName());
        config.set("Members." + p.getUniqueId() + ".Role", CountriesRole.Resident.toString());
        SaveReloadConfig.saveAndReload(config, getConfigFile());
        getBot().sendNationsAnnouncement(getLeadersString(), name, CountriesReason.MEMBER_ADDED, p.getName());
    }

    public void setRole(OfflinePlayer p, CountriesRole role) {
        YamlConfiguration config = getConfig();
        config.set("Members." + p.getUniqueId() + ".Role", role.toString());
        SaveReloadConfig.saveAndReload(config, getConfigFile());
    }

    public void removeMember(OfflinePlayer p) {
        members.remove(p.getUniqueId());
        playerTruces.remove(Aplayer.getAplayer(p));
        YamlConfiguration config = getConfig();
        config.set("Members." + p.getUniqueId(), null);
        SaveReloadConfig.saveAndReload(config, getConfigFile());
        getBot().sendNationsAnnouncement(getLeadersString(), name, CountriesReason.MEMBER_KICKED, p.getName());
    }

    public String getName() {
        if (name == null) {
            name = getConfig().getString("Name");
        }
        return name;
    }

    public void setName(String name) {
        truceNames.put(name, this);
        this.name = name;
        YamlConfiguration config = getConfig();
        config.set("Name", name);
        SaveReloadConfig.saveAndReload(config, getConfigFile());
    }

    public void changeName(String name) {
        Countries countries = new Countries(name, leader);
        getBot().sendNationsAnnouncement(getLeadersString(), name, CountriesReason.CHANGED_NAME, countries.getName(), name);
        countries.setName(name);
        countries.setDescription(getDescription());
        for (OfflinePlayer member : getMembers()) {
            countries.addMember(member);
            countries.setRole(member, getRole(member));
            playerTruces.put(Aplayer.getAplayer(member), countries);
        }
        truceNames.remove(getName());
        truceNames.put(name, countries);
        getConfigFile().delete();
        countries.getConfigFile();
        getConfigFile().delete();
    }

    public CountriesRole getRole(OfflinePlayer p) {
        return CountriesRole.fromString(getConfig().getString("Members." + p.getUniqueId() + ".Role"));
    }

    public File getConfigFile() {
        if (f == null) {
            return new File(getConfigPath(), name + ".yml");
        }
        else {
            return f;
        }
    }

    public YamlConfiguration getConfig() {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(getConfigFile());
        } catch (IOException | InvalidConfigurationException e){

        }
        return config;
    }

    public static File getConfigPath() {
        return new File(pl.getDataFolder() + "/Truces");
    }

    public ArrayList<String> getInfoMessage() {
        ArrayList<String> ar = new ArrayList<>();
        ar.add(ChatColor.translateAlternateColorCodes('&', "&4&m---------------------------------"));
        ar.add(ChatColor.translateAlternateColorCodes('&', "&4&l" + getName().toUpperCase()));
        ar.add("");
        ar.add(ChatColor.translateAlternateColorCodes('&', "&f" + getDescription()));
        ar.add("");
        ar.add(ChatColor.translateAlternateColorCodes('&', "&4&lLEADERS"));
        ar.add(translate("&c" + getLeadersString()));
        ar.add("");
        ar.add(ChatColor.translateAlternateColorCodes('&', "&4&lRESIDENTS"));
        String members = "";
        ArrayList<Player> online = new ArrayList<>();
        ArrayList<OfflinePlayer> offline = new ArrayList<>();
        for (OfflinePlayer member : getMembers()) {
            for (OfflinePlayer leader : getLeaders()) {
                if (member != leader) {
                    if (member.isOnline()) {
                        online.add(Bukkit.getPlayer(member.getUniqueId()));
                    } else {
                        offline.add(member);
                    }
                }
            }
        }
        String onlineString = ChatColor.GREEN + "";
        String offlineString = ChatColor.RED + "";
        Iterator<Player> onlineIt = online.iterator();
        while(onlineIt.hasNext()) {
            Player onlineP = onlineIt.next();
            if (onlineIt.hasNext()) {
                onlineString += getRole(onlineP).getPrefix() + onlineP.getName() + ", ";
            }
            else {
                onlineString += getRole(onlineP).getPrefix() + onlineP.getName();
            }
        }
        Iterator<OfflinePlayer> offlineIt = offline.iterator();
        while(offlineIt.hasNext()) {
            OfflinePlayer offlineP = offlineIt.next();
            if (offlineP == offline.get(0)) {
                offlineString += ", ";
            }
            if (offlineIt.hasNext()) {
                offlineString += getRole(offlineP).getPrefix() + offlineP.getName() + ", ";
            }
            else {
                offlineString += getRole(offlineP).getPrefix() + offlineP.getName();
            }
        }
        members += onlineString + offlineString;
        ar.add(members);
        ar.add(ChatColor.translateAlternateColorCodes('&', "&4&m---------------------------------"));
        return ar;
    }

}
