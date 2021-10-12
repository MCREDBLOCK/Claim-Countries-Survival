package com.redblock6.survival.mccore.functions;

import com.redblock6.survival.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLSetterGetter {

    Main pl = Main.getInstance();

    public boolean playerExistsGlobal(UUID uuid) {
        try {
            PreparedStatement global_statement = pl.getConnection().prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            global_statement.setString(1, uuid.toString());

            ResultSet results = global_statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playerExistsKit(UUID uuid) {
        try {
            PreparedStatement global_statement = pl.getConnection().prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            global_statement.setString(1, uuid.toString());

            ResultSet results = global_statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playerExistsQuiver(UUID uuid) {
        try {
            PreparedStatement global_statement = pl.getConnection().prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            global_statement.setString(1, uuid.toString());

            ResultSet results = global_statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(final UUID uuid, Player p) {
        try {
            PreparedStatement global_statement = pl.getConnection().prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            global_statement.setString(1, uuid.toString());
            ResultSet results = global_statement.executeQuery();
            results.next();

            if (!playerExistsGlobal(uuid)) {
                PreparedStatement insert = pl.getConnection().prepareStatement("INSERT INTO " + pl.global_table + " (UUID,NAME,MAGICDUST,LEVEL,EXP,EXPMAX,TUTORIAL) VALUES (?,?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, p.getName());
                insert.setInt(3, 0);
                insert.setInt(4, 1);
                insert.setInt(5, 0);
                insert.setInt(6, 100);
                insert.setString(7, "Incomplete");

                insert.executeUpdate();
            } if (!playerExistsKit(uuid)) {
                PreparedStatement insert = pl.getConnection().prepareStatement("INSERT INTO " + pl.kitpvp_table + " (UUID,NAME,KITLEVEL,KITEXP,KITEXPMAX,KITCOINS,KITKILLS,KITDEATHS,NPCEVENTS,BBB) VALUES (?,?,?,?,?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, p.getName());
                insert.setInt(3, 1);
                insert.setInt(4, 0);
                insert.setInt(5, 100);
                insert.setInt(6, 0);
                insert.setInt(7, 0);
                insert.setInt(8, 0);
                insert.setBoolean(9, false);
                insert.setBoolean(10, false);

                insert.executeUpdate();
            } if (!playerExistsQuiver(uuid)) {
                PreparedStatement insert = pl.getConnection().prepareStatement("INSERT INTO " + pl.oitq_table + " (UUID,NAME,OITQLevel,OITQExp,OITQExpMax,OITQCoins,OITQKills,OITQBowKills,OITQDeaths,OITQWins,OITQPlayed) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, p.getName());
                insert.setInt(3, 1);
                insert.setInt(4, 0);
                insert.setInt(5, 100);
                insert.setInt(6, 0);
                insert.setInt(7, 0);
                insert.setInt(8, 0);
                insert.setInt(9, 0);
                insert.setInt(10, 0);
                insert.setInt(11, 0);

                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDust(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET MAGICDUST=? WHERE UUID=?");
            statement.setInt(1, (getDust(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void resetDust(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET MAGICDUST=? WHERE UUID=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet results;

    public Integer getDust(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("MAGICDUST");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void keepAlive() {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEXP(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET EXP=? WHERE UUID=?");
            statement.setInt(1, (getEXP(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void resetEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET EXP=? WHERE UUID=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("EXP");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET EXPMAX=? WHERE UUID=?");
            statement.setInt(1, (getEXPMax(uuid) + 100));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("EXPMAX");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET LEVEL=? WHERE UUID=?");
            statement.setInt(1, (getLevel(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("LEVEL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void completedTutorial(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET TUTORIAL=? WHERE UUID=?");
            statement.setString(1, "Completed");
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getTutorial(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getString("TUTORIAL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverCoins(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQCOINS=? WHERE UUID=?");
            statement.setInt(1, (getKitCoins(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverCoins(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQCOINS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverEXP(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET OITQEXP=? WHERE UUID=?");
            statement.setInt(1, (getQuiverEXP(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setQuiverEXP(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQEXP=? WHERE UUID=?");
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void resetQuiverEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQEXP=? WHERE UUID=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQEXP");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQEXPMAX=? WHERE UUID=?");
            statement.setInt(1, (getQuiverEXPMax(uuid) + 100));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQEXPMAX");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET KITLEVEL=? WHERE UUID=?");
            statement.setInt(1, (getQuiverLevel(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQLEVEL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverKills(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQKILLS=? WHERE UUID=?");
            statement.setInt(1, (getQuiverKills(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverKills(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQKILLS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverBowKills(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQBOWKILLS=? WHERE UUID=?");
            statement.setInt(1, (getQuiverKills(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverBowKills(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQBOWKILLS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverDeaths(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQDEATHS=? WHERE UUID=?");
            statement.setInt(1, (getQuiverDeaths(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverDeaths(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQDEATHS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverWins(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQWINS=? WHERE UUID=?");
            statement.setInt(1, (getQuiverWins(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverWins(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQWINS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuiverPlayed(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.oitq_table + " SET OITQPLAYED=? WHERE UUID=?");
            statement.setInt(1, (getQuiverPlayed(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getQuiverPlayed(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.oitq_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("OITQPLAYED");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateKitCoins(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITCOINS=? WHERE UUID=?");
            statement.setInt(1, (getKitCoins(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getKitCoins(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("KITCOINS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateKitEXP(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITEXP=? WHERE UUID=?");
            statement.setInt(1, (getKitEXP(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setKitEXP(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITEXP=? WHERE UUID=?");
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void resetKitEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITEXP=? WHERE UUID=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getKitEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("KITEXP");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateKitEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITEXPMAX=? WHERE UUID=?");
            statement.setInt(1, (getKitEXPMax(uuid) + 100));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getKitEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("KITEXPMAX");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateKitLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITLEVEL=? WHERE UUID=?");
            statement.setInt(1, (getKitLevel(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getKitLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("KITLEVEL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateKitKills(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITKILLS=? WHERE UUID=?");
            statement.setInt(1, (getKitKills(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getKitKills(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("KITKILLS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateKitDeaths(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET KITDEATHS=? WHERE UUID=?");
            statement.setInt(1, (getKitDeaths(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getKitDeaths(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("KITDEATHS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean getNpcEvents(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getBoolean("NPCEVENTS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateNpcEvents(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET NPCEVENTS=? WHERE UUID=?");
            statement.setBoolean(1, true);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Boolean getBBB(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.kitpvp_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getBoolean("BBB");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateBBB(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.kitpvp_table + " SET BBB=? WHERE UUID=?");
            statement.setBoolean(1, true);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
