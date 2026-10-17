package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

public final class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> WUTONG_LEAVES_FALLING =
            PARTICLE_TYPES.register("wutongleavesfalling", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WUTONG_LEAVES_FALLING_0 =
            PARTICLE_TYPES.register("wutongleavesfalling_0", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> OSMANTHUS_FLOWER_FALL =
            PARTICLE_TYPES.register("osmanthusflowerfall", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CHAIN =
            PARTICLE_TYPES.register("chain", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LOC =
            PARTICLE_TYPES.register("loc", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FLOWING =
            PARTICLE_TYPES.register("flowing", () -> new SimpleParticleType(false));

    private ModParticles() {
    }
}
