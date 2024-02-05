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

import static com.gerasiov.particlegeometry.Util.doubleEquals;
import static com.gerasiov.particlegeometry.Util.calculateRealInterval;

public class ParticleRectangle implements ParticleConstruct {
    private ParticleLine[] particleLines;
    private Location center;
    private double interval;

    /**
     * Creates a {@link ParticleRectangle} object, so that the firstCorner is {@link #particleLines}[0][0],
     * the secondCorner is [0][m], the thirdCorner is [n][0] and the fourthCorner is [n][m],
     * while the intervals between particles and particle lines are as close to the specified as possible
     * @param firstCorner The first {@link Location} in the first {@link ParticleLine}.
     * @param secondCorner The last {@link Location} in the first {@link ParticleLine}.
     * @param thirdCorner The fist {@link Location} in the last {@link ParticleLine}.
     * @param fourthCorner The last {@link Location} in the last {@link ParticleLine}.
     * @param verticalInterval The approximate interval between every {@link ParticleLine} in {@link #particleLines}
     * @param horizontalInterval The approximate interval between every {@link RegularParticle} in every {@link ParticleLine}
     */
    public ParticleRectangle(Location firstCorner, Location secondCorner, Location thirdCorner, Location fourthCorner, double verticalInterval, double horizontalInterval) {
        if (verticalInterval <= 0 || horizontalInterval <= 0) {
            throw new IllegalArgumentException("Interval must be greater than 0");
        }
        double distance12 = firstCorner.distance(secondCorner);
        double distance13 = firstCorner.distance(thirdCorner);
        double distance24 = secondCorner.distance(fourthCorner);
        double distance23 = secondCorner.distance(thirdCorner);
        double distance14 = firstCorner.distance(fourthCorner);

        if (!(doubleEquals(distance12 * distance12 + distance13 * distance13, distance23 * distance23) &&
                doubleEquals(distance12 * distance12 + distance24 * distance24, distance14 * distance14))) {
            throw new IllegalArgumentException("Locations don't form a rectangle");
        }
        fillParticleLineArray(firstCorner, secondCorner, thirdCorner, fourthCorner, verticalInterval, horizontalInterval);
    }


    /**
     * Creates a {@link ParticleRectangle} object, so that the firstCorner is {@link #particleLines}[0][0],
     * the secondCorner is [0][m], the thirdCorner is [n][0] and the fourthCorner is [n][m],
     * with the specified amount of particles.
     * @param firstCorner The first {@link Location} in the first {@link ParticleLine}.
     * @param secondCorner The last {@link Location} in the first {@link ParticleLine}.
     * @param thirdCorner The fist {@link Location} in the last {@link ParticleLine}.
     * @param fourthCorner The last {@link Location} in the last {@link ParticleLine}.
     * @param verticalNumOfParticles The amount of {@link ParticleLine}s in {@link #particleLines}
     * @param horizontalNumOfParticles The amount of  {@link RegularParticle}s in every {@link ParticleLine}
     */
    public ParticleRectangle(Location firstCorner, Location secondCorner, Location thirdCorner, Location fourthCorner, int verticalNumOfParticles, int horizontalNumOfParticles) {
        if (verticalNumOfParticles < 2 || horizontalNumOfParticles < 2) {
            throw new IllegalArgumentException("Number of particles must be greater or equal to 2");
        }
        double distance12 = firstCorner.distance(secondCorner);
        double distance13 = firstCorner.distance(thirdCorner);
        double distance24 = secondCorner.distance(fourthCorner);
        double distance23 = secondCorner.distance(thirdCorner);
        double distance14 = firstCorner.distance(fourthCorner);

        if (!(doubleEquals(distance12 * distance12 + distance13 * distance13, distance23 * distance23) &&
                doubleEquals(distance12 * distance12 + distance24 * distance24, distance14 * distance14))) {
            throw new IllegalArgumentException("Locations don't form a rectangle");
        }
        fillParticleLineArray(firstCorner, secondCorner, thirdCorner, fourthCorner, distance13 / verticalNumOfParticles, distance12 / horizontalNumOfParticles);
    }

    private ParticleRectangle(ParticleLine[] particleLines, Location center, double interval) {
        this.particleLines = particleLines;
        this.center = center;
        this.interval = interval;
    }

    private void fillParticleLineArray(Location firstCorner, Location secondCorner, Location thirdCorner, Location fourthCorner, double verticalInterval, double horizontalInterval) {
        center = firstCorner.clone();
        // Vertical
        verticalInterval = calculateRealInterval(firstCorner, secondCorner, verticalInterval);
        this.interval = verticalInterval;
        Vector verticalDirectionVector = secondCorner.clone().toVector().subtract(firstCorner.toVector()).normalize().multiply(verticalInterval);
        double distance = secondCorner.distance(firstCorner);
        int verticalNumberOfParticles = (int) Math.ceil(distance / verticalInterval);
        center.add(verticalDirectionVector.clone().multiply(((double) verticalNumberOfParticles) / 2));

        // Horizontal
        horizontalInterval = calculateRealInterval(firstCorner, thirdCorner, horizontalInterval);
        Vector horizontalDirectionVector = thirdCorner.clone().toVector().subtract(firstCorner.toVector()).normalize().multiply(horizontalInterval);
        distance = thirdCorner.distance(firstCorner);
        int horizontalNumberOfParticles = (int) Math.ceil(distance / horizontalInterval);
        center.add(horizontalDirectionVector.clone().multiply(((double) horizontalNumberOfParticles) / 2));

        particleLines = new ParticleLine[verticalNumberOfParticles];
        for (int i = 0; i < verticalNumberOfParticles; i++) {
            particleLines[i] = new ParticleLine(firstCorner.clone().add(verticalDirectionVector.multiply(i)), firstCorner.clone().add(horizontalDirectionVector.multiply(horizontalNumberOfParticles)), horizontalInterval);
        }
    }

    /**
     * Returns the {@link ParticleLine} object at the specified index.
     *
     * @param index The index of the {@link ParticleLine} object to retrieve.
     * @return the {@link ParticleLine} object at the specified index.
     *
     * @throws IllegalArgumentException if the index is negative or greater than or equal to the length
     *                                  of the {@link #particleLines} array.
     */
    public ParticleLine getParticleLine(int index) {
        if (index < 0 || index >= particleLines.length) {
            throw new IllegalArgumentException("Input index must be greater than 0 and less than length of the instance's particleLines array");
        }
        return particleLines[index];
    }

    /**
     * @return cloned center {@link Location}.
     */
    public Location getCenterLocation() {
        return center.clone();
    }

    /**
     * @return interval between each {@link ParticleLine} in a rectangle.
     */
    public double getInterval() {
        return this.interval;
    }

    /**
     * Calls {@link ParticleLine#updateParticles} on every line in the {@link #particleLines} array,
     * so that every particle in a line indexed 'i' is a clone of the same particle in the particles array at index 'i'.
     * @param particles The array of particles to be used.
     * @throws IllegalArgumentException if the specified particle array's length isn't equal
     *                                  to the {@link #particleLines} array's length.
     */
    public void updateParticleLines(RegularParticle[] particles) {
        if (particles.length != particleLines.length) {
            throw new IllegalArgumentException("Input particles array length must be equal to instance's particleLines array length");
        }
        for (int i = 0; i < particleLines.length; i++) {
            particleLines[i].updateParticles(particles[i]);
        }
    }

    /**
     * Calls {@link ParticleLine#updateParticles} on every line in the {@link #particleLines} array,
     * so that every particle in every line is the clone of the specified particle.
     * @param particle The particle to be used.
     */
    public void updateParticleLines(RegularParticle particle) {
        for (ParticleLine particleLine : particleLines) {
            particleLine.updateParticles(particle);
        }
    }

    /**
     * Calls {@link ParticleLine#updateParticles} on a line from the {@link #particleLines} array with a specified index,
     * so that the specified particles are applied to the {@link ParticleLine} particles.
     * @param particles The particles to be used.
     * @param index     The index of the {@link ParticleLine} in {@link #particleLines}.
     * @throws IllegalArgumentException if the specified particle array's length isn't equal
     *                                  to the {@link #particleLines} array's length or if the index is negative or
     *                                  greater than or equal to the length of the {@link #particleLines} array.
     */
    public void updateParticleLine(RegularParticle[] particles, int index) {
        if (index < 0 || index >= particleLines.length) {
            throw new IllegalArgumentException("Index must be greater than 0 and less than instance's particleLines array length");
        }
        if (particles.length != particleLines[index].getLength()) {
            throw new IllegalArgumentException("Input particles array length must be equal to the particleLine's length");
        }
        particleLines[index].updateParticles(particles);
    }

    /**
     * Calls {@link ParticleLine#updateParticles} on a line from the {@link #particleLines} array with a specified index,
     * so every particle in the line is the clone of the specified particle
     * @param particle The particle to be used.
     * @param index    The index of the {@link ParticleLine} in {@link #particleLines}.
     * @throws IllegalArgumentException if the index is negative or
     *                                  greater than or equal to the length of the {@link #particleLines} array.
     */
    public void updateParticleLine(RegularParticle particle, int index) {
        if (index < 0 || index >= particleLines.length) {
            throw new IllegalArgumentException("Index must be greater than 0 and less than instance's particleLines array length");
        }
        particleLines[index].updateParticles(particle);
    }

    /**
     * Calls {@link ParticleLine#updateParticle} with a specified index on a line from the {@link #particleLines} array
     * with a specified index, so that the particle in the line at the specified index is the clone of the specified particle.
     * @param particle The particle to be used.
     * @param particleLineIndex The index of the {@link ParticleLine} in {@link #particleLines}
     * @param particleIndex The index of the {@link RegularParticle} in the {@link ParticleLine}'s particle array.
     */
    public void updateParticle(RegularParticle particle, int particleLineIndex, int particleIndex) {
        if (particleLineIndex < 0 || particleLineIndex >= particleLines.length) {
            throw new IllegalArgumentException("particleLineIndex must be greater than 0 and less than instance's particleLines array length");
        }
        if (particleIndex < 0 || particleIndex >= particleLines[particleLineIndex].getLength()) {
            throw new IllegalArgumentException("particleIndex must be greater than 0 and less than particleLine's length");
        }
        particleLines[particleLineIndex].updateParticle(particle, particleIndex);
    }

    /**
     * Moves the {@link ParticleRectangle} by adding a specified {@link Vector}.
     * @param vector Input vector that will be added to particle locations.
     */
    public void move(Vector vector) {
        for (ParticleLine particleLine : particleLines) {
            particleLine.move(vector);
        }
    }

    /**
     * Moves the {@link ParticleRectangle} by adding a {@link Vector} that is calculated from the specified {@link Location}
     * @param newLocation Input location that the center will be moved to.
     */
    public void move(Location newLocation) {
        Vector vector = newLocation.toVector().subtract(center.toVector());
        move(vector);
    }

    /**
     * Rotates the {@link ParticleRectangle} using matrix multiplication around the {@link Location}.
     * @param xRotation      The rotation around the x-axis. In radians.
     * @param yRotation      The rotation around the y-axis. In radians.
     * @param zRotation      The rotation around the z-axis. In radians.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotate(double xRotation, double yRotation, double zRotation, Location rotationCenter) {
        for (ParticleLine particleLine : particleLines) {
            particleLine.rotate(xRotation, yRotation, zRotation, rotationCenter);
        }
    }

    /**
     * Rotates the {@link ParticleRectangle} using matrix multiplication around the center of the rectangle.
     * @param xRotation      The rotation around the x-axis. In radians.
     * @param yRotation      The rotation around the y-axis. In radians.
     * @param zRotation      The rotation around the z-axis. In radians.
     */
    public void rotate(double xRotation, double yRotation, double zRotation) {
        rotate(xRotation, yRotation, zRotation, center);
    }

    /**
     * Rotates the {@link ParticleRectangle} using matrix multiplication around the specified {@link Axis} and {@link Location}.
     * @param rotation       The rotation around the axis. In radians.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     * @param axis           The {@link Axis} enum used to determine around which axis to rotate.
     */
    public void rotateAroundAxis(double rotation, Location rotationCenter, Axis axis) {
        for (ParticleLine particleLine : particleLines) {
            particleLine.rotateAroundAxis(rotation, rotationCenter, axis);
        }
    }

    /**
     * Rotates the {@link ParticleRectangle} using matrix multiplication around the center of the rectangle and the specified {@link Axis}.
     * @param rotation The rotation around the axis. In radians.
     * @param axis     The {@link Axis} enum used to determine around which axis to rotate.
     */
    public void rotateAroundAxis(double rotation, Axis axis) {
        rotateAroundAxis(rotation, center, axis);
    }

    /**
     * Rotates the {@link ParticleRectangle} around the x-axis and the {@link Location} using matrix multiplication.
     * @param rotation       The rotation around the x-axis.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotateAroundX(double rotation, Location rotationCenter) {
        rotateAroundAxis(rotation, rotationCenter, Axis.X);
    }

    /**
     * Rotates the {@link ParticleRectangle} around the x-axis and the center of the rectangle using matrix multiplication.
     * @param rotation The rotation around the x-axis.
     */
    public void rotateAroundX(double rotation) {
        rotateAroundX(rotation, center);
    }

    /**
     * Rotates the {@link ParticleRectangle} around the y-axis and the {@link Location} using matrix multiplication.
     * @param rotation       The rotation around the y-axis.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotateAroundY(double rotation, Location rotationCenter) {
        rotateAroundAxis(rotation, rotationCenter, Axis.Y);
    }

    /**
     * Rotates the {@link ParticleRectangle} around the y-axis and the center of the rectangle using matrix multiplication.
     * @param rotation The rotation around the y-axis.
     */
    public void rotateAroundY(double rotation) {
        rotateAroundX(rotation, center);
    }

    /**
     * Rotates the {@link ParticleRectangle} around the z-axis and the rotationCenter using matrix multiplication.
     * @param rotation       The rotation around the z-axis.
     * @param rotationCenter The {@link Location} that will act as the center of rotation.
     */
    public void rotateAroundZ(double rotation, Location rotationCenter) {
        rotateAroundAxis(rotation, rotationCenter, Axis.Z);
    }

    /**
     * Rotates the {@link ParticleLine} around the z-axis and the center of the rectangle using matrix multiplication.
     * @param rotation The rotation around the z-axis.
     */
    public void rotateAroundZ(double rotation) {
        rotateAroundX(rotation, center);
    }

    /**
     *  Finds all entities in a specified radius around every {@link ParticleLine} in the {@link #particleLines} array.
     * @param radiusX The radius along the x-axis.
     * @param radiusY The radius along the y-axis.
     * @param radiusZ The radius along the z-axis.
     * @return a collection of all entities near the {@link ParticleRectangle}.
     */
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
        Collection<Entity> entities = new ArrayList<>();
        for (ParticleLine particleLine : particleLines) {
            Collection<Entity> particleLineEntities = particleLine.getNearbyEntities(radiusX, radiusY, radiusZ);
            entities.addAll(particleLineEntities);
        }
        return entities;
    }

    /**
     *  Finds all entities in a specified radius around every {@link ParticleLine} in the {@link #particleLines} array.
     * @param radiusX The radius along the x-axis.
     * @param radiusY The radius along the y-axis.
     * @param radiusZ The radius along the z-axis.
     * @param filter The filter which will determine what entities to search for.
     * @return a collection of all entities near the {@link ParticleRectangle}.
     */
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter) {
        Collection<Entity> entities = new ArrayList<>();
        for (ParticleLine particleLine : particleLines) {
            Collection<Entity> particleLineEntities = particleLine.getNearbyEntities(radiusX, radiusY, radiusZ, filter);
            entities.addAll(particleLineEntities);
        }
        return entities;
    }

    @Override
    public ParticleRectangle clone() {
        ParticleLine[] newParticleLines = new ParticleLine[particleLines.length];
        for (int i = 0; i < particleLines.length; i++) {
            newParticleLines[i] = particleLines[i].clone();
        }
        return new ParticleRectangle(newParticleLines, center, interval);
    }

    /**
     * Spawns the {@link ParticleRectangle} all at once.
     */
    public void spawn() {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        for (ParticleLine particleLine : particleLines) {
            particleLine.spawn();
        }
    }
}
