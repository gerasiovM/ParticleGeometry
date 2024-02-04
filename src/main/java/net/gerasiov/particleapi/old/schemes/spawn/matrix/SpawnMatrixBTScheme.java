package net.gerasiov.particleapi.old.schemes.spawn.matrix;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.old.schemes.SpawnScheme;

public class SpawnMatrixBTScheme implements SpawnScheme<RegularParticle[][]> {
    @Override
    public RegularParticle[] getNextParticles(int index, RegularParticle[][] spawnParticles) {
        RegularParticle[] particles = new RegularParticle[spawnParticles[0].length];
        for (int j = 0; j < spawnParticles[0].length; j++) particles[j] = spawnParticles[index][j];
        return particles;
    }

    @Override
    public boolean isFinished(int index, RegularParticle[][] particles) {
        return index == particles.length;
    }
}
