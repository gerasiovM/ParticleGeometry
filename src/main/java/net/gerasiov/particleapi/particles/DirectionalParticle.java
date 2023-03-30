package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.particles.types.DirectionalParticleTypes;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.EnumSet;
import java.util.List;

public class DirectionalParticle implements ParticlePoint{
    private Location startLocation;
    private Vector direction;
    private final Particle type;
    private double speed;

    public DirectionalParticle(Location startLocation, Vector direction, Particle type, double speed) {
        if (!DirectionalParticleTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid particle type provided.");
        }

        // Don't know if negative speed provides an error while spawning, so this will be here for now.
//        if (speed <= 0) {
//            throw new IllegalArgumentException("Invalid speed provided. Speed must be >0");
//        }

        this.startLocation = startLocation;
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
        return this.startLocation;
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
        this.startLocation = location;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    @Override
    public void spawn() {
        startLocation.getWorld().spawnParticle(type, startLocation, 0, direction.getX(), direction.getY(), direction.getZ(), speed);
    }
}
