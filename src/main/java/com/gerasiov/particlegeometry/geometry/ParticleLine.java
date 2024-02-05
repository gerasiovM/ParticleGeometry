package com.gerasiov.particlegeometry.geometry;

import com.gerasiov.particlegeometry.events.ParticleConstructSpawnEvent;
import com.gerasiov.particlegeometry.particles.RegularParticle;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import static com.gerasiov.particlegeometry.Util.calculateRealInterval;

public class ParticleLine implements ParticleConstruct {
    final private Location startLocation;
    final private Location endLocation;
    final private double interval;

    private RegularParticle[] particles;

    private Location[] locations;

    private Location center;

    /**
     * ParticleLine constructor.
     * @param startLocation  The first {@link Location} in the {@link #locations} array
     * @param endLocation    The last {@link Location} in the {@link #locations} array
     * @param interval       The approximate interval between 2 {@link RegularParticle}s.
     *                       The real interval will be calculated to fit the start and end locations.
     *
     * @throws IllegalArgumentException if the interval is negative.
     */
    public ParticleLine(Location startLocation, Location endLocation, double interval) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval must be bigger than 0");
        }
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        fillLocationArray();
    }

    public ParticleLine(Location startLocation, Location endLocation, int numOfPoints) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        if (numOfPoints < 2) {
            throw new IllegalArgumentException("Number of points must be at least 2");
        }
        this.interval = calculateRealInterval(startLocation, endLocation, startLocation.distance(endLocation) / numOfPoints);
        fillLocationArray();
    }

    /**
     * Based on instance's fields creates and fills an array with locations for the particles to be spawned in
     * as well as setting the center location property.
     */
    private void fillLocationArray() {
        Vector directionVector = endLocation.toVector().subtract(startLocation.toVector()).normalize().multiply(interval);
        double distance = endLocation.distance(startLocation);
        int numberOfParticles = (int) Math.ceil(distance / interval);
        updateCenterLocation();

        locations = new Location[numberOfParticles];
        particles = new RegularParticle[numberOfParticles];

        locations[0] = startLocation;
        for (int i = 1; i < numberOfParticles; i++) {
            locations[i] = startLocation.clone().add(directionVector.clone().multiply(i));
        }
        // Make it so the last particle is always at the end location.
        locations[locations.length - 1] = endLocation;
    }

    /**
     * Returns a copy of the {@link Location} object at the specified index.
     *
     * @param index The index of the {@link Location} object to retrieve.
     * @return a copy of the {@link Location} object at the specified index.
     *
     * @throws IllegalArgumentException if the index is negative or greater than or equal to the length
     *                                  of the {@link #locations} array.
     */
    public Location getLocation(int index) {
        if (index < 0 || index >= locations.length) {
            throw new IllegalArgumentException("Input index must be greater than 0 and less than length of the instance's locations array");
        }
        return locations[index].clone();
    }

    /**
     * Returns a copy of the {@link RegularParticle} object at the specified index.
     *
     * @param index The index of the {@link RegularParticle} object to retrieve.
     * @return a copy of the {@link RegularParticle} object at the specified index.
     *
     * @throws IllegalArgumentException if the index is negative or greater than or equal to the length
     *                                  of the {@link #particles} array.
     */
    public RegularParticle getParticle(int index) {
        if (index < 0 || index >= particles.length) {
            throw new IllegalArgumentException("Input index must be greater than 0 and less than length of the instance's particles array");
        }
        return particles[index].clone();
    }

    /**
     * @return cloned center {@link Location}.
     */
    public Location getCenterLocation() {
        return center.clone();
    }

    public double getInterval() {
        return interval;
    }

    /**
     * Clones and puts the particles into the {@link #particles} array after updating their locations;
     * @param particles The particles to be put into the array.
     * @throws IllegalArgumentException if the index is negative or greater than or equal to the length
     *      *                           of the {@link #particles} array.
     */
    public void updateParticles(RegularParticle[] particles) {
        if (particles.length != this.particles.length) {
            throw new IllegalArgumentException("Input particles array length must be equal to instances particle array length");
        }
        for (int i = 0; i < particles.length; i++) {
            this.particles[i] = particles[i].clone().setLocation(locations[i]);
        }
    }

    /**
     * Clones and puts the particle multiple times into the {@link #particles} array
     * while updating its location for every copy.
     * @param particle The particle to be put into the array.
     */
    public void updateParticles(RegularParticle particle) {
        for (int i = 0; i < particles.length; i++) {
            this.particles[i] = particle.clone().setLocation(locations[i]);
        }
    }

    /**
     * Clones and puts the particle into the {@link #particles} array at the specified index after updating its location.
     * @param particle The particle to be put into the array.
     * @param index    The index at which to put the particle into the array.
     */
    public void updateParticle(RegularParticle particle, int index) {
        if (index < 0 || index >= particles.length) {
            throw new IllegalArgumentException("Index must be greater than 0 and less than instance's particle array's length");
        }
        this.particles[index] = particle.clone().setLocation(locations[index]);
    }

    /**
     * Updates the locations of particles in the {@link #particles} array.
     */
    public void updateParticleLocations() {
        for (int i = 0; i < particles.length; i++) {
            particles[i].setLocation(locations[i]);
        }
        updateCenterLocation();
    }

    private void updateCenterLocation() {
        center = startLocation.toVector().add(endLocation.toVector()).multiply(0.5).toLocation(startLocation.getWorld());
    }

    /**
     * Moves the {@link ParticleLine} by adding a specified {@link Vector}.
     * @param vector Input vector that will be added to particle locations.
     */
    public void move(Vector vector) {
        for (Location location : locations) {
            location.add(vector);
        }
        updateParticleLocations();
    }

    /**
     * Moves the {@link ParticleLine} by adding a {@link Vector} that is calculated from the specified {@link Location}
     * @param newLocation Input location that the center will be moved to.
     */
    public void move(Location newLocation) {
        Vector vector = newLocation.toVector().subtract(center.toVector());
        move(vector);
    }

    /**
     * Rotates the {@link ParticleLine} using matrix multiplication around the {@link Location}.
     * @param xRotation      The rotation around the x-axis. In radians.
     * @param yRotation      The rotation around the y-axis. In radians.
     * @param zRotation      The rotation around the z-axis. In radians.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotate(double xRotation, double yRotation, double zRotation, Location rotationCenter) {
        double[][] xRotationMatrix = {
                {1, 0, 0},
                {0, Math.cos(xRotation), -Math.sin(xRotation)},
                {0, Math.sin(xRotation), Math.cos(xRotation)}
        };
        double[][] yRotationMatrix = {
                {Math.cos(yRotation), 0, Math.sin(yRotation)},
                {0, 1, 0},
                {-Math.sin(yRotation), 0, Math.cos(yRotation)}
        };
        double[][] zRotationMatrix = {
                {Math.cos(zRotation), -Math.sin(zRotation), 0},
                {Math.sin(zRotation), Math.cos(zRotation), 0},
                {0, 0, 1}
        };
        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i].clone().subtract(rotationCenter.clone());
            locations[i] = new Location(
                    location.getWorld(),
                    location.getX() * xRotationMatrix[0][0] + location.getY() * xRotationMatrix[0][1] + location.getZ() * xRotationMatrix[0][2],
                    location.getX() * xRotationMatrix[1][0] + location.getY() * xRotationMatrix[1][1] + location.getZ() * xRotationMatrix[1][2],
                    location.getX() * xRotationMatrix[2][0] + location.getY() * xRotationMatrix[2][1] + location.getZ() * xRotationMatrix[2][2]);
            location = locations[i];
            locations[i] = new Location(
                    location.getWorld(),
                    location.getX()* yRotationMatrix[0][0] + location.getY() * yRotationMatrix[0][1] + location.getZ() * yRotationMatrix[0][2],
                    location.getX() * yRotationMatrix[1][0] + location.getY() * yRotationMatrix[1][1] + location.getZ() * yRotationMatrix[1][2],
                    location.getX() * yRotationMatrix[2][0] + location.getY() * yRotationMatrix[2][1] + location.getZ() * yRotationMatrix[2][2]);
            location = locations[i];
            locations[i] = rotationCenter.clone().add(new Location(
                    location.getWorld(),
                    location.getX() * zRotationMatrix[0][0] + location.getY() * zRotationMatrix[0][1] + location.getZ() * zRotationMatrix[0][2],
                    location.getX() * zRotationMatrix[1][0] + location.getY() * zRotationMatrix[1][1] + location.getZ() * zRotationMatrix[1][2],
                    location.getX() * zRotationMatrix[2][0] + location.getY() * zRotationMatrix[2][1] + location.getZ() * zRotationMatrix[2][2]));
            updateParticleLocations();
        }
    }

    /**
     * Rotates the {@link ParticleLine} using matrix multiplication around the center of the line.
     * @param xRotation      The rotation around the x-axis. In radians.
     * @param yRotation      The rotation around the y-axis. In radians.
     * @param zRotation      The rotation around the z-axis. In radians.
     */
    public void rotate(double xRotation, double yRotation, double zRotation) {
        rotate(xRotation, yRotation, zRotation, center);
    }

    /**
     * Rotates the {@link ParticleLine} using matrix multiplication around the specified {@link Axis} and {@link Location}.
     * @param rotation       The rotation around the axis. In radians.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     * @param axis           The {@link Axis} enum used to determine around which axis to rotate.
     */
    public void rotateAroundAxis(double rotation, Location rotationCenter, Axis axis) {
        double[][] rotationMatrix = switch (axis.name()) {
            case "X" -> new double[][]{
                    {1, 0, 0},
                    {0, Math.cos(rotation), -Math.sin(rotation)},
                    {0, Math.sin(rotation), Math.cos(rotation)}
            };
            case "Y" -> new double[][]{
                    {Math.cos(rotation), 0, Math.sin(rotation)},
                    {0, 1, 0},
                    {-Math.sin(rotation), 0, Math.cos(rotation)}
            };
            case "Z" -> new double[][]{
                    {Math.cos(rotation), -Math.sin(rotation), 0},
                    {Math.sin(rotation), Math.cos(rotation), 0},
                    {0, 0, 1}
            };
            default -> throw new IllegalArgumentException("Invalid axis");
        };
        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i].clone().subtract(rotationCenter.clone());
            locations[i] = rotationCenter.clone().add(new Location(
                    location.getWorld(),
                    location.getX() * rotationMatrix[0][0] + location.getY() * rotationMatrix[0][1] + location.getZ() * rotationMatrix[0][2],
                    location.getX() * rotationMatrix[1][0] + location.getY() * rotationMatrix[1][1] + location.getZ() * rotationMatrix[1][2],
                    location.getX() * rotationMatrix[2][0] + location.getY() * rotationMatrix[2][1] + location.getZ() * rotationMatrix[2][2]));
            updateParticleLocations();
        }
    }

    /**
     * Rotates the {@link ParticleLine} using matrix multiplication around the center of the line and the specified {@link Axis}.
     * @param rotation The rotation around the axis. In radians.
     * @param axis     The {@link Axis} enum used to determine around which axis to rotate.
     */
    public void rotateAroundAxis(double rotation, Axis axis) {
        rotateAroundAxis(rotation, center, axis);
    }

    /**
     * Rotates the {@link ParticleLine} around the x-axis and the {@link Location} using matrix multiplication.
     * @param rotation       The rotation around the x-axis.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotateAroundX(double rotation, Location rotationCenter) {
        rotateAroundAxis(rotation, rotationCenter, Axis.X);
    }

    /**
     * Rotates the {@link ParticleLine} around the x-axis and the center of the line using matrix multiplication.
     * @param rotation The rotation around the x-axis.
     */
    public void rotateAroundX(double rotation) {
        rotateAroundX(rotation, center);
    }

    /**
     * Rotates the {@link ParticleLine} around the y-axis and the {@link Location} using matrix multiplication.
     * @param rotation       The rotation around the y-axis.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotateAroundY(double rotation, Location rotationCenter) {
        rotateAroundAxis(rotation, rotationCenter, Axis.Y);
    }

    /**
     * Rotates the {@link ParticleLine} around the y-axis and the center of the line using matrix multiplication.
     * @param rotation The rotation around the y-axis.
     */
    public void rotateAroundY(double rotation) {
        rotateAroundX(rotation, center);
    }

    /**
     * Rotates the {@link ParticleLine} around the z-axis and the rotationCenter using matrix multiplication.
     * @param rotation       The rotation around the z-axis.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotateAroundZ(double rotation, Location rotationCenter) {
        rotateAroundAxis(rotation, rotationCenter, Axis.Z);
    }

    /**
     * Rotates the {@link ParticleLine} around the z-axis and the center of the line using matrix multiplication.
     * @param rotation The rotation around the z-axis.
     */
    public void rotateAroundZ(double rotation) {
        rotateAroundX(rotation, center);
    }

    /**
     *  Finds all entities in a specified radius around every location in the {@link #locations} array.
     * @param radiusX The radius along the x-axis.
     * @param radiusY The radius along the y-axis.
     * @param radiusZ The radius along the z-axis.
     * @return a collection of all entities near the {@link ParticleLine}.
     */
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
        Collection<Entity> entities = new ArrayList<>();
        for (Location location : locations) {
            Collection<Entity> locationEntities = location.getWorld().getNearbyEntities(location, radiusX, radiusY, radiusZ);
            entities.addAll(locationEntities);
        }
        return entities;
    }

    /**
     *  Finds all entities in a specified radius around every location in the {@link #locations} array.
     * @param radiusX The radius along the x-axis.
     * @param radiusY The radius along the y-axis.
     * @param radiusZ The radius along the z-axis.
     * @param filter The filter which will determine what entities to search for.
     * @return a collection of all entities near the {@link ParticleLine}.
     */
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter) {
        Collection<Entity> entities = new ArrayList<>();
        for (Location location : locations) {
            Collection<Entity> locationEntities = location.getWorld().getNearbyEntities(location, radiusX, radiusY, radiusZ, filter);
            entities.addAll(locationEntities);
        }
        return entities;
    }

    public ParticleLine clone() {
        ParticleLine particleLine = new ParticleLine(startLocation.clone(), endLocation.clone(), interval);
        particleLine.updateParticles(particles);
        return particleLine;
    }

    /**
     * Spawns the ParticleLine all at once.
     */
    public void spawn() {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        for (RegularParticle particle : particles) {
            particle.spawn();
        }
    }
}
