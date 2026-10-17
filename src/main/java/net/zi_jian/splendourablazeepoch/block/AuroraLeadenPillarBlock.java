package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class AuroraLeadenPillarBlock extends Block {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 2);
    public static final IntegerProperty DIRECTION = IntegerProperty.create("direction", 0, 2);

    public AuroraLeadenPillarBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(BLOCKSTATE, 0).setValue(DIRECTION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, BLOCKSTATE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock,
                                BlockPos neighborPos, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, moving);
        if (level.isClientSide || !level.hasChunkAt(pos.above()) || !level.hasChunkAt(pos.below())) {
            return;
        }
        boolean above = level.getBlockState(pos.above()).is(this);
        boolean below = level.getBlockState(pos.below()).is(this);
        int variant = above == below ? 0 : above ? 2 : 1;
        if (state.getValue(BLOCKSTATE) != variant) {
            level.setBlock(pos, state.setValue(BLOCKSTATE, variant), 3);
        }
    }
}
