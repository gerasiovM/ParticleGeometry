package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.particles.*;
import net.gerasiov.particleapi.particles.types.DirectionalParticleTypes;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class ParticleLine extends ParticleGroup {
    private Location startLocation;
    private Location endLocation;
    private double interval;
    private ArrayList<ParticlePoint> particles;

    private ArrayList<ParticlePoint> template;


    public ParticleLine(Location startLocation, Location endLocation, double interval, ParticlePoint particle) {
        super(createList(startLocation, endLocation, calculateRealInterval(startLocation, endLocation, interval), particle));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = calculateRealInterval(startLocation, endLocation, interval);
        this.particles = createList(startLocation, endLocation, interval, particle);
        ArrayList<ParticlePoint> template = new ArrayList<>();
        template.add(particle);
        this.template = template;
    }

    private static ArrayList<ParticlePoint> createList(Location startLocation, Location endLocation, double interval, ParticlePoint particle) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);

        ArrayList<ParticlePoint> particles = new ArrayList<>();

        Vector directionVector = endLocation.toVector().subtract(startLocation.toVector());
        directionVector.normalize();
        Vector displacementVector = directionVector.clone().multiply(interval);

        for (int i = 0; i < numberOfParticles; i++) {
            Location newLocation = startLocation.clone().add(displacementVector.multiply(i));
            ParticlePoint newParticle = particle.clone();
            newParticle.setLocation(newLocation);
            particles.add(newParticle);
        }

        return particles;
    }

    public static double calculateRealInterval(Location startLocation, Location endLocation, double interval) {
        double distance = endLocation.toVector().subtract(startLocation.toVector()).length();
        double numberOfParticles = Math.round(distance / interval);
        return distance / numberOfParticles;
    }

    public Location getStartLocation() {
        return this.startLocation;
    }

    public Location getEndLocation() {
        return this.endLocation;
    }

    public double getInterval() {
        return this.interval;
    }

    public ArrayList<ParticlePoint> getTemplate() {
        return this.template;
    }
}
