package net.gerasiov.particleapi.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;

import java.util.Collection;
import java.util.function.Predicate;

public class RegularParticle {
    private final Particle type;
    private Location location;

    public RegularParticle(Particle type, Location location) {
        this.type = type;
        this.location = location;
    }

    public Particle getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public RegularParticle setLocation(Location location) {
        this.location = location;
        return this;
    }

    public RegularParticle clone() {
        return new RegularParticle(type, location.clone());
    }

    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
        return location.getWorld().getNearbyEntities(location, radiusX, radiusY, radiusZ);
    }

    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter) {
        return location.getWorld().getNearbyEntities(location, radiusX, radiusY, radiusZ, filter);
    }

    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            location.getWorld().spawnParticle(type, location, 1);
        }
    }
}
