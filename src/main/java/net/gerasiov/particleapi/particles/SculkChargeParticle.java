package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

public class SculkChargeParticle extends RegularParticle {
    private float angle;

    /**
     * Creates a new {@link SculkChargeParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param angle    The angle for the particle in radians. 0 will display it upright, while Math.PI will display it upside down
     */
    public SculkChargeParticle(Location location, float angle) {
        super(Particle.SCULK_CHARGE, location);
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public SculkChargeParticle clone() {
        return new SculkChargeParticle(getLocation(), angle);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 1, angle);
        }
    }
}
