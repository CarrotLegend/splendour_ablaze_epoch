package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public final class LegacyPillarVariantBlock extends RotatedPillarBlock {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 2);
    public static final IntegerProperty DIRECTION = IntegerProperty.create("direction", 0, 2);

    public LegacyPillarVariantBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.Y)
                .setValue(BLOCKSTATE, 0).setValue(DIRECTION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIRECTION, BLOCKSTATE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock,
                                BlockPos neighborPos, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, moving);
        if (level.isClientSide) {
            return;
        }
        for (Direction direction : Direction.values()) {
            if (!level.hasChunkAt(pos.relative(direction))) {
                return;
            }
        }

        boolean up = level.getBlockState(pos.above()).is(this);
        boolean down = level.getBlockState(pos.below()).is(this);
        boolean east = level.getBlockState(pos.east()).is(this);
        boolean west = level.getBlockState(pos.west()).is(this);
        boolean south = level.getBlockState(pos.south()).is(this);
        boolean north = level.getBlockState(pos.north()).is(this);

        int variant = 0;
        Direction.Axis axis = state.getValue(AXIS);
        if (up != down) {
            variant = up ? 2 : 1;
        }
        if (east || west) {
            variant = east == west ? 0 : east ? 1 : 2;
            BlockState neighbor = level.getBlockState(east && !west ? pos.east() : pos.west());
            axis = neighbor.getValue(AXIS);
        }
        if (south || north) {
            variant = south == north ? 0 : south ? 1 : 2;
            BlockState neighbor = level.getBlockState(south && !north ? pos.south() : pos.north());
            axis = neighbor.getValue(AXIS);
        }
        BlockState updated = state.setValue(BLOCKSTATE, variant).setValue(AXIS, axis);
        if (updated != state) {
            level.setBlock(pos, updated, 3);
        }
    }
}
