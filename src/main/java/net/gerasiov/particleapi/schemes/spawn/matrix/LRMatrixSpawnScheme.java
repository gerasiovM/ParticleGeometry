package net.gerasiov.particleapi.schemes.spawn.matrix;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.SpawnScheme;

public class LRMatrixSpawnScheme implements SpawnScheme<RegularParticle[][]> {

    @Override
    public RegularParticle[] getNextParticles(int index, RegularParticle[][] spawnParticles) {
        RegularParticle[] particles = new RegularParticle[spawnParticles.length];
        for (int i = 0; i < spawnParticles.length; i++) particles[i] = spawnParticles[i][index];
        return particles;
    }

    @Override
    public boolean isFinished(int index, RegularParticle[][] particles) {
        return index == particles[0].length;
    }
}
