package net.gerasiov.particleapi.events;

import net.gerasiov.particleapi.geometry.ParticleGroup;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ParticleGroupSpawnEvent extends org.bukkit.event.Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final ParticleGroup particleGroup;
    private boolean cancelled = false;

    public ParticleGroupSpawnEvent(ParticleGroup particleGroup) {
        this.particleGroup = particleGroup;
    }

    public ParticleGroup getParticleGroup() {
        return particleGroup;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
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

