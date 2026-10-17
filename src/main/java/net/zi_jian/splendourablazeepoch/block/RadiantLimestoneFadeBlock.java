package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public final class RadiantLimestoneFadeBlock extends Block {
    public RadiantLimestoneFadeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
                                       boolean willHarvest, FluidState fluid) {
        boolean removed = super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
        if (removed && !level.isClientSide) {
            fadeNeighbors(level, pos);
        }
        return removed;
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        if (!level.isClientSide) {
            fadeNeighbors(level, pos);
        }
    }

    private static void fadeNeighbors(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            if (level.hasChunkAt(neighbor)
                    && level.getBlockState(neighbor).is(ModBlocks.RADIANT_LIMESTONE.get())) {
                level.setBlock(neighbor, ModBlocks.FADED_RADIANT_LIMESTONE.get().defaultBlockState(), 3);
            }
        }
    }
}
