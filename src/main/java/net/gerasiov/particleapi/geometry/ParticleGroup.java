package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.ParticleSpawnInjector;
import net.gerasiov.particleapi.events.ParticleGroupSpawnEvent;
import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.SpawnScheme;
import net.gerasiov.particleapi.schemes.spawn.line.SpawnLineScheme;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleGroup {

    private RegularParticle[] particles;
    private final SpawnScheme spawnScheme;

    public ParticleGroup(RegularParticle[] particles) {
        this.particles = particles;
        this.spawnScheme = new SpawnLineScheme();
    }

    public ParticleGroup(RegularParticle[] particles, SpawnScheme spawnScheme) {
        this.particles = particles;
        this.spawnScheme = spawnScheme;
    }

    public RegularParticle[] getParticles() {
        return particles;
    }

    public RegularParticle getParticle(int index) {
        return particles[index];
    }

    public void setParticles(RegularParticle[] particles) {
        this.particles = particles;
    }

    public void spawn() {
        ParticleGroupSpawnEvent event = new ParticleGroupSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        for (RegularParticle particle : particles) {
            particle.spawn();
        }
    }

    /**
     * Spawn all particles in group with a period between each spawn.
     *
     * @param delay  Delay before the first spawn in ticks.
     * @param period Period between every spawn in ticks.
     */
    public void spawnWithDelays(int delay, int period) {
        ParticleGroupSpawnEvent event = new ParticleGroupSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (event.isCancelled()) {
                    cancel();
                    return;
                }
                for (RegularParticle particle : spawnScheme.getNextParticles(index, particles)) {
                    particle.spawn();
                }
                index++;
                if (spawnScheme.isFinished(index, particles)) {
                    cancel();
                }
            }
        }.runTaskTimer(ParticleAPI.instance, delay, period);
    }

    /**
     * Spawn all particles in group with a period between each spawn.
     *
     * @param delay    Delay before the first spawn in ticks.
     * @param period   Period between every spawn in ticks.
     * @param injector An object that allows the user to inject their own code
     */
    public void spawnWithDelays(int delay, int period, ParticleSpawnInjector injector) {
        ParticleGroupSpawnEvent event = new ParticleGroupSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (event.isCancelled()) {
                    cancel();
                    return;
                }
                injector.reply(index);
                for (RegularParticle particle : spawnScheme.getNextParticles(index, particles)) {
                    particle.spawn();
                }
                index++;
                if (spawnScheme.isFinished(index, particles)) {
                    cancel();
                }
            }
        }.runTaskTimer(ParticleAPI.instance, delay, period);
    }
}
