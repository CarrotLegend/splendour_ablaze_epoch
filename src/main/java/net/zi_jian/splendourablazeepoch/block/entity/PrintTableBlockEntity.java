package net.zi_jian.splendourablazeepoch.block.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.zi_jian.splendourablazeepoch.menu.PrintTableMenu;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;

public final class PrintTableBlockEntity
        extends BaseContainerBlockEntity
        implements WorldlyContainer {

    public static final int INPUT_SLOTS = 9;
    public static final int SLOT_COUNT = 9;

    private static final int[] SLOTS = {
            0, 1, 2,
            3, 4, 5,
            6, 7, 8
    };

    private NonNullList<ItemStack> items =
            NonNullList.withSize(
                    SLOT_COUNT,
                    ItemStack.EMPTY
            );

    private LazyOptional<? extends IItemHandler>[] sidedHandlers =
            SidedInvWrapper.create(
                    this,
                    Direction.values()
            );

    private LazyOptional<IItemHandler> unsidedHandler =
            LazyOptional.of(
                    () -> new InvWrapper(this)
            );

    private PrintTableBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state
    ) {
        super(
                type,
                pos,
                state
        );
    }

    public static PrintTableBlockEntity create(
            BlockPos pos,
            BlockState state
    ) {
        return new PrintTableBlockEntity(
                ModBlockEntities.get("printtable"),
                pos,
                state
        );
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(
                "block.splendour_ablaze_epoch.printtable"
        );
    }

    @Override
    protected AbstractContainerMenu createMenu(
            int containerId,
            Inventory inventory
    ) {
        return new PrintTableMenu(
                containerId,
                inventory,
                this
        );
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(
            int slot
    ) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(
            int slot,
            int amount
    ) {
        ItemStack removed =
                ContainerHelper.removeItem(
                        items,
                        slot,
                        amount
                );

        if (!removed.isEmpty()) {
            setChanged();
        }

        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(
            int slot
    ) {
        ItemStack removed =
                ContainerHelper.takeItem(
                        items,
                        slot
                );

        if (!removed.isEmpty()) {
            setChanged();
        }

        return removed;
    }

    @Override
    public void setItem(
            int slot,
            ItemStack stack
    ) {
        items.set(
                slot,
                stack
        );

        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(
                    getMaxStackSize()
            );
        }

        setChanged();
    }

    @Override
    public boolean canPlaceItem(
            int slot,
            ItemStack stack
    ) {
        return slot >= 0
                && slot < INPUT_SLOTS;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < items.size(); i++) {
            items.set(
                    i,
                    ItemStack.EMPTY
            );
        }

        setChanged();
    }

    @Override
    public boolean stillValid(
            Player player
    ) {
        if (level == null) {
            return false;
        }

        if (level.getBlockEntity(
                worldPosition
        ) != this) {
            return false;
        }

        return player.distanceToSqr(
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 0.5D,
                worldPosition.getZ() + 0.5D
        ) <= 64.0D;
    }

    @Override
    public int[] getSlotsForFace(
            Direction direction
    ) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(
            int slot,
            ItemStack stack,
            @Nullable Direction direction
    ) {
        return canPlaceItem(
                slot,
                stack
        );
    }

    @Override
    public boolean canTakeItemThroughFace(
            int slot,
            ItemStack stack,
            Direction direction
    ) {
        return slot >= 0
                && slot < INPUT_SLOTS;
    }

    @Override
    public void load(
            CompoundTag tag
    ) {
        super.load(tag);

        items =
                NonNullList.withSize(
                        SLOT_COUNT,
                        ItemStack.EMPTY
                );

        ContainerHelper.loadAllItems(
                tag,
                items
        );
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {
        ContainerHelper.saveAllItems(
                tag,
                items
        );

        super.saveAdditional(tag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(
            Capability<T> capability,
            @Nullable Direction side
    ) {
        if (!remove
                && capability == ForgeCapabilities.ITEM_HANDLER) {

            return (
                    side == null
                            ? unsidedHandler
                            : sidedHandlers[side.ordinal()]
            ).cast();
        }

        return super.getCapability(
                capability,
                side
        );
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        unsidedHandler.invalidate();

        for (LazyOptional<? extends IItemHandler> handler
                : sidedHandlers) {
            handler.invalidate();
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();

        sidedHandlers =
                SidedInvWrapper.create(
                        this,
                        Direction.values()
                );

        unsidedHandler =
                LazyOptional.of(
                        () -> new InvWrapper(this)
                );
    }
}