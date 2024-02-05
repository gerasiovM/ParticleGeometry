package com.gerasiov.particlegeometry.particles;

import com.gerasiov.particlegeometry.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;

public class DustParticle extends RegularParticle {
    private Color color;
    private Color secondaryColor;
    private float size;

    /**
     * Creates a new {@link DustParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param color    The color for the particle.
     * @param size     The size for the particle.
     */
    public DustParticle(Location location, Color color, float size) {
        super(Particle.REDSTONE, location);
        this.color = color;
        this.size = size;
    }

    /**
     * Creates a new {@link DustParticle} object with the specified parameters.
     *
     * @param location        The location for the particle.
     * @param color           The color for the particle.
     * @param secondaryColor  The secondary color for the particle, towards which the main color will transition.
     * @param size            The size for the particle.
     */
    public DustParticle(Location location, Color color, Color secondaryColor, float size) {
        super(Particle.DUST_COLOR_TRANSITION, location);
        this.color = color;
        this.secondaryColor = secondaryColor;
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public float getSize() {
        return size;
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
        if (getType() == Particle.REDSTONE) {
            return new DustParticle(getLocation().clone(), color, size);
        }
        return new DustParticle(getLocation(), color, secondaryColor, size);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            if (getType() == Particle.REDSTONE) {
                DustOptions dustOptions = new DustOptions(color, size);
                getLocation().getWorld().spawnParticle(getType(), getLocation(), 1, dustOptions);
            } else {
                DustTransition dustTransition = new DustTransition(color, secondaryColor, size);
                getLocation().getWorld().spawnParticle(getType(), getLocation(), 1, dustTransition);
            }
        }
    }
}
