package net.zi_jian.splendourablazeepoch.block;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class MottledBambooBudBlock
        extends BushBlock
        implements BonemealableBlock {

    private static final VoxelShape SHAPE =
            box(
                    3.0D,
                    0.0D,
                    3.0D,
                    13.0D,
                    9.0D,
                    13.0D
            );

    private static final ResourceKey<ConfiguredFeature<?, ?>> GROWTH_FEATURE =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            SplendourAblazeEpochMod.MOD_ID,
                            "mottledbamboo_4"
                    )
            );

    public MottledBambooBudBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(
            BlockState state,
            BlockGetter level,
            BlockPos pos
    ) {
        return state.is(ModBlocks.INK_DIRT.get())
                || state.is(ModBlocks.INK_GRASS_BLOCK.get());
    }

    @Override
    public VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
    public void randomTick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        if (level.getMaxLocalRawBrightness(pos.above()) < 9) {
            return;
        }

        if (random.nextInt(7) != 0) {
            return;
        }

        grow(
                level,
                pos,
                random
        );
    }

    @Override
    public boolean isValidBonemealTarget(
            LevelReader level,
            BlockPos pos,
            BlockState state,
            boolean isClient
    ) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(
            Level level,
            RandomSource random,
            BlockPos pos,
            BlockState state
    ) {
        return random.nextFloat() < 0.45F;
    }

    @Override
    public void performBonemeal(
            ServerLevel level,
            RandomSource random,
            BlockPos pos,
            BlockState state
    ) {
        grow(
                level,
                pos,
                random
        );
    }

    private void grow(
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        ConfiguredFeature<?, ?> feature =
                level.registryAccess()
                        .registryOrThrow(
                                Registries.CONFIGURED_FEATURE
                        )
                        .get(GROWTH_FEATURE);

        if (feature == null) {
            SplendourAblazeEpochMod.LOGGER.error(
                    "Missing configured feature {}",
                    GROWTH_FEATURE.location()
            );

            return;
        }

        feature.place(
                level,
                level.getChunkSource().getGenerator(),
                random,
                pos
        );
    }
}