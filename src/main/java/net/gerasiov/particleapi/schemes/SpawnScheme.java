package net.gerasiov.particleapi.schemes;


/**
 * Is responsible for how particles in ParticleGroups spawn
 * @param <T> Used to declare whether the ParticleGroup uses 1 dimension, 2 or 3.
 *          Possible uses: RegularParticle[], RegularParticle[][], RegularParticle[][][]
 */
public interface SpawnScheme<T> {
    int[] getNextParticleIndexes(int index);
    boolean isFinished(int index, T particles);
}
