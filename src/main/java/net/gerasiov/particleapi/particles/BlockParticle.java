package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class BlockParticle extends RegularParticle {
    private final BlockData blockData;

    /**
     * Creates a new {@link BlockParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param blockData The {@link BlockData} for the particle.
     */
    public BlockParticle(Particle type, Location location, BlockData blockData) {
        super(type, location);
        this.blockData = blockData;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public BlockParticle clone() {
        return new BlockParticle(getType(), getLocation(), blockData);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent event = new ParticleSpawnEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            getLocation().getWorld().spawnParticle(getType(), getLocation(), 1, blockData);
        }
    }
}
