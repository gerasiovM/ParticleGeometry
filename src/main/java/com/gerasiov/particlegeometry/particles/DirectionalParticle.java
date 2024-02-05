package com.gerasiov.particlegeometry.particles;

import com.gerasiov.particlegeometry.events.ParticleSpawnEvent;
import com.gerasiov.particlegeometry.particles.types.DirectionalParticleTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class DirectionalParticle extends RegularParticle {

    private Vector direction;
    private double speed;

    /**
     * Creates a new {@link DirectionalParticle} object with the specified parameters.
     *
     * @param location   The start location of the particle. This is where it will spawn.
     * @param direction  The direction in which the particle will travel.
     * @param type       The particle type. Should be one of the {@link DirectionalParticleTypes} values.
     * @param speed      The particle speed.
     * @throws IllegalArgumentException If an invalid particle type is provided.
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
    public DirectionalParticle clone() {
        return new DirectionalParticle(getType(), getLocation().clone(), direction.clone(), speed);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 0, direction.getX(), direction.getY(), direction.getZ(), speed);
        }
    }
}
