package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public final class WetWhitewashedBricksBlock extends Block {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
    public static final IntegerProperty WET = IntegerProperty.create("wet", 0, 1);

    public WetWhitewashedBricksBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(BLOCKSTATE, 0).setValue(WET, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WET, BLOCKSTATE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock,
                                BlockPos neighborPos, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, moving);
        if (level.isClientSide || !level.hasChunkAt(pos.above()) || !level.hasChunkAt(pos.below())) {
            return;
        }
        if (!isWhitewashed(level.getBlockState(pos.above()))
                && !isWhitewashed(level.getBlockState(pos.below()))
                && state.getValue(BLOCKSTATE) != 1) {
            level.setBlock(pos, state.setValue(BLOCKSTATE, 1), 3);
        }
    }

    private static boolean isWhitewashed(BlockState state) {
        return state.is(ModBlocks.WET_WHITEWASHED_BRICKS_BLOCK.get())
                || state.is(ModBlocks.WHITEWASHED_BRICKS_BLOCK.get())
                || state.is(ModBlocks.PATCHY_WHITEWASHED_BRICKS_BLOCK.get())
                || state.is(ModBlocks.MOSSY_WHITEWASHED_BRICKS_BLOCK.get());
    }
}
