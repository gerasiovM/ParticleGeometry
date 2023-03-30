package net.gerasiov.particleapi.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public interface ParticlePoint {
    Particle getType();

    Location getLocation();

    void setLocation(Location location);

    void spawn();

}
