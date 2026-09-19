package net.zi_jian.splendourablazeepoch.registry;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.world.feature.StructureFeatureConfiguration;
import net.zi_jian.splendourablazeepoch.world.feature.TopworldStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<Feature<?>> STRUCTURE_FEATURE = FEATURES.register("structure_feature",
            () -> new TopworldStructureFeature(StructureFeatureConfiguration.CODEC));

    private ModFeatures() {}
}
