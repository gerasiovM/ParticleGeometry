package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.particles.ParticlePoint;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleGroup {
    private ParticlePoint[] particles;


    public ParticleGroup(ParticlePoint[] particles) {
        this.particles = particles;
    }


    public ParticlePoint[] getParticles() {
        return particles;
    }

    public ParticlePoint getParticle(int index) {
        return particles[index];
    }

    public void spawn() {
        for (ParticlePoint particle: particles) {
            particle.spawn();
        }
    }

    /**
     * Spawn all particles in group with a period between each spawn.
     *
     * @param delay Delay before the first spawn in ticks.
     * @param period Period between every spawn in ticks.
     */
    public void spawnWithDelays(int delay, int period) {
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                particles[index].spawn();
                index++;
                if (index == particles.length) {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(ParticleAPI.instance, delay, period);
    }
}
