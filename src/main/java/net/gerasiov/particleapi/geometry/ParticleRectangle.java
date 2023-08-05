package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.schemes.build.rectangle.BuildRectangleScheme;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ParticleRectangle extends ParticleMatrix {
    /**
     * Creates a ParticleRectangle which will be in the upright position.
     * The 2 locations provided should be the opposite corners of the rectangle.
     *
     * @param firstCorner        The first corner of the rectangle.
     * @param oppositeCorner     The opposite to the first corner of the rectangle.
     * @param verticalInterval   The initial interval between all particle locations. (Vertical in relation to matrix columns)
     * @param horizontalInterval The initial horizontal interval between particles. (Horizontal in relation to matrix rows)
     * @param buildScheme        The BuildRectangleScheme instance to create the RegularParticle[][]
     */
    public ParticleRectangle(@NotNull Location firstCorner, @NotNull Location oppositeCorner, double verticalInterval, double horizontalInterval, BuildRectangleScheme buildScheme) {
        super(firstCorner, oppositeCorner, verticalInterval, horizontalInterval);
        setParticles(buildScheme.buildRectangle(getLocations().length, getLocations()[0].length));
    }

    /**
     * Creates a ParticleRectangle.
     * The 3 locations provided should form a right angle. Order doesn't matter.
     *
     * @param firstCorner        The first corner of the rectangle.
     * @param secondCorner       The second corner of the rectangle.
     * @param thirdCorner        The third corner of the rectangle.
     * @param verticalInterval   The initial interval between all particle locations. (Vertical in relation to matrix columns)
     * @param horizontalInterval The initial horizontal interval between particles. (Horizontal in relation to matrix rows)
     * @param buildScheme        The BuildRectangleScheme instance to create the RegularParticle[][]
     */
    public ParticleRectangle(@NotNull Location firstCorner, @NotNull Location secondCorner, @NotNull Location thirdCorner, double verticalInterval, double horizontalInterval, BuildRectangleScheme buildScheme) {
        super(firstCorner, secondCorner, thirdCorner, verticalInterval, horizontalInterval);
        setParticles(buildScheme.buildRectangle(getParticles().length, getLocations()[0].length));
    }


}
