package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.events.ParticleGroupSpawnEvent;
import net.gerasiov.particleapi.particles.ParticlePoint;
import org.bukkit.Bukkit;
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
        ParticleGroupSpawnEvent event = new ParticleGroupSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            for (ParticlePoint particle : particles) {
                particle.spawn();
            }
        }
    }

    /**
     * Spawn all particles in group with a period between each spawn.
     *
     * @param delay Delay before the first spawn in ticks.
     * @param period Period between every spawn in ticks.
     */
    public void spawnWithDelays(int delay, int period) {
        ParticleGroupSpawnEvent event = new ParticleGroupSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            new BukkitRunnable() {
                int index = 0;

                @Override
                public void run() {
                    if (event.isCancelled()) {
                        cancel();
                    }
                    particles[index].spawn();
                    index++;
                    if (index == particles.length) {
                        cancel();
                    }
                }
            }.runTaskTimer(ParticleAPI.instance, delay, period);
        }
    }
}
