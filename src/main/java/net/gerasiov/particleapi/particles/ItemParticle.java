package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.function.Predicate;

public class ItemParticle extends RegularParticle{
    private ItemStack itemData;


    public ItemParticle(Location location, ItemStack itemData) {
        super(Particle.ITEM_CRACK, location);
        this.itemData = itemData;
    }

    public ItemStack getItemData() {
        return this.itemData;
    }


    @Override
    public ItemParticle clone() {
        return new ItemParticle(getLocation(), itemData);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(particleSpawnEvent);

        if (!particleSpawnEvent.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 1, itemData);
        }
    }
}
