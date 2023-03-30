package net.gerasiov.particleapi.particles.types;
import org.bukkit.Particle;

public enum DirectionalParticleTypes {
    BUBBLE_COLUMN_UP(Particle.BUBBLE_COLUMN_UP),
    BUBBLE_POP(Particle.BUBBLE_POP),
    CAMPFIRE_COZY_SMOKE(Particle.CAMPFIRE_COSY_SMOKE),
    CAMPFIRE_SIGNAL_SMOKE(Particle.CAMPFIRE_SIGNAL_SMOKE),
    CLOUD(Particle.CLOUD),
    CRIT(Particle.CRIT),
    CRIT_MAGIC(Particle.CRIT_MAGIC),
    DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR),
    DRAGON_BREATH(Particle.DRAGON_BREATH),
    ELECTRIC_SPARK(Particle.ELECTRIC_SPARK),
    ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE),
    END_ROD(Particle.END_ROD),
    EXPLOSION_NORMAL(Particle.EXPLOSION_NORMAL),
    FIREWORKS_SPARK(Particle.FIREWORKS_SPARK),
    FLAME(Particle.FLAME),
    NAUTILUS(Particle.NAUTILUS),
    PORTAL(Particle.PORTAL),
    REVERSE_PORTAL(Particle.REVERSE_PORTAL),
    SCRAPE(Particle.SCRAPE),
    SCULK_CHARGE(Particle.SCULK_CHARGE),
    SCULK_CHARGE_POP(Particle.SCULK_CHARGE_POP),
    SCULK_SOUL(Particle.SCULK_SOUL),
    SMALL_FLAME(Particle.SMALL_FLAME),
    SMOKE_LARGE(Particle.SMOKE_LARGE),
    SMOKE_NORMAL(Particle.SMOKE_NORMAL),
    SOUL(Particle.SOUL),
    SOUL_FIRE_FLAME(Particle.SOUL_FIRE_FLAME),
    SPIT(Particle.SPIT),
    SQUID_INK(Particle.SQUID_INK),
    TOTEM(Particle.TOTEM),
    WATER_BUBBLE(Particle.WATER_BUBBLE),
    WATER_WAKE(Particle.WATER_WAKE),
    WAX_OFF(Particle.WAX_OFF),
    WAX_ON(Particle.WAX_ON);

    private final Particle type;

    DirectionalParticleTypes(Particle particle) {
        this.type = particle;
    }

    public Particle getParticle() {
        return type;
    }

    public static boolean contains(Particle particle) {
        for (DirectionalParticleTypes directionalParticleTypes : DirectionalParticleTypes.values()) {
            if (directionalParticleTypes.getParticle() == particle) {
                return true;
            }
        }
        return false;
    }
}