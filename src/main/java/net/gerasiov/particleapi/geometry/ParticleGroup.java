package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.particles.ParticlePoint;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ParticleGroup {
    private List<ParticlePoint> particles;


    public ParticleGroup(List<ParticlePoint> particles) {
        this.particles = particles;
    }


    public List<ParticlePoint> getParticles() {
        return particles;
    }

    public ParticlePoint getParticle(int index) {
        return particles.get(index);
    }

    public void setParticle(int index, ParticlePoint particle) {
        particles.set(index, particle);
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
                particles.get(index).spawn();
                index++;
            }
        }.runTaskTimerAsynchronously(ParticleAPI.instance, delay, period);
    }
}
