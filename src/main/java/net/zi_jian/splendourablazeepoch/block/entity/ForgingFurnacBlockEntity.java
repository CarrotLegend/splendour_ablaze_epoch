package net.zi_jian.splendourablazeepoch.block.entity;

import net.zi_jian.splendourablazeepoch.menu.ForgingFurnaceMenu;
import net.zi_jian.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.IntStream;

public final class ForgingFurnacBlockEntity
        extends BaseContainerBlockEntity
        implements WorldlyContainer {

    public static final int INPUT_SLOTS = 4;
    public static final int OUTPUT_SLOT = 4;
    public static final int SLOT_COUNT = 5;

    private NonNullList<ItemStack> items =
            NonNullList.withSize(
                    SLOT_COUNT,
                    ItemStack.EMPTY
            );

    private final LazyOptional<? extends IItemHandler>[]
            sidedHandlers =
            SidedInvWrapper.create(
                    this,
                    Direction.values()
            );

    private final LazyOptional<IItemHandler>
            unsidedHandler =
            LazyOptional.of(
                    () -> new InvWrapper(this)
            );

    private boolean recipeDirty = true;

    private boolean changingResult = false;

    public ForgingFurnacBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.FORGING_FURNAC.get(),
                pos,
                state
        );
    }

    public static void serverTick(
            Level level,
            BlockPos pos,
            BlockState state,
            ForgingFurnacBlockEntity furnace
    ) {
        if (furnace.recipeDirty) {
            furnace.refreshResult();
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(
                "block.splendour_ablaze_epoch.forgingfurnac"
        );
    }

    @Override
    protected AbstractContainerMenu createMenu(
            int containerId,
            Inventory inventory
    ) {
        refreshResult();

        return new ForgingFurnaceMenu(
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
        if (slot < 0 || slot >= SLOT_COUNT) {
            return ItemStack.EMPTY;
        }

        return items.get(slot);
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
    public void setItem(
            int slot,
            ItemStack stack
    ) {
        if (slot < 0 || slot >= SLOT_COUNT) {
            return;
        }

        if (slot == OUTPUT_SLOT
                && !changingResult) {

            if (level != null
                    && level.isClientSide) {

                items.set(
                        OUTPUT_SLOT,
                        stack.copy()
                );
            }

            return;
        }

        ItemStack inserted =
                stack.copy();

        if (inserted.getCount()
                > getMaxStackSize()) {

            inserted.setCount(
                    getMaxStackSize()
            );
        }

        items.set(
                slot,
                inserted
        );

        setChanged();

        if (slot < INPUT_SLOTS) {
            inputsChanged();
        }
    }

    @Override
    public ItemStack removeItem(
            int slot,
            int amount
    ) {
        if (slot < 0 || slot >= SLOT_COUNT) {
            return ItemStack.EMPTY;
        }

        if (slot == OUTPUT_SLOT) {
            return takeResult(amount);
        }

        ItemStack removed =
                ContainerHelper.removeItem(
                        items,
                        slot,
                        amount
                );

        if (!removed.isEmpty()) {
            inputsChanged();
        }

        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(
            int slot
    ) {
        if (slot < 0 || slot >= SLOT_COUNT) {
            return ItemStack.EMPTY;
        }

        if (slot == OUTPUT_SLOT) {
            return ItemStack.EMPTY;
        }

        ItemStack removed =
                ContainerHelper.takeItem(
                        items,
                        slot
                );

        if (!removed.isEmpty()) {
            inputsChanged();
        }

        return removed;
    }

    @Override
    public void clearContent() {
        for (int slot = 0;
             slot < INPUT_SLOTS;
             slot++) {

            items.set(
                    slot,
                    ItemStack.EMPTY
            );
        }

        setPreview(
                ItemStack.EMPTY
        );

        inputsChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (!changingResult) {
            recipeDirty = true;
        }
    }

    @Override
    public boolean stillValid(
            Player player
    ) {
        return level != null
                && level.getBlockEntity(worldPosition) == this
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
        return IntStream
                .range(
                        0,
                        SLOT_COUNT
                )
                .toArray();
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
        return true;
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

        for (LazyOptional<? extends IItemHandler>
                handler : sidedHandlers) {

            handler.invalidate();
        }
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

        items.set(
                OUTPUT_SLOT,
                ItemStack.EMPTY
        );

        recipeDirty = true;
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {
        NonNullList<ItemStack> savedItems =
                NonNullList.withSize(
                        SLOT_COUNT,
                        ItemStack.EMPTY
                );

        for (int slot = 0;
             slot < INPUT_SLOTS;
             slot++) {

            savedItems.set(
                    slot,
                    items.get(slot)
            );
        }

        ContainerHelper.saveAllItems(
                tag,
                savedItems
        );

        super.saveAdditional(tag);
    }

    public void refreshResult() {
        if (level == null
                || level.isClientSide
                || changingResult) {

            return;
        }

        Optional<RecipeMatch> match =
                findRecipeMatch();

        ItemStack result =
                match.map(
                        value ->
                                value.recipe()
                                        .getResultItem(
                                                level.registryAccess()
                                        )
                ).orElse(
                        ItemStack.EMPTY
                );

        setPreview(result);

        recipeDirty = false;
    }

    public ItemStack takeResult(
            int requestedAmount
    ) {
        if (level == null
                || level.isClientSide
                || requestedAmount <= 0) {

            return ItemStack.EMPTY;
        }

        Optional<RecipeMatch> matchOptional =
                findRecipeMatch();

        if (matchOptional.isEmpty()) {
            setPreview(
                    ItemStack.EMPTY
            );

            return ItemStack.EMPTY;
        }

        RecipeMatch match =
                matchOptional.get();

        ForgingFurnaceRecipe recipe =
                match.recipe();

        int[] slotMapping =
                match.slotMapping();

        ItemStack result =
                recipe.getResultItem(
                        level.registryAccess()
                );

        if (result.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (requestedAmount
                < result.getCount()) {

            return ItemStack.EMPTY;
        }

        if (!ItemStack.isSameItemSameTags(
                items.get(OUTPUT_SLOT),
                result
        )) {
            refreshResult();
            return ItemStack.EMPTY;
        }

        if (items.get(OUTPUT_SLOT).getCount()
                != result.getCount()) {

            refreshResult();
            return ItemStack.EMPTY;
        }

        changingResult = true;

        try {
            for (int recipeSlot = 0;
                 recipeSlot
                         < ForgingFurnaceRecipe.INPUT_COUNT;
                 recipeSlot++) {

                ForgingFurnaceRecipe.Input input =
                        recipe.input(
                                recipeSlot
                        );

                if (input.isEmpty()) {
                    continue;
                }

                int actualMachineSlot =
                        slotMapping[
                                recipeSlot
                        ];

                if (actualMachineSlot < 0
                        || actualMachineSlot
                        >= INPUT_SLOTS) {

                    continue;
                }

                if (input.consumeCount() <= 0) {
                    continue;
                }

                if (level.random.nextFloat()
                        >= input.consumeChance()) {

                    continue;
                }

                ItemStack actualStack =
                        items.get(
                                actualMachineSlot
                        );

                if (!input.matches(
                        actualStack
                )) {
                    recipeDirty = true;
                    return ItemStack.EMPTY;
                }

                actualStack.shrink(
                        input.consumeCount()
                );

                if (actualStack.isEmpty()) {
                    items.set(
                            actualMachineSlot,
                            ItemStack.EMPTY
                    );
                }
            }

            items.set(
                    OUTPUT_SLOT,
                    ItemStack.EMPTY
            );

        } finally {
            changingResult = false;
        }

        recipeDirty = true;

        setChanged();

        refreshResult();

        return result.copy();
    }

    public ItemStack getPreview() {
        refreshResult();

        return items
                .get(OUTPUT_SLOT)
                .copy();
    }

    private Optional<RecipeMatch> findRecipeMatch() {
        if (level == null) {
            return Optional.empty();
        }

        SimpleContainer input =
                createInputContainer();

        Optional<ForgingFurnaceRecipe> recipeOptional =
                level.getRecipeManager()
                        .getRecipeFor(
                                ModRecipes.FORGING_FURNACE_TYPE,
                                input,
                                level
                        );

        if (recipeOptional.isEmpty()) {
            return Optional.empty();
        }

        ForgingFurnaceRecipe recipe =
                recipeOptional.get();

        Optional<int[]> mappingOptional =
                recipe.findMatchingSlots(
                        input
                );

        if (mappingOptional.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                new RecipeMatch(
                        recipe,
                        mappingOptional.get()
                )
        );
    }

    private SimpleContainer createInputContainer() {
        SimpleContainer input =
                new SimpleContainer(
                        INPUT_SLOTS
                );

        for (int slot = 0;
             slot < INPUT_SLOTS;
             slot++) {

            input.setItem(
                    slot,
                    items.get(slot).copy()
            );
        }

        return input;
    }

    private void inputsChanged() {
        recipeDirty = true;

        setChanged();

        if (level != null
                && !level.isClientSide) {

            refreshResult();
        }
    }

    private void setPreview(
            ItemStack stack
    ) {
        ItemStack old =
                items.get(
                        OUTPUT_SLOT
                );

        if (ItemStack.matches(
                old,
                stack
        )) {
            return;
        }

        changingResult = true;

        try {
            items.set(
                    OUTPUT_SLOT,
                    stack.copy()
            );
        } finally {
            changingResult = false;
        }

        setChanged();

        if (level != null) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    3
            );

            level.updateNeighbourForOutputSignal(
                    worldPosition,
                    getBlockState().getBlock()
            );
        }
    }

    private record RecipeMatch(
            ForgingFurnaceRecipe recipe,
            int[] slotMapping
    ) {
    }
}