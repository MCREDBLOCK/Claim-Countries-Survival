package com.redblock6.survival.mccore.functions;

import com.redblock6.survival.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class CreateGameMenu implements Listener {
    private static Plugin plugin = Main.getPlugin(Main.class);

    public void newGameMenu(Player p) {
        Inventory i = plugin.getServer().createInventory(null, 6, "Game Menu");
        p.openInventory(i);
	}

    public static ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, byte color, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1, color);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, Color color, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        // Set the name of the item
        meta.setColor(color);
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, Color color, Enchantment ench, int level, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        item.addUnsafeEnchantment(ench, level);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        // Set the name of the item
        meta.setColor(color);
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, Color color) {
        ItemStack item = new ItemStack(material, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        // Set the name of the item
        meta.setColor(color);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, Enchantment ench, int enchLevel,  String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        item.addUnsafeEnchantment(ench, enchLevel);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createGuiItem(ItemStack item, Enchantment ench, int enchLevel,  String name, String... lore) {
        item.addUnsafeEnchantment(ench, enchLevel);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(Material material, Enchantment ench, int enchLevel) {
        ItemStack item = new ItemStack(material, 1);
        item.addUnsafeEnchantment(ench, enchLevel);

        return item;
    }

    public static String translateOther(String msg) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendClickableCommand(Player player, String message, String command) {
        // Make a new component (Bungee API).
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        // Add a click event to the component.
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));

        // Send it!
        player.spigot().sendMessage(component);
    }


    public static String translate(String msg) {
        String newmsg = ChatColor.translateAlternateColorCodes('&', msg);

        return newmsg;
    }
}
