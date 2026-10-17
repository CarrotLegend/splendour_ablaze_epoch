package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public final class CogongrassBlock extends DoublePlantBlock {
    public CogongrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos soilPos) {
        if (!state.is(ModBlocks.INK_GRASS_BLOCK.get()) && !state.is(ModBlocks.INK_DIRT.get())) {
            return false;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (level.getBlockState(soilPos.relative(direction)).is(Blocks.WATER)) {
                return true;
            }
        }
        return false;
    }
}
