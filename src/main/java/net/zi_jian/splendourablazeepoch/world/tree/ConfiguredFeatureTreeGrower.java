package net.zi_jian.splendourablazeepoch.world.tree;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class ConfiguredFeatureTreeGrower extends AbstractTreeGrower {

    private final List<ResourceKey<ConfiguredFeature<?, ?>>> features;

    public ConfiguredFeatureTreeGrower(
            List<ResourceKey<ConfiguredFeature<?, ?>>> features
    ) {
        if (features == null || features.isEmpty()) {
            throw new IllegalArgumentException(
                    "ConfiguredFeatureTreeGrower requires at least one feature"
            );
        }

        this.features = List.copyOf(features);
    }

    @SafeVarargs
    public ConfiguredFeatureTreeGrower(
            ResourceKey<ConfiguredFeature<?, ?>>... features
    ) {
        this(List.of(features));
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(
            RandomSource random,
            boolean hasFlowers
    ) {
        if (features.size() == 1) {
            return features.get(0);
        }

        return features.get(
                random.nextInt(features.size())
        );
    }
}