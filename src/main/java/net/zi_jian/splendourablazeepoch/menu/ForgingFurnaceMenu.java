package net.zi_jian.splendourablazeepoch.menu;

import net.zi_jian.splendourablazeepoch.block.entity.ForgingFurnacBlockEntity;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class ForgingFurnaceMenu extends AbstractContainerMenu {
    private static final int MACHINE_SLOT_COUNT = 5;
    private static final int PLAYER_INVENTORY_START = MACHINE_SLOT_COUNT;
    private static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_START + 27;
    private static final int PLAYER_END = PLAYER_HOTBAR_START + 9;

    private final ForgingFurnacBlockEntity furnace;
    private final ContainerLevelAccess access;

    public ForgingFurnaceMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, findFurnace(inventory, data.readBlockPos()));
    }

    public ForgingFurnaceMenu(int containerId, Inventory inventory, ForgingFurnacBlockEntity furnace) {
        super(ModMenus.FORGING_FURNACE_GUI.get(), containerId);
        this.furnace = furnace;
        this.access = ContainerLevelAccess.create(inventory.player.level(), furnace.getBlockPos());
        furnace.startOpen(inventory.player);

        addSlot(new Slot(furnace, 0, 25, 17));
        addSlot(new Slot(furnace, 1, 25, 35));
        addSlot(new Slot(furnace, 2, 25, 53));
        addSlot(new Slot(furnace, 3, 69, 14));
        addSlot(new Slot(furnace, ForgingFurnacBlockEntity.OUTPUT_SLOT, 124, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + (row + 1) * 9, 8 + column * 18, 84 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }
    }

    private static ForgingFurnacBlockEntity findFurnace(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof ForgingFurnacBlockEntity furnace) return furnace;
        throw new IllegalStateException("Forging furnace menu opened without a forging furnace block entity at " + pos);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.FORGING_FURNAC.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index < 0 || index >= slots.size()) return ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        if (index == ForgingFurnacBlockEntity.OUTPUT_SLOT) {
            ItemStack preview = furnace.getPreview();
            if (preview.isEmpty()) return ItemStack.EMPTY;
            ItemStack moving = preview.copy();
            if (!moveItemStackTo(moving, PLAYER_INVENTORY_START, PLAYER_END, true) || !moving.isEmpty()) {
                return ItemStack.EMPTY;
            }
            ItemStack crafted = furnace.takeResult(preview.getCount());
            if (crafted.isEmpty()) return ItemStack.EMPTY;
            slot.onTake(player, crafted);
            return crafted.copy();
        }

        ItemStack source = slot.getItem();
        ItemStack original = source.copy();
        if (index < MACHINE_SLOT_COUNT) {
            if (!moveItemStackTo(source, PLAYER_INVENTORY_START, PLAYER_END, true)) return ItemStack.EMPTY;
        } else if (!moveItemStackTo(source, 0, ForgingFurnacBlockEntity.INPUT_SLOTS, false)) {
            if (index < PLAYER_HOTBAR_START) {
                if (!moveItemStackTo(source, PLAYER_HOTBAR_START, PLAYER_END, false)) return ItemStack.EMPTY;
            } else if (!moveItemStackTo(source, PLAYER_INVENTORY_START, PLAYER_HOTBAR_START, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (source.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        if (source.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, source);
        return original;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        furnace.stopOpen(player);
    }
}
