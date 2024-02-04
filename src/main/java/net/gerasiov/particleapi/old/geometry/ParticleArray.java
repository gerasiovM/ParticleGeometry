package net.gerasiov.particleapi.old.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.ParticleSpawnInjector;
import net.gerasiov.particleapi.events.ParticleConstructSpawnEvent;
import net.gerasiov.particleapi.geometry.ParticleConstruct;
import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.old.schemes.SpawnScheme;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ParticleArray implements ParticleConstruct {
    private Location startLocation;
    private Location endLocation;
    private double interval;

    private RegularParticle[] particles;

    private Location[] locations;

    public ParticleArray(@NotNull Location startLocation, @NotNull Location endLocation, double interval) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval must be greater than 0");
        }
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        fillLocationArray();
    }

    /**
     * Calculates the real interval based on 2 locations and the initial interval.
     *
     * @param startLocation The first location.
     * @param endLocation   The second location.
     * @param interval      The initial interval.
     * @return The correct interval
     */
    public static double calculateRealInterval(@NotNull Location startLocation, @NotNull Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);
        return distance / numberOfParticles;
    }

    /**
     * Based on instance's fields creates and fills an array with locations for the particles to be spawned in.
     */
    private void fillLocationArray() {
        Vector directionVector = endLocation.toVector().subtract(startLocation.toVector()).normalize().multiply(interval);
        double distance = endLocation.distance(startLocation);
        int numberOfParticles = (int) Math.ceil(distance / interval);

        locations = new Location[numberOfParticles];
        particles = new RegularParticle[numberOfParticles];

        for (int i = 0; i < numberOfParticles; i++) {
            locations[i] = startLocation.clone().add(directionVector.clone().multiply(i));
        }
    }

    private void updateParticleLocations() {
        for (int i = 0; i < particles.length; i++) {
            particles[i].setLocation(locations[i]);
        }
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
        interval = calculateRealInterval(startLocation, endLocation, interval);
        fillLocationArray();
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
        interval = calculateRealInterval(startLocation, endLocation, interval);
        fillLocationArray();
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        fillLocationArray();
    }

    public Location[] getLocations() {
        return locations;
    }

    public Location getLocation(int i) {
        return locations[i];
    }

    public RegularParticle[] getParticles() {
        return particles;
    }

    public RegularParticle getParticle(int i) {
        return particles[i];
    }

    public void setParticles(RegularParticle[] particles) {
        if (particles.length != locations.length) {
            throw new IllegalArgumentException("RegularParticle[] length must be equal to Location[] length");
        }

        for (int i = 0; i < particles.length; i++) {
            RegularParticle clonedParticle = particles[i].clone();
            clonedParticle.setLocation(locations[i]);
            this.particles[i] = clonedParticle;
        }
    }

    public void setParticle(RegularParticle particle, int i) {
        RegularParticle copyParticle = particle.clone();
        copyParticle.setLocation(locations[i]);
        this.particles[i] = copyParticle;
    }

    public void rotate(double xRotation, double yRotation, double zRotation) {
        Location center = locations.length % 2 == 0 ? locations[locations.length / 2].clone().add(locations[locations.length / 2 + 1].clone().subtract(locations[locations.length / 2].clone()).multiply(0.5)) : locations[locations.length / 2 + 1].clone();
        for (int i = 0; i < locations.length; i++) {
            locations[i] = center.clone().add(locations[i].clone().subtract(center.clone()).toVector().rotateAroundX(xRotation).rotateAroundY(yRotation).rotateAroundZ(zRotation));
        }
        updateParticleLocations();
    }

    public void rotateAroundLocation(Location rotationCenter, double xRotation, double yRotation, double zRotation) {
        for (int i = 0; i < locations.length; i++) {
            locations[i] = rotationCenter.clone().add(locations[i].clone().subtract(rotationCenter.clone()).toVector().rotateAroundX(xRotation).rotateAroundY(yRotation).rotateAroundZ(zRotation));
        }
        updateParticleLocations();
    }

    @Override
    public ParticleArray clone() {
        return new ParticleArray(startLocation.clone(), endLocation.clone(), interval);
    }

    @Override
    public void spawn() {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        for (RegularParticle particle : particles) {
            particle.spawn();
        }
    }

    /**
     * Spawn all particles in the array with a period between each spawn.
     *
     * @param delay       Delay before the first spawn in ticks.
     * @param period      Period between every spawn in ticks.
     * @param spawnScheme A scheme that decides the order in which to spawn particles.
     */
    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[]> spawnScheme) {
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

    /**
     * Spawn all particles in the array with a period between each spawn.
     *
     * @param delay       Delay before the first spawn in ticks.
     * @param period      Period between every spawn in ticks.
     * @param spawnScheme A scheme that decides the order in which to spawn particles.
     * @param injector    An object that allows the user to inject their own code
     */
    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[]> spawnScheme, ParticleSpawnInjector injector) {
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
