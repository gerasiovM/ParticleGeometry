package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.ParticleAPI;
import net.gerasiov.particleapi.ParticleSpawnInjector;
import net.gerasiov.particleapi.events.ParticleConstructSpawnEvent;
import net.gerasiov.particleapi.particles.DustParticle;
import net.gerasiov.particleapi.particles.RegularParticle;
import net.gerasiov.particleapi.schemes.SpawnScheme;
import net.gerasiov.particleapi.schemes.build.matrix.MatrixBuildScheme;
import net.gerasiov.particleapi.schemes.spawn.matrix.LRMatrixSpawnScheme;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleMatrix implements ParticleConstruct {
    private RegularParticle[][] particles;
    private Location[][] locations;
    private Location center;
    private double interval;

    public ParticleMatrix(Location l1, Location l2, double interval) {
        fillLocationMatrix(new Location(l2.getWorld(), l2.getX(), l1.getY(), l2.getZ()), l1, l2, interval);
    }

    public ParticleMatrix(Location l1, Location l2, Location l3, double interval) {
        double distance12, distance13, distance23;
        distance12 = l1.distance(l2);
        distance13 = l1.distance(l3);
        distance23 = l2.distance(l3);
        if (doubleEquals(distance12 * distance12 + distance13 * distance13, distance23 * distance23)) {
            fillLocationMatrix(l1, l2, l3, interval);
        } else if (doubleEquals(distance12 * distance12 + distance23 * distance23, distance13 * distance13)) {
            fillLocationMatrix(l2, l1, l3, interval);
        } else if (doubleEquals(distance13 * distance13 + distance23 * distance23, distance12 * distance12)){
            fillLocationMatrix(l3, l1, l2, interval);
        } else {
            throw new IllegalArgumentException("Locations don't form a rectangle");
        }
    }

    private boolean doubleEquals(double a, double b) {
        double epsilon = 1E-6;
        return Math.abs(a - b) < epsilon;
    }

    public static double calculateRealInterval(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);
        return distance / numberOfParticles;
    }

    private void fillLocationMatrix(Location l1, Location l2, Location l3, double interval) {
        center = l1.clone();
        // Vertical
        interval = calculateRealInterval(l1, l2, interval);
        Vector verticalDirectionVector = l2.clone().toVector().subtract(l1.toVector()).normalize().multiply(interval);
        double distance = l2.distance(l1);
        int verticalNumberOfParticles = (int) Math.ceil(distance / interval);
        center.add(verticalDirectionVector.clone().multiply(((double) verticalNumberOfParticles) / 2));

        // Horizontal
        interval = calculateRealInterval(l1, l3, interval);
        Vector horizontalDirectionVector = l3.clone().toVector().subtract(l1.toVector()).normalize().multiply(interval);
        distance = l3.distance(l1);
        int horizontalNumberOfParticles = (int) Math.ceil(distance / interval);
        center.add(horizontalDirectionVector.clone().multiply(((double) horizontalNumberOfParticles) / 2));

        locations = new Location[horizontalNumberOfParticles][verticalNumberOfParticles];
        for (int i = 0; i < horizontalNumberOfParticles; i++) {
            for (int j = 0; j < verticalNumberOfParticles; j++) {
                locations[i][j] = l1.clone().add(horizontalDirectionVector.clone().multiply(i)).add(verticalDirectionVector.clone().multiply(j));
            }
        }

        particles = new RegularParticle[horizontalNumberOfParticles][verticalNumberOfParticles];
    }

    public Location getCenter() {
        return center;
    }

    public RegularParticle[][] getParticles() {
        return particles;
    }

    public RegularParticle getParticle(int row, int column) {
        return particles[row][column];
    }

    public void setParticle(RegularParticle particle, int row, int column) {
        RegularParticle newParticle = particle.clone();
        newParticle.setLocation(locations[row][column]);
        particles[row][column] = newParticle;
    }

    public void rotate(double xRotation, double yRotation, double zRotation) {
        double[][] xRotationMatrix = {
                {1, 0, 0},
                {0, Math.cos(xRotation), -Math.sin(xRotation)},
                {0, Math.sin(xRotation), Math.cos(xRotation)}};
        double[][] yRotationMatrix = {
                {Math.cos(yRotation), 0, Math.sin(yRotation)},
                {0, 1, 0},
                {-Math.sin(yRotation), 0, Math.cos(yRotation)}};
        double[][] zRotationMatrix = {
                {Math.cos(zRotation), -Math.sin(zRotation), 0},
                {Math.sin(zRotation), Math.cos(zRotation), 0},
                {0, 0, 1}};
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {

                Location location = locations[i][j].clone().subtract(center.clone());
                locations[i][j] = new Location(
                        location.getWorld(),
                        location.getX() * xRotationMatrix[0][0] + location.getY() * xRotationMatrix[0][1] + location.getZ() * xRotationMatrix[0][2],
                        location.getX() * xRotationMatrix[1][0] + location.getY() * xRotationMatrix[1][1] + location.getZ() * xRotationMatrix[1][2],
                        location.getX() * xRotationMatrix[2][0] + location.getY() * xRotationMatrix[2][1] + location.getZ() * xRotationMatrix[2][2]);
                location = locations[i][j];
                locations[i][j] = new Location(
                        location.getWorld(),
                        location.getX()* yRotationMatrix[0][0] + location.getY() * yRotationMatrix[0][1] + location.getZ() * yRotationMatrix[0][2],
                        location.getX() * yRotationMatrix[1][0] + location.getY() * yRotationMatrix[1][1] + location.getZ() * yRotationMatrix[1][2],
                        location.getX() * yRotationMatrix[2][0] + location.getY() * yRotationMatrix[2][1] + location.getZ() * yRotationMatrix[2][2]);
                location = locations[i][j];
                locations[i][j] = center.clone().add(new Location(
                        location.getWorld(),
                        location.getX() * zRotationMatrix[0][0] + location.getY() * zRotationMatrix[0][1] + location.getZ() * zRotationMatrix[0][2],
                        location.getX() * zRotationMatrix[1][0] + location.getY() * zRotationMatrix[1][1] + location.getZ() * zRotationMatrix[1][2],
                        location.getX() * zRotationMatrix[2][0] + location.getY() * zRotationMatrix[2][1] + location.getZ() * zRotationMatrix[2][2]));
                updateParticleLocations();
            }
        }
    }

    private void updateParticleLocations() {
        for (int i = 0; i < particles.length; i++) {
            for (int j = 0; j < particles[0].length; j++) {
                particles[i][j].setLocation(locations[i][j]);
            }
        }
    }

    @Override
    public ParticleMatrix clone() {
        return this;
    }

    @Override
    public void spawn() {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        DustParticle center = new DustParticle(this.center, Color.GREEN, 1.0f);
        center.spawn();
        for (int i = 0; i < particles.length; i++) {
            for (int j = 0; j < particles[0].length; j++) {
                if (particles[i][j] != null) {
                    particles[i][j].spawn();
                }
            }
        }
    }

    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[][]> spawnScheme) {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (event.isCancelled()) {
                    cancel();
                    return;
                }

                index++;

            }
        }.runTaskTimer(ParticleAPI.instance, delay, period);
    }

    public void spawnWithDelays(int delay, int period, SpawnScheme<RegularParticle[][]> spawnScheme, ParticleSpawnInjector injector) {
        ParticleConstructSpawnEvent event = new ParticleConstructSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (event.isCancelled()) {
                    cancel();
                    return;
                }
                injector.reply(index);
                for (RegularParticle particle : spawnScheme.getNextParticles(index, particles)) {
                    particle.spawn();
                }
                index++;
                if (spawnScheme.isFinished(index, particles)) {
                    cancel();
                }
            }
        }.runTaskTimer(ParticleAPI.instance, delay, period);
    }
}
