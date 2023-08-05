package net.gerasiov.particleapi.schemes.build.rectangle;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.build.BuildScheme;

public class BuildRectangleScheme implements BuildScheme {
    private final RegularParticle particle;

    public BuildRectangleScheme(RegularParticle particle){this.particle = particle;}

    public RegularParticle[][] buildRectangle(int length, int length2) {
        RegularParticle[][] particles = new RegularParticle[length][length2];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length2; j++) {
                RegularParticle clonedParticle = particle.clone();
                particles[i][j] = clonedParticle;
            }
        }

        return particles;
    }
}
