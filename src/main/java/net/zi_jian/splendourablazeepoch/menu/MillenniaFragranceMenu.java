package net.zi_jian.splendourablazeepoch.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zi_jian.splendourablazeepoch.block.entity.ClaypotBlockEntity;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;

public final class MillenniaFragranceMenu extends AbstractContainerMenu {
    private static final String[] INGREDIENTS = {
            "staranise", "sichuanpepper", "fennel", "clove",
            "cinnamonbark", "bayleaf", "nutmeg", "dahurianangelica"
    };
    private static final int[][] POSITIONS = {
            {79, 7}, {52, 16}, {43, 43}, {52, 69},
            {79, 79}, {106, 69}, {115, 43}, {106, 16}, {79, 43}
    };

    private final ClaypotBlockEntity pot;

    public MillenniaFragranceMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, findPot(inventory, data.readBlockPos()));
    }

    public MillenniaFragranceMenu(int id, Inventory inventory, ClaypotBlockEntity pot) {
        super(ModMenus.MILLENNIA_FRAGRANCE.get(), id);
        this.pot = pot;
        pot.startOpen(inventory.player);
        for (int i = 0; i < INGREDIENTS.length; i++) {
            final int ingredientSlot = i;
            addSlot(new Slot(pot, i, POSITIONS[i][0], POSITIONS[i][1]) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(ModItems.byId(INGREDIENTS[ingredientSlot]).get());
                }

                @Override
                public boolean mayPickup(Player player) {
                    return false;
                }
            });
        }
        addSlot(new Slot(pot, 8, POSITIONS[8][0], POSITIONS[8][1]) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + (row + 1) * 9,
                        8 + column * 18, 108 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 166));
        }
    }

    private static ClaypotBlockEntity findPot(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof ClaypotBlockEntity pot) {
            return pot;
        }
        throw new IllegalStateException("No claypot at " + pos);
    }

    @Override
    public boolean stillValid(Player player) {
        return pot.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem() || !slot.mayPickup(player)) {
            return ItemStack.EMPTY;
        }
        ItemStack inSlot = slot.getItem();
        ItemStack copy = inSlot.copy();
        int potEnd = ClaypotBlockEntity.SLOT_COUNT;
        if (index < potEnd) {
            if (!moveItemStackTo(inSlot, potEnd, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(inSlot, 0, 8, false)) {
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
        pot.stopOpen(player);
    }
}
