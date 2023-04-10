package net.gerasiov.particleapi.events;

import net.gerasiov.particleapi.particles.ParticlePoint;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParticleSpawnEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final ParticlePoint particlePoint;
    private boolean cancelled = false;

    public ParticleSpawnEvent(ParticlePoint particlePoint) {
        this.particlePoint = particlePoint;
    }

    public ParticlePoint getParticle() {
        return particlePoint;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
