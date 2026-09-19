package net.zi_jian.splendourablazeepoch.world.tree;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class ModTreeGrowers {

    public static final ConfiguredFeatureTreeGrower WUTONG =
            new ConfiguredFeatureTreeGrower(
                    key("wutongadd"),
                    key("wutongadd_1")
            );

    public static final ConfiguredFeatureTreeGrower GINKGO =
            new ConfiguredFeatureTreeGrower(
                    key("ginkgoadd")
            );

    public static final ConfiguredFeatureTreeGrower CERASUS_JAPONICA =
            new ConfiguredFeatureTreeGrower(
                    key("cerasusjaponicaadd")
            );

    public static final ConfiguredFeatureTreeGrower OSMANTHUS =
            new ConfiguredFeatureTreeGrower(
                    key("osmanthusadd")
            );

    public static final ConfiguredFeatureTreeGrower MULBERRY =
            new ConfiguredFeatureTreeGrower(
                    key("mulberryadd")
            );

    public static final ConfiguredFeatureTreeGrower HAWTHORN =
            new ConfiguredFeatureTreeGrower(
                    key("hawthorntreeadd")
            );

    public static final ConfiguredFeatureTreeGrower SPECULAR_PINE =
            new ConfiguredFeatureTreeGrower(
                    key("specularpine"),
                    key("specularpinea"),
                    key("specularpineb")
            );

    private static ResourceKey<ConfiguredFeature<?, ?>> key(
            String name
    ) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                new ResourceLocation(
                        SplendourAblazeEpochMod.MOD_ID,
                        name
                )
        );
    }

    private ModTreeGrowers() {
    }
}