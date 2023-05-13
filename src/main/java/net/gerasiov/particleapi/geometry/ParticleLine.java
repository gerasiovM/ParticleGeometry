package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.SpawnScheme;
import net.gerasiov.particleapi.schemes.array.line.ArrayLineScheme;
import net.gerasiov.particleapi.schemes.spawn.line.SpawnLineScheme;
import org.bukkit.Location;

public class ParticleLine extends ParticleGroup {
    private Location startLocation;
    private Location endLocation;
    private double interval;
    private ArrayLineScheme arrayScheme;

    /**
     * Creates a {@link ParticleLine} object representing a line of {@link RegularParticle}s between the given start
     * and end locations, with the given interval (adjusted to fit the line length) between {@link RegularParticle}s,
     * and using the given {@link RegularParticle} object to define the particle effect to be displayed.
     *
     * @param startLocation the starting {@link Location} of the line
     * @param endLocation the ending {@link Location} of the line
     * @param interval the desired distance between {@link RegularParticle}s along the line
     * @param particle the {@link RegularParticle} object defining the particle effect to be displayed.
     */
    public ParticleLine(Location startLocation, Location endLocation, double interval, RegularParticle particle) {
        super(new ArrayLineScheme(particle).createArray(startLocation, endLocation, calculateRealInterval(startLocation, endLocation, interval)));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
    }

    /**
     * Constructs a {@link ParticleLine} object representing a line of {@link RegularParticle}s
     * between the given start and end locations, with the given interval between {@link RegularParticle}s,
     * and using the provided {@link ArrayLineScheme} to define the particle effect to be displayed.
     *
     * @param startLocation the starting {@link Location} of the line
     * @param endLocation the ending {@link Location} of the line
     * @param interval the desired distance between {@link RegularParticle}s along the line
     * @param arrayScheme the {@link ArrayLineScheme} object, defining the particle effects to be displayed
     * @param spawnScheme the {@link SpawnLineScheme} object, defining the order in which the particles will be spawned
     */
    public ParticleLine(Location startLocation, Location endLocation, double interval, ArrayLineScheme arrayScheme, SpawnScheme spawnScheme) {
        super(arrayScheme.createArray(startLocation, endLocation, interval), spawnScheme);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        this.arrayScheme = arrayScheme;
    }

    /**
     * Calculates the actual distance between {@link RegularParticle}s along the line between the given start and end locations,
     * given a desired interval.
     *
     * @param startLocation the starting {@link Location} of the line
     * @param endLocation the ending {@link Location} of the line
     * @param interval the desired distance between {@link RegularParticle}s along the line.
     * @return the actual distance.
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

    public ArrayLineScheme getScheme() {
        return this.arrayScheme;
    }

    public int getLength() {
        return getParticles().length;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
        this.interval = calculateRealInterval(this.startLocation, this.endLocation, this.interval);
        setParticles(this.arrayScheme.createArray(this.startLocation, this.endLocation, this.interval));
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(this.startLocation, this.endLocation, this.interval);
        setParticles(this.arrayScheme.createArray(this.startLocation, this.endLocation, this.interval));
    }

    public void setInterval(double interval) {
        this.interval = calculateRealInterval(this.startLocation, this.endLocation, interval);
        setParticles(this.arrayScheme.createArray(this.startLocation, this.endLocation, this.interval));
    }
}
