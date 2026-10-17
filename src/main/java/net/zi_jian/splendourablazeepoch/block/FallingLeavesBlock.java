package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zi_jian.splendourablazeepoch.registry.ModParticles;

public final class FallingLeavesBlock extends HarvestLeavesBlock {
    private final boolean wutong;

    public FallingLeavesBlock(Properties properties, boolean wutong) {
        super(properties, wutong ? "wutong_leaves" : "blossomosmanthusleaves");
        this.wutong = wutong;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        if (!level.isClientSide && !state.is(oldState.getBlock())) {
            level.scheduleTick(pos, this, 60);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        if (!level.getBlockState(pos).is(this)) {
            return;
        }
        if (wutong) {
            if (random.nextInt(6) == 2) {
                level.sendParticles(ModParticles.WUTONG_LEAVES_FALLING.get(), pos.getX(), pos.getY(), pos.getZ(),
                        1, 1.0D, 1.0D, 1.0D, 0.001D);
                if (random.nextInt(5) == 0) {
                    level.sendParticles(ModParticles.WUTONG_LEAVES_FALLING_0.get(),
                            pos.getX(), pos.getY(), pos.getZ(), 1, 1.0D, 1.0D, 1.0D, 0.001D);
                }
            }
        } else {
            level.sendParticles(ModParticles.OSMANTHUS_FLOWER_FALL.get(),
                    pos.getX(), pos.getY(), pos.getZ(), 1, 1.0D, 1.0D, 1.0D, 0.001D);
        }
        level.scheduleTick(pos, this, 60);
    }
}
