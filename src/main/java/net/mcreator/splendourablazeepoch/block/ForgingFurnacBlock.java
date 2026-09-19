package net.mcreator.splendourablazeepoch.block;

import net.mcreator.splendourablazeepoch.block.entity.ForgingFurnacBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public final class ForgingFurnacBlock extends BaseEntityBlock {
    public ForgingFurnacBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.0F, 20.0F).lightLevel(state -> 15));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer
                && level.getBlockEntity(pos) instanceof ForgingFurnacBlockEntity furnace) {
            furnace.refreshResult();
            NetworkHooks.openScreen(serverPlayer, furnace, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof ForgingFurnacBlockEntity furnace) {
            if (!level.isClientSide) {
                for (int slot = 0; slot < ForgingFurnacBlockEntity.INPUT_SLOTS; slot++) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), furnace.removeItemNoUpdate(slot));
                }
            }
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, moving);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof ForgingFurnacBlockEntity furnace
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(furnace) : 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgingFurnacBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type,
                net.mcreator.splendourablazeepoch.registry.ModBlockEntities.FORGING_FURNAC.get(),
                ForgingFurnacBlockEntity::serverTick);
    }
}
