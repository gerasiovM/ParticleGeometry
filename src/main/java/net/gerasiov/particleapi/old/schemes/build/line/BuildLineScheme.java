package net.gerasiov.particleapi.old.schemes.build.line;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.old.schemes.build.BuildScheme;

public class BuildLineScheme implements BuildScheme {
    
    private final RegularParticle particle;

    public BuildLineScheme(RegularParticle particle) {
        this.particle = particle;
    }

    /**
     * Creates a RegularParticle array in a specific way and assigns a location to each.
     *
     * @param length The length of the needed line.
     * @return The filled RegularParticle array
     */
    public RegularParticle[] buildLine(int length) {

        RegularParticle[] particles = new RegularParticle[length];

        for (int i = 0; i < length; i++) {
            RegularParticle clonedParticle = particle.clone();
            particles[i] = clonedParticle;
        }
        return particles;
    }
}
