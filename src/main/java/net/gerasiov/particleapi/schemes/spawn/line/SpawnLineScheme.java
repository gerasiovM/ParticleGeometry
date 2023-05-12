package net.gerasiov.particleapi.schemes.spawn.line;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.SpawnScheme;

public class SpawnLineScheme implements SpawnScheme<RegularParticle[]> {
    @Override
    public int[] getNextParticleIndexes(int index) {
        return new int[] {index};
    }

    @Override
    public boolean isFinished(int index, RegularParticle[] spawnParticles) {
        if (index == spawnParticles.length) {
            return true;
        }
        return false;
    }
}
