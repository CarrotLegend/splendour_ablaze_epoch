package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public class HarvestLeavesBlock extends LeavesBlock {
    private final String shearDrop;

    public HarvestLeavesBlock(Properties properties, String shearDrop) {
        super(properties);
        this.shearDrop = shearDrop;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
                                       boolean willHarvest, FluidState fluid) {
        boolean removed = super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
        if (removed && shearDrop != null && level instanceof ServerLevel serverLevel
                && player.getMainHandItem().is(Items.SHEARS)) {
            ItemEntity item = new ItemEntity(serverLevel, pos.getX(), pos.getY(), pos.getZ(),
                    new ItemStack(ModBlocks.get(shearDrop)));
            item.setPickUpDelay(10);
            serverLevel.addFreshEntity(item);
        }
        return removed;
    }
}
