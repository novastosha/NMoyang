package dev.nova.nmoyang.bukkit;

import dev.nova.nmoyang.api.Mojang;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * This class was purely created for Bukkit
 *
 * As bukkit requires the jar to be a plugin.
 *
 */
public class MainBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§7[§cNMoyangAPI§7] The API's Bukkit plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§7[§cNMoyangAPI§7] The API's Bukkit plugin has been disabled!");
    }
}
