package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.build.line.BuildLineScheme;
import org.bukkit.Location;

import org.jetbrains.annotations.NotNull;

public class ParticleLine extends ParticleArray {

    /**
     * Constructs a {@link ParticleLine} object representing a line of RegularParticles
     * between the given start and end locations, with the given interval between RegularParticles.
     *
     * @param startLocation the starting {@link Location} of the line
     * @param endLocation the ending {@link Location} of the line
     * @param interval the desired distance between RegularParticles along the line
     */
    public ParticleLine(@NotNull Location startLocation, @NotNull Location endLocation, double interval, RegularParticle particle) {
        super(startLocation, endLocation, interval);
        setParticles(new BuildLineScheme(particle).buildLine(getLocations()));
    }

    /**
     * Constructs a {@link ParticleLine} object representing a line of RegularParticles
     * between the given start and end locations, with the given interval between RegularParticles,
     * and using the provided {@link BuildLineScheme} to define the particle effect to be displayed.
     *
     * @param startLocation the starting {@link Location} of the line
     * @param endLocation the ending {@link Location} of the line
     * @param interval the desired distance between RegularParticles along the line
     * @param buildScheme the {@link BuildLineScheme} object, defining the particle effects to be displayed
     */
    public ParticleLine(@NotNull Location startLocation, @NotNull Location endLocation, double interval, BuildLineScheme buildScheme) {
        super(startLocation, endLocation, interval);
        setParticles(buildScheme.buildLine(getLocations()));
    }


}
