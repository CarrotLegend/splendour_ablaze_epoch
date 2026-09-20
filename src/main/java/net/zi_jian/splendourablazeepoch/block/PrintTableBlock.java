package net.zi_jian.splendourablazeepoch.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.zi_jian.splendourablazeepoch.block.entity.PrintTableBlockEntity;

public final class PrintTableBlock extends BaseEntityBlock {

    public PrintTableBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(
            BlockState state
    ) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return PrintTableBlockEntity.create(
                pos,
                state
        );
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (!level.isClientSide
                && player instanceof ServerPlayer serverPlayer
                && level.getBlockEntity(pos)
                instanceof PrintTableBlockEntity table) {

            NetworkHooks.openScreen(
                    serverPlayer,
                    table,
                    pos
            );
        }

        return InteractionResult.sidedSuccess(
                level.isClientSide
        );
    }

    @Override
    public void onRemove(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState newState,
            boolean moving
    ) {
        if (!state.is(
                newState.getBlock()
        )) {
            if (level.getBlockEntity(pos)
                    instanceof PrintTableBlockEntity table) {

                Containers.dropContents(
                        level,
                        pos,
                        table
                );

                level.updateNeighbourForOutputSignal(
                        pos,
                        this
                );
            }
        }

        super.onRemove(
                state,
                level,
                pos,
                newState,
                moving
        );
    }

    @Override
    public boolean hasAnalogOutputSignal(
            BlockState state
    ) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(
            BlockState state,
            Level level,
            BlockPos pos
    ) {
        return AbstractContainerMenu
                .getRedstoneSignalFromBlockEntity(
                        level.getBlockEntity(pos)
                );
    }
}