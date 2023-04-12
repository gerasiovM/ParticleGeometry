package net.gerasiov.particleapi.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.function.Predicate;

public interface ParticlePoint {
    Particle getType();

    Location getLocation();

    void setLocation(Location location);

    ParticlePoint clone();

    Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ);

    Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter);

    void spawn();

}
