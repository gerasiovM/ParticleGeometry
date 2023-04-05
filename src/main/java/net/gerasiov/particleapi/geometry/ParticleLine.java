package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.particles.*;
import net.gerasiov.particleapi.schemes.line.LineScheme;
import org.bukkit.Location;

public class ParticleLine extends ParticleGroup {
    private Location startLocation;
    private Location endLocation;
    private double interval;
    private ParticlePoint[] particles;

    private LineScheme scheme;


    /**
     * Creates a {@link ParticleLine} object representing a line of {@link ParticlePoint}s between the given start
     * and end locations, with the given interval(adjusted to fit line length) between {@link ParticlePoint}s,
     * and using the given {@link ParticlePoint} object to define the particle effect to be displayed.
     *
     * @param startLocation the starting Location of the line
     * @param endLocation the ending Location of the line
     * @param interval the desired distance between ParticlePoints along the line
     * @param particle the ParticlePoint object defining the particle effect to be displayed
     */
    public ParticleLine(Location startLocation, Location endLocation, double interval, ParticlePoint particle) {
        super(new LineScheme(particle).createArray(startLocation, endLocation, calculateRealInterval(startLocation, endLocation, interval)));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
    }

    /**
     * Constructs a {@link ParticleLine} object representing a line of {@link ParticlePoint}s
     * between the given start and end locations, with the given interval between {@link ParticlePoint}s,
     * and using the provided {@link LineScheme} to define the particle effect to be displayed.
     *
     * @param startLocation the starting {@link Location} of the line
     * @param endLocation the ending {@link Location} of the line
     * @param interval the desired distance between {@link ParticlePoint}s along the line
     * @param scheme the {@link LineScheme} object defining the particle effects to be displayed
     */
    public ParticleLine(Location startLocation, Location endLocation, double interval, LineScheme scheme) {
        super(scheme.createArray(startLocation, endLocation, interval));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        this.scheme = scheme;
    }

    /**
     * Calculates the actual distance between ParticlePoints along the line between the given start and end locations,
     * given a desired interval.
     *
     * @param startLocation the starting Location of the line
     * @param endLocation the ending Location of the line
     * @param interval the desired distance between ParticlePoints along the line
     */
    public static double calculateRealInterval(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);
        return distance / numberOfParticles;
    }

    public Location getStartLocation() {
        return this.startLocation;
    }

    public Location getEndLocation() {
        return this.endLocation;
    }

    public double getInterval() {
        return this.interval;
    }

    public LineScheme getScheme() {
        return this.scheme;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
        this.interval = calculateRealInterval(this.startLocation, this.endLocation, this.interval);
        this.particles = this.scheme.createArray(this.startLocation, this.endLocation, this.interval);
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(this.startLocation, this.endLocation, this.interval);
        this.particles = this.scheme.createArray(this.startLocation, this.endLocation, this.interval);
    }

    public void setInterval(double interval) {
        this.interval = calculateRealInterval(this.startLocation, this.endLocation, interval);
        this.particles = this.scheme.createArray(this.startLocation, this.endLocation, this.interval);
    }
}
