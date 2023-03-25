package net.gerasiov.particleapi;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ParticleAPI extends JavaPlugin {
    public Logger PluginLogger = Bukkit.getLogger();

    @Override
    public void onEnable() {
        PluginLogger.info("Enabled");
        World world = Bukkit.getWorld("overworld");

    }

    @Override
    public void onDisable() {
        PluginLogger.info("Disabled");
    }
}
