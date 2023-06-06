package net.gerasiov.particleapi.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import net.gerasiov.particleapi.geometry.ParticleGroup;

public class ParticleGroupSpawnEvent extends Event implements Cancellable {
    private final ParticleGroup particleGroup;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

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
