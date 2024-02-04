package net.gerasiov.particleapi;

import org.bukkit.Location;

public class Util {
    /**
     * This function is used to compare 2 doubles for equality.
     */
    private boolean doubleEquals(double a, double b) {
        double epsilon = 1E-6;
        return Math.abs(a - b) < epsilon;
    }

    /**
     * Calculates the real interval based on 2 locations and the initial interval.
     *
     * @param startLocation The first location.
     * @param endLocation   The second location.
     * @param interval      The initial interval.
     * @return              The correct interval
     */
    public static double calculateRealInterval(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);
        return distance / numberOfParticles;
    }
}
