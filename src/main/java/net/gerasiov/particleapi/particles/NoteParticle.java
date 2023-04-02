package net.gerasiov.particleapi.particles;

import org.bukkit.Location;
import org.bukkit.Particle;

public class NoteParticle implements ParticlePoint {
    private Location location;
    private double note;


    /**
     * Creates a new {@link NoteParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param note The double value for note's color. Should be between 0 and 1 and also divisible by 1/24.
     */
    public NoteParticle(Location location, double note) {
        this.location = location;
        this.note = note;
    }

    /**
     * Creates a new {@link NoteParticle} object with the specified parameters.
     * @param location The location for the particle.
     * @param note The int value for note's color. Should be between 0 and 24.
     */
    public NoteParticle(Location location, int note) {
        this.location = location;
        this.note = note / 24D;
    }


    @Override
    public Particle getType() {
        return Particle.NOTE;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    public double getNote() {
        return this.note;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setNote(int note) {
        this.note = note / 24D;
    }

    public void setNote(double note) {
        this.note = note;
    }

    @Override
    public void spawn() {
        location.getWorld().spawnParticle(Particle.NOTE, location, 0, note, 0, 0, 1);
    }

}
