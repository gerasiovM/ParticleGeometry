package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.ParticleSpawnInjector;
import net.gerasiov.particleapi.events.ParticleConstructSpawnEvent;
import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.SpawnScheme;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ParticleMatrix implements ParticleConstruct {
    private RegularParticle[][] particles;
    private Location[][] locations;
    private Location center;
    private double verticalInterval;
    private double horizontalInterval;

    /**
     * Creates a ParticleMatrix which will be represented with an upright rectangle.
     * The 2 locations provided should be the opposite corners of the rectangle.
     *
     * @param firstCorner        The first corner of the rectangle.
     * @param oppositeCorner     The opposite to the first corner of the rectangle.
     * @param verticalInterval   The initial interval between all particle locations. (Vertical in relation to matrix columns)
     * @param horizontalInterval The initial horizontal interval between particles. (Horizontal in relation to matrix rows)
     */
    public ParticleMatrix(@NotNull Location firstCorner, @NotNull Location oppositeCorner, double verticalInterval, double horizontalInterval) {
        if (verticalInterval <= 0 || horizontalInterval <= 0) {
            throw new IllegalArgumentException("Intervals must be greater than 0");
        }
        fillLocationMatrix(new Location(oppositeCorner.getWorld(), oppositeCorner.getX(), firstCorner.getY(), oppositeCorner.getZ()), firstCorner, oppositeCorner, verticalInterval, horizontalInterval);
    }

    /**
     * Creates a ParticleMatrix which will be represented by a rectangle.
     * The 3 locations provided should form a right angle. Order doesn't matter.
     *
     * @param firstCorner        The first corner of the rectangle.
     * @param secondCorner       The second corner of the rectangle.
     * @param thirdCorner        The third corner of the rectangle.
     * @param verticalInterval   The initial interval between all particle locations. (Vertical in relation to matrix columns)
     * @param horizontalInterval The initial horizontal interval between particles. (Horizontal in relation to matrix rows)
     */
    public ParticleMatrix(@NotNull Location firstCorner, @NotNull Location secondCorner, @NotNull Location thirdCorner, double verticalInterval, double horizontalInterval) {
        if (verticalInterval <= 0 || horizontalInterval <= 0) {
            throw new IllegalArgumentException("Interval must be greater than 0");
        }
        double distance12, distance13, distance23;
        distance12 = firstCorner.distance(secondCorner);
        distance13 = firstCorner.distance(thirdCorner);
        distance23 = secondCorner.distance(thirdCorner);
        if (doubleEquals(distance12 * distance12 + distance13 * distance13, distance23 * distance23)) {
            fillLocationMatrix(firstCorner, secondCorner, thirdCorner, verticalInterval, horizontalInterval);
        } else if (doubleEquals(distance12 * distance12 + distance23 * distance23, distance13 * distance13)) {
            fillLocationMatrix(secondCorner, firstCorner, thirdCorner, verticalInterval, horizontalInterval);
        } else if (doubleEquals(distance13 * distance13 + distance23 * distance23, distance12 * distance12)){
            fillLocationMatrix(thirdCorner, firstCorner, secondCorner, verticalInterval, horizontalInterval);
        } else {
            throw new IllegalArgumentException("Locations don't form a rectangle");
        }
    }

    private ParticleMatrix(RegularParticle[][] particles, Location[][] locations, Location center, double verticalInterval, double horizontalInterval) {
        this.particles = particles;
        this.locations = locations;
        this.center = center;
        this.verticalInterval = verticalInterval;
        this.horizontalInterval = horizontalInterval;
    }

    /**
     * This function is used to compare 2 doubles for equality.
     */
    private boolean doubleEquals(double a, double b) {
        double epsilon = 1E-6;
        return Math.abs(a - b) < epsilon;
    }

    /**
     * Calculates the real interval based on 2 locations and the initial interval.
     *
     * @param startLocation The first location.
     * @param endLocation   The second location.
     * @param interval      The initial interval.
     * @return              The correct interval
     */
    public static double calculateRealInterval(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);
        return distance / numberOfParticles;
    }

    private void fillLocationMatrix(Location l1, Location l2, Location l3, double verticalInterval, double horizontalInterval) {
        center = l1.clone();
        // Vertical
        verticalInterval = calculateRealInterval(l1, l2, verticalInterval);
        this.verticalInterval = verticalInterval;
        Vector verticalDirectionVector = l2.clone().toVector().subtract(l1.toVector()).normalize().multiply(verticalInterval);
        double distance = l2.distance(l1);
        int verticalNumberOfParticles = (int) Math.ceil(distance / verticalInterval);
        center.add(verticalDirectionVector.clone().multiply(((double) verticalNumberOfParticles) / 2));

        // Horizontal
        horizontalInterval = calculateRealInterval(l1, l3, horizontalInterval);
        this.horizontalInterval = horizontalInterval;
        Vector horizontalDirectionVector = l3.clone().toVector().subtract(l1.toVector()).normalize().multiply(horizontalInterval);
        distance = l3.distance(l1);
        int horizontalNumberOfParticles = (int) Math.ceil(distance / horizontalInterval);
        center.add(horizontalDirectionVector.clone().multiply(((double) horizontalNumberOfParticles) / 2));

        locations = new Location[horizontalNumberOfParticles][verticalNumberOfParticles];
        for (int i = 0; i < horizontalNumberOfParticles; i++) {
            for (int j = 0; j < verticalNumberOfParticles; j++) {
                locations[i][j] = l1.clone().add(horizontalDirectionVector.clone().multiply(i)).add(verticalDirectionVector.clone().multiply(j));
            }
        }

        particles = new RegularParticle[horizontalNumberOfParticles][verticalNumberOfParticles];
    }

    public Location getCenter() {
        return center;
    }

    public RegularParticle[][] getParticles() {
        return particles;
    }

    public RegularParticle getParticle(int row, int column) {
        return particles[row][column];
    }

    public void setParticle(RegularParticle particle, int row, int column) {
        RegularParticle newParticle = particle.clone();
        newParticle.setLocation(locations[row][column]);
        particles[row][column] = newParticle;
    }

    public void rotate(double xRotation, double yRotation, double zRotation) {
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                locations[i][j] = center.clone().add(locations[i][j].clone().subtract(center.clone()).toVector().rotateAroundX(xRotation).rotateAroundY(yRotation).rotateAroundZ(zRotation));
            }
        }
        updateParticleLocations();
    }

    private void updateParticleLocations() {
        for (int i = 0; i < particles.length; i++) {
            for (int j = 0; j < particles[0].length; j++) {
                particles[i][j].setLocation(locations[i][j]);
            }
        }
    }

    @Override
    public ParticleMatrix clone() {
        return new ParticleMatrix(particles.clone(), locations.clone(), center.clone(), verticalInterval, horizontalInterval);
    }

    @Override
    public void spawn() {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        for (int i = 0; i < particles.length; i++) {
            for (int j = 0; j < particles[0].length; j++) {
                if (particles[i][j] != null) {
                    particles[i][j].spawn();
                }
            }
        }
    }

    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[][]> spawnScheme) {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (event.isCancelled()) {
                    cancel();
                    return;
                }
                for (RegularParticle particle : spawnScheme.getNextParticles(index, particles)) {
                    particle.spawn();
                }
                index++;
                if (spawnScheme.isFinished(index, particles)) {
                    cancel();
                }
            }
        }.runTaskTimer(ParticleAPI.instance, delay, period);
    }

    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[][]> spawnScheme, ParticleSpawnInjector injector) {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (event.isCancelled()) {
                    cancel();
                    return;
                }
                injector.reply(index);
                for (RegularParticle particle : spawnScheme.getNextParticles(index, particles)) {
                    particle.spawn();
                }
                index++;
                if (spawnScheme.isFinished(index, particles)) {
                    cancel();
                }
            }
        }.runTaskTimer(ParticleAPI.instance, delay, period);
    }
}
