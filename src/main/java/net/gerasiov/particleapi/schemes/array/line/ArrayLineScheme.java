package net.gerasiov.particleapi.schemes.array.line;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.array.ArrayScheme;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ArrayLineScheme implements ArrayScheme {
    private final RegularParticle particle;

    public ArrayLineScheme(RegularParticle particle) {
        this.particle = particle;
    }

    /**
     * Creates an array of {@link RegularParticle}s along the line between the given start and end locations, with a given interval.
     *
     * @param startLocation the starting Location of the line
     * @param endLocation the ending Location of the line
     * @param interval the distance between each {@link RegularParticle} on the line
     */
    public RegularParticle[] createArray(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        int numberOfParticles = (int) (Math.round(distance / interval));

        RegularParticle[] particles = new RegularParticle[numberOfParticles];

        Vector directionVector = endLocation.toVector().subtract(startLocation.toVector());
        directionVector.normalize();
        Vector displacementVector = directionVector.clone().multiply(interval);

        for (int i = 0; i < numberOfParticles; i++) {
            Location newLocation = startLocation.clone().add(displacementVector.clone().multiply(i));
            RegularParticle newParticle = particle.clone();
            newParticle.setLocation(newLocation);
            particles[i] = newParticle;
        }

        return particles;
    }
}
