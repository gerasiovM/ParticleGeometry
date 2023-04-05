package net.gerasiov.particleapi.schemes.line;

import net.gerasiov.particleapi.particles.ParticlePoint;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LineScheme {
    private final ParticlePoint particle;

    public LineScheme(ParticlePoint particle) {
        this.particle = particle;
    }

    public ParticlePoint[] createArray(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        int numberOfParticles = (int) (Math.round(distance / interval));

        ParticlePoint[] particles = new ParticlePoint[numberOfParticles];

        Vector directionVector = endLocation.toVector().subtract(startLocation.toVector());
        directionVector.normalize();
        Vector displacementVector = directionVector.clone().multiply(interval);

        for (int i = 0; i < numberOfParticles; i++) {
            Location newLocation = startLocation.clone().add(displacementVector.clone().multiply(i));
            ParticlePoint newParticle = particle.clone();
            newParticle.setLocation(newLocation);
            particles[i] = newParticle;
        }

        return particles;
    }


}
