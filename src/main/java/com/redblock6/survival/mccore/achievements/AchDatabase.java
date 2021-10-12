package com.redblock6.survival.mccore.achievements;

import com.redblock6.survival.Main;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

public class AchDatabase {
    Player p;
    ArrayList<HAchType> achH = new ArrayList<>();
    ArrayList<KAchType> achK = new ArrayList<>();

    public AchDatabase(Player p) {
        this.p = p;
    }

    public ArrayList<HAchType> getHubAch() {
        Jedis j = Main.pool.getResource();
        for (HAchType ach : HAchType.values()) {
            if (j.get(ach.name() + p.getUniqueId()) != null) {
                achH.add(ach);
            }
        }
        j.close();

        return achH;
    }

    public ArrayList<KAchType> getKitAch() {
        Jedis j = Main.pool.getResource();
        for (KAchType ach : KAchType.values()) {
            if (j.get(ach.name() + p.getUniqueId()) != null) {
                achK.add(ach);
            }
        }
        j.close();

        return achK;
    }

    public void revokeHAch(HAchType ach) {
        Jedis j = Main.pool.getResource();
        j.del(ach.name() + p.getUniqueId());
        j.close();
    }

    public void revokeKAch(KAchType ach) {
        Jedis j = Main.pool.getResource();
        j.del(ach.name() + p.getUniqueId());
        j.close();
    }

    public void addHAch(HAchType ach) {
        Jedis j = Main.pool.getResource();
        j.set(ach.name() + p.getUniqueId(), "HAS");
        j.close();
    }

    public void addKAch(KAchType ach) {
        Jedis j = Main.pool.getResource();
        j.set(ach.name() + p.getUniqueId(), "HAS");
        j.close();
    }
}
