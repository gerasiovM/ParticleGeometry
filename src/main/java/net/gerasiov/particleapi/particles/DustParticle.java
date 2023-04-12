package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.function.Predicate;

public class DustParticle implements ParticlePoint{
    private Location location;
    private Color color;
    private Color secondaryColor = null;
    private float size;


    /**
     * Creates a new {@link DustParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param color The color for the particle.
     * @param size The size for the particle.
     */
    public DustParticle(Location location, Color color, float size) {
        this.location = location;
        this.color = color;
        this.size = size;
    }

    /**
     * Creates a new {@link DustParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param color The color for the particle.
     * @param secondaryColor The secondary color for the particle, towards which the main color will transition.
     * @param size The size for the particle.
     */
    public DustParticle(Location location, Color color, Color secondaryColor, float size) {
        this.location = location;
        this.color = color;
        this.secondaryColor = secondaryColor;
        this.size = size;
    }


    @Override
    public Particle getType() {
        if (secondaryColor == null) {
            return Particle.REDSTONE;
        } else {
            return Particle.DUST_COLOR_TRANSITION;
        }
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

    public float getSize() {
        return this.size;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public DustParticle clone() {
        return new DustParticle(this.location, this.color, this.secondaryColor, this.size);
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
            if (secondaryColor == null) {
                DustOptions dustOptions = new DustOptions(color, size);
                location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
            } else {
                DustTransition dustTransition = new DustTransition(color, secondaryColor, size);
                location.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 1, dustTransition);
            }
        }
    }
}
