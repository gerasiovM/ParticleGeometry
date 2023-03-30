package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.particles.types.DirectionalParticleTypes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;


public class DirectionalParticle implements ParticlePoint{
    private Location startLocation;
    private Vector direction;
    private final Particle type;
    private double speed;


    /**
     * Creates a new {@link DirectionalParticle} object with the specified parameters.
     *
     * @param startLocation The start location of the particle. This is where it will spawn.
     * @param direction The direction in which the particle will travel.
     * @param type The particle type. Should be one of the {@link DirectionalParticleTypes} values.
     * @param speed The particle speed.
     */
    public DirectionalParticle(Location startLocation, Vector direction, Particle type, double speed) {
        if (!DirectionalParticleTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid particle type provided.");
        }

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
