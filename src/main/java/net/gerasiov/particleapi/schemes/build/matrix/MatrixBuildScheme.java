package net.gerasiov.particleapi.schemes.build.matrix;

import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.build.BuildScheme;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MatrixBuildScheme implements BuildScheme {
    private final RegularParticle particle;

    public MatrixBuildScheme(RegularParticle particle) {
        this.particle = particle;
    }

    /**
     * Creates a matrix of {@link RegularParticle}s with the given locations and intervals
     *
     * @param startLocation      The location of the top left corner of the matrix
     * @param endLocation        The location of the bottom right corner of the matrix
     * @param verticalInterval   The distance between two columns in a matrix
     * @param horizontalInterval The distance between two rows in a matrix
     * @return The particle matrix
     */
    public RegularParticle[][] createMatrix(Location startLocation, Location endLocation, double verticalInterval, double horizontalInterval) {
        // Vertical
        Location corner = new Location(endLocation.getWorld(), endLocation.getX(), startLocation.getY(), endLocation.getZ());
        Vector verticalDirectionVector = corner.toVector().subtract(startLocation.toVector()).normalize().multiply(verticalInterval);
        double distance = corner.distance(startLocation);
        int verticalNumberOfParticles = (int) Math.ceil(distance / verticalInterval);

        // Horizontal
        corner = new Location(endLocation.getWorld(), startLocation.getX(), endLocation.getY(), startLocation.getZ());
        Vector horizontalDirectionVector = corner.toVector().subtract(startLocation.toVector()).normalize().multiply(horizontalInterval);
        distance = corner.distance(startLocation);
        int horizontalNumberOfParticles = (int) Math.ceil(distance / verticalInterval);

        RegularParticle[][] particles = new RegularParticle[horizontalNumberOfParticles][verticalNumberOfParticles];

        for (int i = 0; i < horizontalNumberOfParticles; i++) {
            for (int j = 0; j < verticalNumberOfParticles; j++) {
                Location particleLocation = startLocation.clone().add(horizontalDirectionVector.multiply(i)).add(verticalDirectionVector.multiply(j));
                RegularParticle clonedParticle = particle.clone();
                clonedParticle.setLocation(particleLocation);
                particles[i][j] = clonedParticle;
            }
        }

        return particles;
    }

    @Deprecated
    public RegularParticle[][] createMatrixThreeCorners(Location TLCorner, Location TRCorner, Location BLCorner, double verticalInterval, double horizontalInterval) {
        throw new UnsupportedOperationException("createMatrixThreeCorners is not yet implemented");
    }
}
