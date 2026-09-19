package net.zi_jian.splendourablazeepoch.block;

import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class MottledBambooStalkBlock extends Block {

    private static final VoxelShape SHAPE =
            box(
                    6.0D,
                    0.0D,
                    6.0D,
                    10.0D,
                    16.0D,
                    10.0D
            );

    public MottledBambooStalkBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);
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
    public VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(
            BlockState state,
            LevelReader level,
            BlockPos pos
    ) {
        BlockState below =
                level.getBlockState(
                        pos.below()
                );

        return below.is(ModBlocks.INK_DIRT.get())
                || below.is(ModBlocks.INK_GRASS_BLOCK.get())
                || below.is(ModBlocks.MOTTLED_BAMBOO_BUD.get())
                || below.is(ModBlocks.MOTTLED_BAMBOO_STALK.get());
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (
                direction == Direction.DOWN
                        && !canSurvive(
                                state,
                                level,
                                pos
                        )
        ) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(
                state,
                direction,
                neighborState,
                level,
                pos,
                neighborPos
        );
    }
}
