package com.redblock6.survival;

import com.redblock6.survival.countries.Countries;
import com.redblock6.survival.mccore.bot.BotMain;
import com.redblock6.survival.mccore.functions.CreateGameMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JavaPlugin {

    private static Main instance;
    public static boolean streaming;
    public static BotMain bot;
    public static JedisPool pool;
    private Connection connection;
    // private static HikariDataSource ds;
    public String host, database, username, password, global_table, hub_table, kitpvp_table, oitq_table;
    public int port;

    public void mysqlSetup() {
        host = "192.168.1.252";
        port = 3306;
        username = "mc";
        database = "mc_user";
        global_table = "GLOBAL";
        hub_table = "HUB";
        kitpvp_table = "KITPVP";
        oitq_table = "OITQ";
        password = "minecraft";

        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
            Bukkit.getConsoleSender().sendMessage(CreateGameMenu.translate("&2&l> &fConnected to &aMySQL &fusing user: &a" + this.username));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onEnable() {
        instance = this;
        streaming = true;

        Register.register();
        pool = new JedisPool("192.168.1.222", Integer.parseInt("6379"));
        bot = new BotMain(this);
        mysqlSetup();
        /*
        boolean containsUS = false;
        for (File file : Truce.getConfigPath().listFiles()) {
            if (file.getName().equals("United_States")) {
                containsUS = true;
            }
        }
        if (!containsUS)  {
            for (Countries country : Countries.values()) {

            }
        }
         */
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Countries.registerTruces();
    }

    public static Main getInstance() {
        return instance;
    }
    public static BotMain getBot() {
        return bot;
    }

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static World getMultiverseWorld(String name) {
        for (World world : Bukkit.getWorlds()) {
            if (world.getName().equals(name)) {
                return world;
            }
        }
        return null;
    }
}
