package net.gerasiov.particleapi.particles;

import java.util.Collection;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;

public class ItemParticle extends RegularParticle {
    private final ItemStack itemData;

    /**
     * Creates a new {@link ItemParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param itemData The {@link ItemStack} to be used as item data.
     */
    public ItemParticle(Location location, ItemStack itemData) {
        super(Particle.ITEM_CRACK, location);
        this.itemData = itemData;
    }

    public ItemStack getItemData() {
        return itemData;
    }

    @Override
    public ItemParticle clone() {
        return new ItemParticle(getLocation().clone(), itemData.clone());
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 1, itemData);
        }
    }
}
