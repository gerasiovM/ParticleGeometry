package net.gerasiov.particleapi.old.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.ParticleSpawnInjector;
import net.gerasiov.particleapi.events.ParticleConstructSpawnEvent;
import net.gerasiov.particleapi.geometry.ParticleConstruct;
import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.old.schemes.SpawnScheme;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleGroup implements ParticleConstruct {

    private RegularParticle[] particles;

    public ParticleGroup(RegularParticle[] particles) {
        this.particles = particles;
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

    public void setParticle(RegularParticle particle, int index) {particles[index] = particle;}

    public ParticleGroup clone() {
        return new ParticleGroup(particles.clone());
    }

    public void spawn() {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        for (RegularParticle particle : particles) {
            particle.spawn();
        }
    }

    /**
     * Spawn all particles in group with a period between each spawn.
     *
     * @param delay  Delay before the first spawn in ticks.
     * @param period Period between every spawn in ticks.
     * @param spawnScheme The spawn scheme for the group.
     */
    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[]> spawnScheme) {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
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
     * @param spawnScheme The spawn scheme for the group.
     * @param injector An object that allows the user to inject their own code
     */
    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[]> spawnScheme, ParticleSpawnInjector injector) {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
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
