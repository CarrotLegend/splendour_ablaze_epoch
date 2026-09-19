package net.zi_jian.splendourablazeepoch.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zi_jian.splendourablazeepoch.block.entity.ForgingFurnacBlockEntity;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;

public final class ForgingFurnaceMenu
        extends AbstractContainerMenu {

    private static final int MACHINE_SLOT_COUNT = 5;
    private static final int PLAYER_INVENTORY_START = 5;
    private static final int PLAYER_HOTBAR_START = 32;
    private static final int PLAYER_END = 41;

    private final ForgingFurnacBlockEntity furnace;
    private final ContainerLevelAccess access;

    public ForgingFurnaceMenu(
            int containerId,
            Inventory inventory,
            FriendlyByteBuf data
    ) {
        this(
                containerId,
                inventory,
                findFurnace(
                        inventory,
                        data.readBlockPos()
                )
        );
    }

    public ForgingFurnaceMenu(
            int containerId,
            Inventory inventory,
            ForgingFurnacBlockEntity furnace
    ) {
        super(
                ModMenus.FORGING_FURNACE_GUI.get(),
                containerId
        );

        this.furnace = furnace;
        this.access = ContainerLevelAccess.create(
                inventory.player.level(),
                furnace.getBlockPos()
        );

        furnace.startOpen(
                inventory.player
        );

        addSlot(
                new Slot(
                        furnace,
                        0,
                        25,
                        17
                )
        );

        addSlot(
                new Slot(
                        furnace,
                        1,
                        25,
                        35
                )
        );

        addSlot(
                new Slot(
                        furnace,
                        2,
                        25,
                        53
                )
        );

        addSlot(
                new Slot(
                        furnace,
                        3,
                        69,
                        14
                )
        );

        addSlot(
                new Slot(
                        furnace,
                        ForgingFurnacBlockEntity.OUTPUT_SLOT,
                        124,
                        35
                ) {
                    @Override
                    public boolean mayPlace(
                            ItemStack stack
                    ) {
                        return false;
                    }
                }
        );

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(
                        new Slot(
                                inventory,
                                column + (row + 1) * 9,
                                8 + column * 18,
                                84 + row * 18
                        )
                );
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(
                    new Slot(
                            inventory,
                            column,
                            8 + column * 18,
                            142
                    )
            );
        }

        addDataSlots(
                furnace.getDataAccess()
        );
    }

    private static ForgingFurnacBlockEntity findFurnace(
            Inventory inventory,
            BlockPos pos
    ) {
        if (inventory.player.level().getBlockEntity(pos)
                instanceof ForgingFurnacBlockEntity furnace) {
            return furnace;
        }

        throw new IllegalStateException(
                "Forging furnace block entity not found at " + pos
        );
    }

    public int getScaledProgress(
            int width
    ) {
        int progress = furnace.getDataAccess().get(0);
        int total = furnace.getDataAccess().get(1);

        return total == 0
                ? 0
                : progress * width / total;
    }

    @Override
    public boolean stillValid(
            Player player
    ) {
        return stillValid(
                access,
                player,
                ModBlocks.FORGING_FURNAC.get()
        );
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int index
    ) {
        if (index < 0 || index >= slots.size()) {
            return ItemStack.EMPTY;
        }

        Slot slot = slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (index < MACHINE_SLOT_COUNT) {
            if (!moveItemStackTo(
                    stack,
                    PLAYER_INVENTORY_START,
                    PLAYER_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(
                stack,
                0,
                ForgingFurnacBlockEntity.INPUT_SLOTS,
                false
        )) {
            if (index < PLAYER_HOTBAR_START) {
                if (!moveItemStackTo(
                        stack,
                        PLAYER_HOTBAR_START,
                        PLAYER_END,
                        false
                )) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(
                    stack,
                    PLAYER_INVENTORY_START,
                    PLAYER_HOTBAR_START,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(
                    ItemStack.EMPTY
            );
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(
                player,
                stack
        );

        return original;
    }

    @Override
    public void removed(
            Player player
    ) {
        super.removed(player);

        furnace.stopOpen(
                player
        );
    }
}