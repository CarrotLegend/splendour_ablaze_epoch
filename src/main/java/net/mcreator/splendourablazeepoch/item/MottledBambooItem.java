package net.mcreator.splendourablazeepoch.item;

import net.mcreator.splendourablazeepoch.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public final class MottledBambooItem extends Item {
    public MottledBambooItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos clicked = context.getClickedPos();
        var clickedBlock = context.getLevel().getBlockState(clicked).getBlock();
        if (clickedBlock != ModBlocks.get("inkdirt") && clickedBlock != ModBlocks.get("inkgrassblock")) {
            return InteractionResult.PASS;
        }
        if (!context.getLevel().isClientSide && context.getLevel().setBlock(clicked.above(),
                ModBlocks.get("mottledbamboobud").defaultBlockState(), 3)) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }
}
