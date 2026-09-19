package net.zi_jian.splendourablazeepoch.registry;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SplendourAblazeEpochMod.MOD_ID);

    private ModParticles() {
    }
}
