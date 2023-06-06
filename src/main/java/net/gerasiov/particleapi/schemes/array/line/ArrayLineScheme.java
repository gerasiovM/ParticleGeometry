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
     * @return the array of RegularParticle.
     */
    public RegularParticle[] createArray(Location startLocation, Location endLocation, double interval) {
        Vector directionVector = endLocation.toVector().subtract(startLocation.toVector()).normalize().multiply(interval);
        double distance = endLocation.distance(startLocation);
        int numberOfParticles = (int) Math.ceil(distance / interval);

        RegularParticle[] particles = new RegularParticle[numberOfParticles];        

        for (int i = 0; i < numberOfParticles; i++) {
            Location particleLocation = startLocation.clone().add(directionVector.clone().multiply(i));
            RegularParticle clonedParticle = particle.clone();
            clonedParticle.setLocation(particleLocation);
            particles[i] = clonedParticle;
        }

        return particles;
    }
}
