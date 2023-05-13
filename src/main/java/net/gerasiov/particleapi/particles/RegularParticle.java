package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

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

    public void setLocation(Location location) {
        this.location = location;
    }

    public RegularParticle clone() {
        return new RegularParticle(this.type, this.location);
    }

    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
        return this.location.getWorld().getNearbyEntities(this.location, radiusX, radiusY, radiusZ);
    }

    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter) {
        return this.location.getWorld().getNearbyEntities(this.location, radiusX, radiusY, radiusZ, filter);
    }

    public void spawn() {
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(particleSpawnEvent);

        if (!particleSpawnEvent.isCancelled()) {
            location.getWorld().spawnParticle(type, location, 1);
        }
    }
}
