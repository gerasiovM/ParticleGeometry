package com.gerasiov.particlegeometry.particles;

import com.gerasiov.particlegeometry.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class SpellMobParticle extends RegularParticle {
    private double red;
    private double green;
    private double blue;
    // I don't know what this value is, maybe alpha? Until then, it will be called extra. extra=1D should produce normal results.
    private double extra;

	/**
     * Creates a new {@link SpellMobParticle} object with the specified parameters.
     *
     * @param type     Particle type. Can be either {@link Particle#SPELL_MOB} or {@link Particle#SPELL_MOB_AMBIENT}.
     * @param location The location for the particle.
     * @param color    The color for the particle.
     * @param extra    The extra value for the particle. Presumably alpha, 1D should produce normal results.
     */
    public SpellMobParticle(Particle type, Location location, Color color, double extra) {
        super(type, location);
        if (!(type == Particle.SPELL_MOB || type == Particle.SPELL_MOB_AMBIENT)) {
            throw new IllegalArgumentException("Invalid particle type provided");
        }
        this.red = color.getRed() / 255D;
        this.green = color.getGreen() / 255D;
        this.blue = color.getBlue() / 255D;
        this.extra = extra;
    }

    /**
     * Creates a new {@link SpellMobParticle} object with the specified parameters.
     *
     * @param type     Particle type. Can be either {@link Particle#SPELL_MOB} or {@link Particle#SPELL_MOB_AMBIENT}.
     * @param location The location for the particle.
     * @param red      The red component of the color. The value must be an integer between 0 and 255.
     * @param green    The green component of the color. The value must be an integer between 0 and 255.
     * @param blue     The blue component of the color. The value must be an integer between 0 and 255.
     * @param extra    The extra value for the particle. Presumably alpha, 1D should produce normal results.
     */
    public SpellMobParticle(Particle type, Location location, int red, int green, int blue, double extra) {
        super(type, location);
        if (!(type == Particle.SPELL_MOB || type == Particle.SPELL_MOB_AMBIENT)) {
            throw new IllegalArgumentException("Invalid particle type provided");
        }
        this.red = red / 255D;
        this.green = green / 255D;
        this.blue = blue / 255D;
        this.extra = extra;
    }

    /**
     * Creates a new {@link SpellMobParticle} object with the specified parameters.
     *
     * @param type Particle type. Can be either {@link Particle#SPELL_MOB} or {@link Particle#SPELL_MOB_AMBIENT}.
     * @param location The location for the particle.
     * @param red The red component of the color. The value must be a double between 0 and 1, also divisible by 1/255.;
     * @param green The green component of the color. The value must be a double between 0 and 1, also divisible by 1/255.;
     * @param blue The blue component of the colo. The value must be a double between 0 and 1, also divisible by 1/255.;
     * @param extra The extra value for the particle. Presumably alpha, 1D should produce normal results.
     */
    public SpellMobParticle(Particle type, Location location, double red, double green, double blue, double extra) {
        super(type, location);
        if (!(type == Particle.SPELL_MOB || type == Particle.SPELL_MOB_AMBIENT)) {
            throw new IllegalArgumentException("Invalid particle type provided");
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.extra = extra;
    }

    public int getRed() {
        return (int) (this.red * 255);
    }

    public int getGreen() {
        return (int) (this.green * 255);
    }

    public int getBlue() {
        return (int) (this.blue * 255);
    }

    public double getExtra() {
        return this.extra;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

    public void setColor(Color color) {
        this.red = color.getRed() / 255D;
        this.green = color.getGreen() / 255D;
        this.blue = color.getBlue() / 255D;
    }

    @Override
    public SpellMobParticle clone() {
        return new SpellMobParticle(getType(), getLocation().clone(), this.red, this.green, this.blue, this.extra);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 0, red, green, blue, extra);
        }
    }
}
