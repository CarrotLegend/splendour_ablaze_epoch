package net.zi_jian.splendourablazeepoch.block;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
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
                || state.is(ModBlocks.INK_GRASS_BLOCK.get())
                || state.is(ModBlocks.get("mottled_bamboo_log"))
                || state.is(ModBlocks.MOTTLED_BAMBOO_STALK.get());
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
        grow(level, pos, random);
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
        return true;
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
        if (random.nextInt(8) != 0) {
            return;
        }
        ResourceLocation id = new ResourceLocation(SplendourAblazeEpochMod.MOD_ID,
                random.nextBoolean() ? "mottled_bamboo1" : "mottled_bamboo3");
        StructureTemplate template = level.getStructureManager().get(id).orElse(null);
        if (template == null) {
            SplendourAblazeEpochMod.LOGGER.error(
                    "Missing bamboo structure {}", id
            );
            return;
        }
        BlockPos origin = pos.offset(-1, -1, -1);
        template.placeInWorld(level, origin, origin,
                new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                random, 3);
    }
}
