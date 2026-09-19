package net.zi_jian.splendourablazeepoch.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public final class MottledBambooItem extends Item {

    public MottledBambooItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        boolean validSoil =
                clickedState.is(ModBlocks.INK_DIRT.get())
                        || clickedState.is(ModBlocks.INK_GRASS_BLOCK.get());

        if (!validSoil) {
            return InteractionResult.PASS;
        }

        BlockPos placePos = clickedPos.above();
        BlockState currentState = level.getBlockState(placePos);

        BlockPlaceContext placeContext =
                new BlockPlaceContext(context);

        if (!currentState.canBeReplaced(placeContext)) {
            return InteractionResult.FAIL;
        }

        BlockState budState =
                ModBlocks.MOTTLED_BAMBOO_BUD
                        .get()
                        .defaultBlockState();

        if (!budState.canSurvive(level, placePos)) {
            return InteractionResult.FAIL;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        boolean placed =
                level.setBlock(
                        placePos,
                        budState,
                        3
                );

        if (!placed) {
            return InteractionResult.FAIL;
        }

        level.playSound(
                null,
                placePos,
                SoundEvents.BAMBOO_SAPLING_PLACE,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}