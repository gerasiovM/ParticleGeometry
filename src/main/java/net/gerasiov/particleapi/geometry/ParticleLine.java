package net.gerasiov.particleapi.geometry;

import net.gerasiov.particleapi.particles.DirectionalParticle;
import net.gerasiov.particleapi.particles.DustParticle;
import net.gerasiov.particleapi.particles.ParticlePoint;
import net.gerasiov.particleapi.particles.types.DirectionalParticleTypes;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.*;

public class ParticleLine extends ParticleGroup {
    private Location startLocation;
    private Location endLocation;
    private double interval;


    public ParticleLine(Location startLocation, Location endLocation, double interval, ParticlePoint particle) {
        super(createList(startLocation, endLocation, interval, particle));
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.interval = interval;
    }

    private static List<ParticlePoint> createList(Location startLocation, Location endLocation, double interval, ParticlePoint particle) {
        double startX = startLocation.getX();
        double startY = startLocation.getY();
        double startZ = startLocation.getZ();
        double endX = endLocation.getX();
        double endY = endLocation.getY();
        double endZ = endLocation.getZ();

        double distance = Math.sqrt(Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2) + Math.pow(startZ - endZ, 2));
        double realInterval = distance / Math.round(distance / interval);
        List<ParticlePoint> particles = new ArrayList<>();

        Location particleLocation = particle.getLocation();
        Particle particleType = particle.getType();
        if (particle instanceof DirectionalParticle) {
            Vector particleDirection = ((DirectionalParticle) particle).getDirection();
            double particleSpeed = ((DirectionalParticle) particle).getSpeed();
            for (double i = 0; i <= distance; i+= realInterval) {
                DirectionalParticle newParticle = new DirectionalParticle(particleType, particleLocation, particleDirection, particleSpeed);
                particles.add(newParticle);
            }
        }else if (particle instanceof DustParticle) {
            Color particleColor = ((DustParticle) particle).getColor();
            Color particleSecondaryColor = ((DustParticle) particle).getSecondaryColor();
            float particleSize = ((DustParticle) particle).getSize();
            for (double i = 0; i <= distance; i+= realInterval) {
                DustParticle newParticle = new DustParticle(particleLocation, particleColor, particleSecondaryColor, particleSize);
                particles.add(newParticle);
            }
        }

    }
}
