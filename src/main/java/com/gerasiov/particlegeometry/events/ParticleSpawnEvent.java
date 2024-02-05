package com.gerasiov.particlegeometry.events;

import com.gerasiov.particlegeometry.particles.RegularParticle;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParticleSpawnEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final RegularParticle particle;
    private boolean cancelled;

    public ParticleSpawnEvent(RegularParticle particle) {
        this.particle = particle;
    }

    public RegularParticle getParticle() {
        return particle;
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
