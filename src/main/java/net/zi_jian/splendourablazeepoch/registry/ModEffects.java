package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.effect.CrisisEffect;
import net.zi_jian.splendourablazeepoch.effect.HazeEffect;
import net.zi_jian.splendourablazeepoch.effect.ParalyticToxinEffect;
import net.zi_jian.splendourablazeepoch.effect.TargetEffect;

public final class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<MobEffect> CRISIS = MOB_EFFECTS.register("crisis", CrisisEffect::new);
    public static final RegistryObject<MobEffect> TARGET = MOB_EFFECTS.register("target", TargetEffect::new);
    public static final RegistryObject<MobEffect> PARALYTIC_TOXIN = MOB_EFFECTS.register("paralytictoxin", ParalyticToxinEffect::new);
    public static final RegistryObject<MobEffect> HAZE = MOB_EFFECTS.register("haze", HazeEffect::new);

    private ModEffects() {
    }
}
