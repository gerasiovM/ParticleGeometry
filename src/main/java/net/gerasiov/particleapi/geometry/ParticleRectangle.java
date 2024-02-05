package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.particles.RegularParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import static net.gerasiov.particleapi.Util.doubleEquals;
import static net.gerasiov.particleapi.Util.calculateRealInterval;

public class ParticleRectangle implements ParticleConstruct {
    private ParticleLine[] particleLines;
    private Location center;
    private double interval;

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
}
