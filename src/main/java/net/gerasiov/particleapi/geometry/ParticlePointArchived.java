package net.gerasiov.particleapi.geometry;

import org.bukkit.*;

import java.util.UUID;

//public class ParticlePoint {
//    private Location location;
//    private Particle type;
//    private double speed;
//    private Color color;
//    private float size;
//    private boolean force;
//
//    /**
//     * Creates a new {@link ParticlePoint} object with the specified parameters.
//     *
//     * @param location The location for the particle.
//     * @param type The type for the particle using org.bukkit.Particle.
//     * @param speed The speed of the particle.
//     * @param color The color of the particle.
//     * @param size The size of the particle.
//     * @param force Whether to send the particle to players within an extended
//     * range and encourage their client to render it regardless of settings.
//     */
//    public ParticlePoint(Location location, Particle type, double speed, Color color, float size, boolean force) {
//        if (location == null) {
//            throw new IllegalArgumentException("Location cannot be null");
//        }
//        if (type == null) {
//            throw new IllegalArgumentException("Particle type cannot be null");
//        }
//        if (color == null) {
//            throw new IllegalArgumentException("Color cannot be null");
//        }
//        this.location = location;
//        this.type = type;
//        this.speed = speed;
//        this.color = color;
//        this.size = size;
//        this.force = force;
//    }
//
//    /**
//     * Creates a new {@link ParticlePoint} object with the specified parameters.
//     *
//     * @param world An instance of a World class.
//     * @param x The x-coordinate of the location.
//     * @param y The y-coordinate of the location.
//     * @param z The z-coordinate of the location.
//     * @param type The type for the particle using org.bukkit.Particle.
//     * @param speed The speed of the particle.
//     * @param color The color of the particle.
//     * @param size The size of the particle.
//     * @param force Whether to send the particle to players within an extended
//     * range and encourage their client to render it regardless of settings.
//     */
//    public ParticlePoint(World world, double x, double y, double z, Particle type, double speed, Color color, float size, boolean force) {
//        this(new Location(world, x, y, z), type, speed, color, size, force);
//    }
//
//    /**
//     * Creates a new {@link ParticlePoint} object with the specified parameters.
//     *
//     * @param worldName The name of the world.
//     * @param x The x-coordinate of the location.
//     * @param y The y-coordinate of the location.
//     * @param z The z-coordinate of the location.
//     * @param type The type for the particle using org.bukkit.Particle.
//     * @param speed The speed of the particle.
//     * @param color The color of the particle.
//     * @param size The size of the particle.
//     * @param force Whether to send the particle to players within an extended
//     * range and encourage their client to render it regardless of settings.
//     */
//    public ParticlePoint(String worldName, double x, double y, double z, Particle type, double speed, Color color, float size, boolean force) {
//        this(new Location(org.bukkit.Bukkit.getWorld(worldName), x, y, z), type, speed, color, size, force);
//    }
//
//    /**
//     * Creates a new {@link ParticlePoint} object with the specified parameters.
//     *
//     * @param worldUUID The UUID of the world.
//     * @param x The x-coordinate of the location.
//     * @param y The y-coordinate of the location.
//     * @param z The z-coordinate of the location.
//     * @param type The type for the particle using org.bukkit.ParticlePoint.
//     * @param speed The speed of the particle.
//     * @param color The color of the particle using org.bukkit.Color.
//     * @param size The size of the particle.
//     * @param force Whether to send the particle to players within an extended
//     * range and encourage their client to render it regardless of settings.
//     */
//    public ParticlePoint(UUID worldUUID, double x, double y, double z, org.bukkit.Particle type, double speed, org.bukkit.Color color, float size, boolean force) throws IllegalArgumentException {
//        this(new Location(Bukkit.getWorld(worldUUID), x, y, z), type, speed, color, size, force);
//    }
//
//    public org.bukkit.Particle getType() {
//        return type;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public boolean isForce() {
//        return force;
//    }
//
//    public Color getColor() {
//        return color;
//    }
//
//    public double getSize() {
//        return size;
//    }
//
//    public double getSpeed() {
//        return speed;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    public void setSize(float size) {
//        this.size = size;
//    }
//
//    public void setSpeed(double speed) {
//        this.speed = speed;
//    }
//
//    public void setType(org.bukkit.Particle type) {
//        this.type = type;
//    }
//
//    public void setForce(boolean force) {
//        this.force = force;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//
//    public void spawn() {
//        Particle.DustOptions dustOptions = new Particle.DustOptions(color, size);
//        location.getWorld().spawnParticle(type, location, 1, 0, 0, 0, speed);
//    }
//}
