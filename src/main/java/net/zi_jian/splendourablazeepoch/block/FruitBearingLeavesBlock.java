package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public final class FruitBearingLeavesBlock extends HarvestLeavesBlock {
    private final String fruitId;

    public FruitBearingLeavesBlock(Properties properties, String fruitId, String shearDrop) {
        super(properties, shearDrop);
        this.fruitId = fruitId;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        if (level.getBlockState(pos).is(this) && random.nextFloat() < 0.01F) {
            BlockState fruit = ModBlocks.get(fruitId).defaultBlockState();
            level.setBlock(pos, fruit
                    .setValue(DISTANCE, state.getValue(DISTANCE))
                    .setValue(PERSISTENT, state.getValue(PERSISTENT))
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
        }
    }
}
