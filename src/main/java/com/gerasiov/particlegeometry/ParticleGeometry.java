package com.gerasiov.particlegeometry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ParticleGeometry extends JavaPlugin {
    public static Plugin instance;
    public Logger PluginLogger = Bukkit.getLogger();

    @Override
    public void onEnable() {
        PluginLogger.info("Enabled");
        instance = this;
    }

    @Override
    public void onDisable() {
        PluginLogger.info("Disabled");
    }

}
