package gerasiov.net.particleapi.geometry;

import org.bukkit.Location;

public class Particle {
    private Location location;
    private org.bukkit.Particle type;
    private boolean force;

    protected Particle(Location location, org.bukkit.Particle type, boolean force) {
        this.location = location;
        this.type = type;
        this.force = force;
    }

    public void spawn() {
        location.getWorld().spawnParticle(type, location, 1, force);
    }
}
