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
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;

public final class LegacyChestBlockEntity
        extends RandomizableContainerBlockEntity
        implements WorldlyContainer {

    public static final int SLOT_COUNT = 27;

    private static final int[] SLOTS = createSlots();

    private final String registryName;

    private NonNullList<ItemStack> items =
            NonNullList.withSize(
                    SLOT_COUNT,
                    ItemStack.EMPTY
            );

    private LazyOptional<IItemHandler> itemHandler =
            LazyOptional.of(
                    () -> new InvWrapper(this)
            );

    private LegacyChestBlockEntity(
            String registryName,
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state
    ) {
        super(
                type,
                pos,
                state
        );

        this.registryName = registryName;
    }

    public static LegacyChestBlockEntity create(
            String registryName,
            BlockPos pos,
            BlockState state
    ) {
        return new LegacyChestBlockEntity(
                registryName,
                ModBlockEntities.get(registryName),
                pos,
                state
        );
    }

    private static int[] createSlots() {
        int[] slots =
                new int[SLOT_COUNT];

        for (int i = 0; i < SLOT_COUNT; i++) {
            slots[i] = i;
        }

        return slots;
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(
                "block."
                        + SplendourAblazeEpochMod.MOD_ID
                        + "."
                        + registryName
        );
    }

    @Override
    protected AbstractContainerMenu createMenu(
            int containerId,
            Inventory inventory
    ) {
        return ChestMenu.threeRows(
                containerId,
                inventory,
                this
        );
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(
            NonNullList<ItemStack> items
    ) {
        this.items = items;
    }

    @Override
    public boolean isEmpty() {
        unpackLootTable(null);

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
        unpackLootTable(null);

        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(
            int slot,
            int amount
    ) {
        unpackLootTable(null);

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
        unpackLootTable(null);

        return ContainerHelper.takeItem(
                items,
                slot
        );
    }

    @Override
    public void setItem(
            int slot,
            ItemStack stack
    ) {
        unpackLootTable(null);

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
    public void clearContent() {
        unpackLootTable(null);

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

        if (level.getBlockEntity(worldPosition) != this) {
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
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(
            int slot,
            ItemStack stack,
            Direction direction
    ) {
        return true;
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

        if (!tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(
                    tag,
                    items
            );
        }
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {
        super.saveAdditional(tag);

        if (!trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(
                    tag,
                    items
            );
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(
            Capability<T> capability,
            @Nullable Direction side
    ) {
        if (!remove
                && capability == ForgeCapabilities.ITEM_HANDLER) {

            return itemHandler.cast();
        }

        return super.getCapability(
                capability,
                side
        );
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();

        itemHandler =
                LazyOptional.of(
                        () -> new InvWrapper(this)
                );
    }
}