package net.zi_jian.splendourablazeepoch.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zi_jian.splendourablazeepoch.block.entity.ForgingFurnacBlockEntity;
import net.zi_jian.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

public final class ForgingFurnaceMenu
        extends AbstractContainerMenu {

    private static final int MACHINE_SLOT_COUNT = 5;
    private static final int PLAYER_INVENTORY_START = 5;
    private static final int PLAYER_HOTBAR_START = 32;
    private static final int PLAYER_END = 41;

    private final ForgingFurnacBlockEntity furnace;
    private final ContainerLevelAccess access;
    private final Inventory playerInventory;

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
        this.playerInventory = inventory;
        this.access =
                ContainerLevelAccess.create(
                        inventory.player.level(),
                        furnace.getBlockPos()
                );

        furnace.startOpen(
                inventory.player
        );

        addInputSlot(
                0,
                25,
                17
        );

        addInputSlot(
                1,
                25,
                35
        );

        addInputSlot(
                2,
                25,
                53
        );

        addInputSlot(
                ForgingFurnacBlockEntity.PYROTEMPER_DUST_SLOT,
                69,
                14
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
            for (int column = 0;
                 column < 9;
                 column++) {

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

        for (int column = 0;
             column < 9;
             column++) {

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

    private void addInputSlot(
            int slotIndex,
            int x,
            int y
    ) {
        addSlot(
                new Slot(
                        furnace,
                        slotIndex,
                        x,
                        y
                ) {
                    @Override
                    public boolean mayPlace(
                            ItemStack stack
                    ) {
                        return furnace.canPlaceItem(
                                slotIndex,
                                stack
                        );
                    }
                }
        );
    }

    private static ForgingFurnacBlockEntity findFurnace(
            Inventory inventory,
            BlockPos pos
    ) {
        if (inventory.player.level()
                .getBlockEntity(pos)
                instanceof ForgingFurnacBlockEntity furnace) {
            return furnace;
        }

        throw new IllegalStateException(
                "Forging furnace block entity not found at "
                        + pos
        );
    }

    public int getScaledProgress(
            int width
    ) {
        int progress =
                furnace.getDataAccess()
                        .get(0);

        int total =
                furnace.getDataAccess()
                        .get(1);

        return total <= 0
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
        if (index < 0
                || index >= slots.size()) {
            return ItemStack.EMPTY;
        }

        Slot sourceSlot =
                slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack =
                sourceSlot.getItem();

        ItemStack original =
                sourceStack.copy();

        if (index < MACHINE_SLOT_COUNT) {
            if (!moveItemStackTo(
                    sourceStack,
                    PLAYER_INVENTORY_START,
                    PLAYER_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            boolean movedToMachine;

            if (sourceStack.is(
                    ModItems.PYROTEMPER_DUST.get()
            )) {
                movedToMachine =
                        moveItemStackTo(
                                sourceStack,
                                ForgingFurnacBlockEntity.PYROTEMPER_DUST_SLOT,
                                ForgingFurnacBlockEntity.PYROTEMPER_DUST_SLOT + 1,
                                false
                        );
            } else {
                movedToMachine =
                        moveToBestMaterialSlot(
                                sourceStack
                        );
            }

            if (!movedToMachine) {
                if (index >= PLAYER_INVENTORY_START
                        && index < PLAYER_HOTBAR_START) {

                    if (!moveItemStackTo(
                            sourceStack,
                            PLAYER_HOTBAR_START,
                            PLAYER_END,
                            false
                    )) {
                        return ItemStack.EMPTY;
                    }

                } else if (index >= PLAYER_HOTBAR_START
                        && index < PLAYER_END) {

                    if (!moveItemStackTo(
                            sourceStack,
                            PLAYER_INVENTORY_START,
                            PLAYER_HOTBAR_START,
                            false
                    )) {
                        return ItemStack.EMPTY;
                    }

                } else {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.setByPlayer(
                    ItemStack.EMPTY
            );
        } else {
            sourceSlot.setChanged();
        }

        if (sourceStack.getCount()
                == original.getCount()) {
            return ItemStack.EMPTY;
        }

        sourceSlot.onTake(
                player,
                sourceStack
        );

        return original;
    }

    private boolean moveToBestMaterialSlot(
            ItemStack stack
    ) {
        List<SlotCandidate> candidates =
                findMaterialSlotCandidates(
                        stack
                );

        if (candidates.isEmpty()) {
            return false;
        }

        candidates.sort(
                Comparator
                        .comparingInt(
                                SlotCandidate::score
                        )
                        .reversed()
                        .thenComparingInt(
                                candidate ->
                                        isMergeTarget(
                                                candidate.slot(),
                                                stack
                                        )
                                                ? 0
                                                : 1
                        )
                        .thenComparingInt(
                                SlotCandidate::slot
                        )
        );

        for (SlotCandidate candidate
                : candidates) {

            if (moveItemStackTo(
                    stack,
                    candidate.slot(),
                    candidate.slot() + 1,
                    false
            )) {
                return true;
            }
        }

        return false;
    }

    private List<SlotCandidate> findMaterialSlotCandidates(
            ItemStack stack
    ) {
        Map<Integer, Integer> bestScores =
                new HashMap<>();

        List<ForgingFurnaceRecipe> recipes =
                playerInventory.player
                        .level()
                        .getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipes.FORGING_FURNACE_TYPE
                        );

        for (ForgingFurnaceRecipe recipe
                : recipes) {

            for (int slot =
                    ForgingFurnacBlockEntity.MATERIAL_SLOT_START;
                 slot <
                         ForgingFurnacBlockEntity.MATERIAL_SLOT_END;
                 slot++) {

                ForgingFurnaceRecipe.Input input =
                        recipe.input(slot);

                if (input.isEmpty()
                        || !input.ingredient()
                                .test(stack)) {
                    continue;
                }

                int score =
                        getRecipeCompatibilityScore(
                                recipe,
                                slot
                        );

                if (score < 0) {
                    continue;
                }

                bestScores.merge(
                        slot,
                        score,
                        Math::max
                );
            }
        }

        List<SlotCandidate> candidates =
                new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry
                : bestScores.entrySet()) {

            candidates.add(
                    new SlotCandidate(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        return candidates;
    }

    private int getRecipeCompatibilityScore(
            ForgingFurnaceRecipe recipe,
            int targetSlot
    ) {
        int score = 0;

        for (int slot = 0;
             slot < ForgingFurnacBlockEntity.INPUT_SLOTS;
             slot++) {

            if (slot == targetSlot) {
                continue;
            }

            ItemStack existing =
                    furnace.getItem(slot);

            if (existing.isEmpty()) {
                continue;
            }

            ForgingFurnaceRecipe.Input expected =
                    recipe.input(slot);

            if (expected.isEmpty()
                    || !expected.ingredient()
                            .test(existing)) {
                return -1;
            }

            score++;
        }

        return score;
    }

    private boolean isMergeTarget(
            int slot,
            ItemStack stack
    ) {
        ItemStack existing =
                furnace.getItem(slot);

        return !existing.isEmpty()
                && ItemStack.isSameItemSameTags(
                        existing,
                        stack
                );
    }

    @Override
    public void removed(
            Player player
    ) {
        super.removed(
                player
        );

        furnace.stopOpen(
                player
        );
    }

    private record SlotCandidate(
            int slot,
            int score
    ) {
    }
}