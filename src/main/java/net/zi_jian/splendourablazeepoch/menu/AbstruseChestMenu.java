package net.zi_jian.splendourablazeepoch.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zi_jian.splendourablazeepoch.block.entity.LegacyChestBlockEntity;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;

public final class AbstruseChestMenu extends AbstractContainerMenu {
    private static final int[][] CHEST_POSITIONS = {
            {79, 26}, {97, 44}, {43, 26}, {151, 62}, {25, 44}, {7, 26},
            {7, 8}, {97, 62}, {79, 62}, {151, 44}, {133, 26}, {97, 8},
            {79, 44}, {43, 62}, {7, 62}, {97, 26}, {115, 44}, {151, 8},
            {25, 8}, {25, 62}, {61, 44}, {61, 26}, {61, 8}, {115, 8},
            {133, 62}, {133, 44}, {61, 62}, {25, 26}, {43, 44}, {43, 8},
            {7, 44}, {115, 62}, {115, 26}, {79, 8}, {151, 26}, {133, 8}
    };

    private final LegacyChestBlockEntity chest;

    public AbstruseChestMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, findChest(inventory, data.readBlockPos()));
    }

    public AbstruseChestMenu(int id, Inventory inventory, LegacyChestBlockEntity chest) {
        super(ModMenus.ABSTRUSE_CHEST_GUI.get(), id);
        if (chest.getContainerSize() != LegacyChestBlockEntity.SLOT_COUNT) {
            throw new IllegalArgumentException("Expected a 36-slot storage block");
        }
        this.chest = chest;
        chest.startOpen(inventory.player);
        for (int i = 0; i < CHEST_POSITIONS.length; i++) {
            addSlot(new Slot(chest, i, CHEST_POSITIONS[i][0], CHEST_POSITIONS[i][1]));
        }
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + (row + 1) * 9,
                        8 + column * 18, 84 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }
    }

    private static LegacyChestBlockEntity findChest(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof LegacyChestBlockEntity chest
                && chest.getContainerSize() == LegacyChestBlockEntity.SLOT_COUNT) {
            return chest;
        }
        throw new IllegalStateException("No storage block at " + pos);
    }

    @Override
    public boolean stillValid(Player player) {
        return chest.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack inSlot = slot.getItem();
        ItemStack copy = inSlot.copy();
        int chestEnd = LegacyChestBlockEntity.SLOT_COUNT;
        if (index < chestEnd) {
            if (!moveItemStackTo(inSlot, chestEnd, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(inSlot, 0, chestEnd, false)) {
            return ItemStack.EMPTY;
        }
        if (inSlot.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        slot.onTake(player, inSlot);
        return copy;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        chest.stopOpen(player);
    }
}
