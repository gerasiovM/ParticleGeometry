package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Vibration;

public class VibrationParticle extends RegularParticle {
    private Vibration vibration;

    /**
     * Creates a new {@link VibrationParticle} object with the specified parameters.
     *
     * @param vibration The {@link Vibration} for the particle.
     */
    public VibrationParticle(Vibration vibration) {
        super(Particle.VIBRATION, vibration.getOrigin());
        this.vibration = vibration;
    }

    public Vibration getVibration() {
        return vibration;
    }

    public void setVibration(Vibration vibration) {
        this.vibration = vibration;
    }

    @Override
    public VibrationParticle clone() {
        return new VibrationParticle(vibration);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            getLocation().getWorld().spawnParticle(Particle.VIBRATION, getLocation(), 1, vibration);
        }
    }
}
