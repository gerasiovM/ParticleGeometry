package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import net.gerasiov.particleapi.particles.types.DirectionalParticleTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.function.Predicate;


public class DirectionalParticle implements ParticlePoint{
    private Location location;
    private Vector direction;
    private final Particle type;
    private double speed;


    /**
     * Creates a new {@link DirectionalParticle} object with the specified parameters.
     *
     * @param location The start location of the particle. This is where it will spawn.
     * @param direction The direction in which the particle will travel.
     * @param type The particle type. Should be one of the {@link DirectionalParticleTypes} values.
     * @param speed The particle speed.
     */
    public DirectionalParticle(Particle type, Location location, Vector direction, double speed) {
        if (!DirectionalParticleTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid particle type provided.");
        }

        this.location = location;
        this.direction = direction;
        this.type = type;
        this.speed = speed;
    }

    @Override
    public Particle getType() {
        return this.type;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    public Vector getDirection() {
        return this.direction;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    @Override
    public DirectionalParticle clone() {
        return new DirectionalParticle(this.type, this.location, this.direction, this.speed);
    }

    @Override
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
        return this.location.getWorld().getNearbyEntities(this.location, radiusX, radiusY, radiusZ);
    }

    @Override
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter) {
        return this.location.getWorld().getNearbyEntities(this.location, radiusX, radiusY, radiusZ, filter);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(particleSpawnEvent);

        if (!particleSpawnEvent.isCancelled()) {
            location.getWorld().spawnParticle(type, location, 0, direction.getX(), direction.getY(), direction.getZ(), speed);
        }
    }
}
