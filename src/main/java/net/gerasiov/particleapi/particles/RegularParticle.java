package net.gerasiov.particleapi.particles;

import org.bukkit.Location;
import org.bukkit.Particle;

public class RegularParticle implements ParticlePoint {

    private final Particle type;
    private Location location;


    public RegularParticle(Particle type, Location location) {
        this.type = type;
        this.location = location;
    }

    @Override
    public Particle getType() {
        return type;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public RegularParticle clone() {
        return new RegularParticle(this.type, this.location);
    }

    @Override
    public void spawn() {
        location.getWorld().spawnParticle(type, location, 1);
    }
}
