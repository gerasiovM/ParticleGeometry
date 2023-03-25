package net.gerasiov.particleapi.geometry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class ParticleBuilder {
    private Location _location;
    private org.bukkit.Particle _type;
    private boolean _force;

    public ParticleBuilder() { }

    public Particle build() {
        return new Particle(_location, _type, _force);
    }

    public ParticleBuilder location(Location _location) {
        this._location = _location;
        return this;
    }

    public ParticleBuilder location(World world, int x, int y, int z) {
        this._location = new Location(world, x, y, z);
        return this;
    }

    public ParticleBuilder location(String worldName, int x, int y, int z) {
        this._location = new Location(Bukkit.getWorld(worldName), x, y, z);
        return this;
    }

    public ParticleBuilder location(UUID worldUUID, int x, int y, int z) {
        this._location = new Location(Bukkit.getWorld(worldUUID), x, y, z);
        return this;
    }

    public ParticleBuilder type(org.bukkit.Particle _type) {
        this._type = _type;
        return this;
    }

    public ParticleBuilder force(boolean _force) {
        this._force = _force;
        return this;
    }
}
