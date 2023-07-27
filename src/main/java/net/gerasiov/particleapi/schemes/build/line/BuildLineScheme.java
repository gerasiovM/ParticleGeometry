package net.gerasiov.particleapi.schemes.build.line;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.build.BuildScheme;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class BuildLineScheme implements BuildScheme {
    
    private final RegularParticle particle;

    public BuildLineScheme(RegularParticle particle) {
        this.particle = particle;
    }

    /**
     * Creates a RegularParticle array in a specific way and assigns a location to each.
     *
     * @param locations The locations of the particles
     * @return The filled RegularParticle array
     */
    public RegularParticle[] buildLine(Location @NotNull [] locations) {

        RegularParticle[] particles = new RegularParticle[locations.length];

        for (int i = 0; i < locations.length; i++) {
            RegularParticle clonedParticle = particle.clone();
            clonedParticle.setLocation(locations[i]);
            particles[i] = clonedParticle;
        }
        return particles;
    }
}
