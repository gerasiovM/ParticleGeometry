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



    public ParticleLine(Location startLocation, Location endLocation, double interval, ParticlePoint particle) {
        super(new LineScheme(particle).createArray(startLocation, endLocation, calculateRealInterval(startLocation, endLocation, interval)));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
    }

    public ParticleLine(Location startLocation, Location endLocation, double interval, LineScheme scheme) {
        super(scheme.createArray(startLocation, endLocation, interval));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        this.scheme = scheme;
    }

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
