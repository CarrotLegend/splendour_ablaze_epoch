package net.zi_jian.splendourablazeepoch.block.entity;

import java.util.List;

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
import net.minecraft.world.item.crafting.Ingredient;
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
import net.zi_jian.splendourablazeepoch.recipe.PrintTableRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

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
        ItemStack inserted =
                stack.copy();

        int maxStackSize =
                Math.min(
                        getMaxStackSize(),
                        inserted.getMaxStackSize()
                );

        if (inserted.getCount() > maxStackSize) {
            inserted.setCount(
                    maxStackSize
            );
        }

        items.set(
                slot,
                inserted
        );

        setChanged();
    }

    @Override
    public boolean canPlaceItem(
            int slot,
            ItemStack stack
    ) {
        if (level == null
                || stack.isEmpty()
                || slot < 0
                || slot >= INPUT_SLOTS) {
            return false;
        }

        List<PrintTableRecipe> recipes =
                level.getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipes.PRINT_TABLE_TYPE
                        );

        for (PrintTableRecipe recipe : recipes) {
            Ingredient ingredient =
                    recipe.input(slot);

            if (ingredient != Ingredient.EMPTY
                    && ingredient.test(stack)) {
                return true;
            }
        }

        return false;
    }

    public int findBestInputSlot(
            ItemStack stack
    ) {
        if (level == null
                || stack.isEmpty()) {
            return -1;
        }

        List<PrintTableRecipe> recipes =
                level.getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipes.PRINT_TABLE_TYPE
                        );

        int bestSlot = -1;
        int bestScore = Integer.MIN_VALUE;

        for (PrintTableRecipe recipe : recipes) {
            for (int targetSlot = 0;
                 targetSlot < INPUT_SLOTS;
                 targetSlot++) {

                Ingredient targetIngredient =
                        recipe.input(targetSlot);

                if (targetIngredient == Ingredient.EMPTY
                        || !targetIngredient.test(stack)) {
                    continue;
                }

                if (!canAcceptStack(
                        targetSlot,
                        stack
                )) {
                    continue;
                }

                int score =
                        getRecipeCompatibilityScore(
                                recipe,
                                targetSlot
                        );

                if (score < 0) {
                    continue;
                }

                ItemStack existing =
                        items.get(targetSlot);

                if (!existing.isEmpty()
                        && ItemStack.isSameItemSameTags(
                                existing,
                                stack
                        )) {
                    score += 1000;
                }

                if (score > bestScore) {
                    bestScore = score;
                    bestSlot = targetSlot;
                }
            }
        }

        return bestSlot;
    }

    public boolean insertIntoBestSlot(
            ItemStack source
    ) {
        if (source.isEmpty()) {
            return false;
        }

        int targetSlot =
                findBestInputSlot(
                        source
                );

        if (targetSlot < 0) {
            return false;
        }

        ItemStack existing =
                items.get(targetSlot);

        if (existing.isEmpty()) {
            int maxStackSize =
                    Math.min(
                            source.getMaxStackSize(),
                            getMaxStackSize()
                    );

            int transfer =
                    Math.min(
                            source.getCount(),
                            maxStackSize
                    );

            if (transfer <= 0) {
                return false;
            }

            ItemStack inserted =
                    source.copy();

            inserted.setCount(
                    transfer
            );

            items.set(
                    targetSlot,
                    inserted
            );

            source.shrink(
                    transfer
            );

            setChanged();

            return true;
        }

        if (!ItemStack.isSameItemSameTags(
                existing,
                source
        )) {
            return false;
        }

        int maxStackSize =
                Math.min(
                        existing.getMaxStackSize(),
                        getMaxStackSize()
                );

        int space =
                maxStackSize
                        - existing.getCount();

        if (space <= 0) {
            return false;
        }

        int transfer =
                Math.min(
                        source.getCount(),
                        space
                );

        if (transfer <= 0) {
            return false;
        }

        existing.grow(
                transfer
        );

        source.shrink(
                transfer
        );

        setChanged();

        return true;
    }

    private boolean canAcceptStack(
            int slot,
            ItemStack stack
    ) {
        if (!canPlaceItem(
                slot,
                stack
        )) {
            return false;
        }

        ItemStack existing =
                items.get(slot);

        if (existing.isEmpty()) {
            return true;
        }

        if (!ItemStack.isSameItemSameTags(
                existing,
                stack
        )) {
            return false;
        }

        int maxStackSize =
                Math.min(
                        existing.getMaxStackSize(),
                        getMaxStackSize()
                );

        return existing.getCount()
                < maxStackSize;
    }

    private int getRecipeCompatibilityScore(
            PrintTableRecipe recipe,
            int targetSlot
    ) {
        int score = 0;

        for (int slot = 0;
             slot < INPUT_SLOTS;
             slot++) {

            if (slot == targetSlot) {
                continue;
            }

            ItemStack existing =
                    items.get(slot);

            if (existing.isEmpty()) {
                continue;
            }

            Ingredient expected =
                    recipe.input(slot);

            if (expected == Ingredient.EMPTY
                    || !expected.test(existing)) {
                return -1;
            }

            score++;
        }

        return score;
    }

    @Override
    public void clearContent() {
        for (int i = 0;
             i < items.size();
             i++) {

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
        return level != null
                && level.getBlockEntity(
                        worldPosition
                ) == this
                && player.distanceToSqr(
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
                && capability
                == ForgeCapabilities.ITEM_HANDLER) {

            return (
                    side == null
                            ? unsidedHandler
                            : sidedHandlers[
                                    side.ordinal()
                            ]
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