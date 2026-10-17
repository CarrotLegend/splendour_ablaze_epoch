package net.zi_jian.splendourablazeepoch.block.entity;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.zi_jian.splendourablazeepoch.menu.MillenniaFragranceMenu;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;

public final class ClaypotBlockEntity extends LegacyChestBlockEntity {
    public static final int SLOT_COUNT = 9;
    private static final String OVERFLOW_KEY = "SAELegacyOverflow";

    private final List<OverflowEntry> legacyOverflow = new ArrayList<>();
    private LazyOptional<? extends IItemHandler>[] sidedHandlers;

    public ClaypotBlockEntity(BlockPos pos, BlockState state) {
        super("claypot", ModBlockEntities.CLAY_POT.get(), pos, state, SLOT_COUNT);
        sidedHandlers = SidedInvWrapper.create(this, Direction.values());
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new MillenniaFragranceMenu(id, inventory, this);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot >= 0 && slot < 8;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot == 8;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        if (!isRemoved() && capability == ForgeCapabilities.ITEM_HANDLER) {
            return side == null ? LazyOptional.empty() : sidedHandlers[side.ordinal()].cast();
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : sidedHandlers) {
            handler.invalidate();
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        sidedHandlers = SidedInvWrapper.create(this, Direction.values());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        legacyOverflow.clear();

        ListTag savedOverflow = tag.getList(OVERFLOW_KEY, Tag.TAG_COMPOUND);
        for (int i = 0; i < savedOverflow.size(); i++) {
            CompoundTag entry = savedOverflow.getCompound(i);
            ItemStack stack = ItemStack.of(entry.getCompound("Stack"));
            if (!stack.isEmpty()) {
                legacyOverflow.add(new OverflowEntry(entry.getInt("OriginalSlot"), stack));
            }
        }

        // ContainerHelper ignores indices outside the new nine-slot list.
        ListTag oldItems = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < oldItems.size(); i++) {
            CompoundTag entry = oldItems.getCompound(i);
            int slot = entry.getByte("Slot") & 255;
            if (slot >= SLOT_COUNT) {
                ItemStack stack = ItemStack.of(entry);
                if (!stack.isEmpty()) {
                    legacyOverflow.add(new OverflowEntry(slot, stack));
                }
            }
        }

        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            ItemStack stack = getItems().get(slot);
            if (stack.getCount() > 1) {
                ItemStack excess = stack.copy();
                excess.shrink(1);
                legacyOverflow.add(new OverflowEntry(slot, excess));
                ItemStack single = stack.copy();
                single.setCount(1);
                getItems().set(slot, single);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ListTag overflow = new ListTag();
        for (OverflowEntry entry : legacyOverflow) {
            CompoundTag saved = new CompoundTag();
            saved.putInt("OriginalSlot", entry.slot());
            saved.put("Stack", entry.stack().save(new CompoundTag()));
            overflow.add(saved);
        }
        if (!overflow.isEmpty()) {
            tag.put(OVERFLOW_KEY, overflow);
        }
    }

    public void dropLegacyOverflow(Level level) {
        if (level.isClientSide) {
            return;
        }
        for (OverflowEntry entry : legacyOverflow) {
            Containers.dropItemStack(level,
                    worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(),
                    entry.stack());
        }
        legacyOverflow.clear();
        setChanged();
    }

    private record OverflowEntry(int slot, ItemStack stack) {
    }
}
