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


public class DirectionalParticle extends RegularParticle{
    private Vector direction;
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
        super(type, location);
        if (!DirectionalParticleTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid particle type provided.");
        }
        this.direction = direction;
        this.speed = speed;
    }

    public Vector getDirection() {
        return this.direction;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(particleSpawnEvent);

        if (!particleSpawnEvent.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 0, direction.getX(), direction.getY(), direction.getZ(), speed);
        }
    }
}
