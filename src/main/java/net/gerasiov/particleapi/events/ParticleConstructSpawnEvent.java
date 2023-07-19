package net.gerasiov.particleapi.events;

import net.gerasiov.particleapi.geometry.ParticleConstruct;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParticleConstructSpawnEvent extends Event implements Cancellable {
    private final ParticleConstruct particleConstruct;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public ParticleConstructSpawnEvent(ParticleConstruct particleConstruct) {
        this.particleConstruct = particleConstruct;
    }

    public ParticleConstruct getParticleConstruct() {
        return particleConstruct;
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
