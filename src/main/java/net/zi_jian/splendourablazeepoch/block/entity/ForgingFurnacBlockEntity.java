package net.zi_jian.splendourablazeepoch.block.entity;

import java.util.Optional;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Nullable;

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
import net.minecraft.world.inventory.ContainerData;
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
import net.zi_jian.splendourablazeepoch.menu.ForgingFurnaceMenu;
import net.zi_jian.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

public final class ForgingFurnacBlockEntity
        extends BaseContainerBlockEntity
        implements WorldlyContainer {

    public static final int INPUT_SLOTS = 4;
    public static final int OUTPUT_SLOT = 4;
    public static final int SLOT_COUNT = 5;
    public static final int COOK_TIME = 220;

    private NonNullList<ItemStack> items =
            NonNullList.withSize(
                    SLOT_COUNT,
                    ItemStack.EMPTY
            );

    private int cookProgress;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> cookProgress;
                case 1 -> COOK_TIME;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                cookProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    private final LazyOptional<? extends IItemHandler>[] sidedHandlers =
            SidedInvWrapper.create(
                    this,
                    Direction.values()
            );

    private final LazyOptional<IItemHandler> unsidedHandler =
            LazyOptional.of(
                    () -> new InvWrapper(this)
            );

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
        Optional<ForgingFurnaceRecipe> recipe =
                furnace.findRecipe();

        if (recipe.isPresent()
                && furnace.canOutput(recipe.get())) {

            furnace.cookProgress++;

            if (furnace.cookProgress >= COOK_TIME) {
                furnace.cookProgress = 0;
                furnace.craft(recipe.get());
            }

            furnace.setChanged();
        } else if (furnace.cookProgress != 0) {
            furnace.cookProgress = 0;
            furnace.setChanged();
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
        return new ForgingFurnaceMenu(
                containerId,
                inventory,
                this
        );
    }

    public ContainerData getDataAccess() {
        return data;
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
    public void setItem(
            int slot,
            ItemStack stack
    ) {
        ItemStack inserted = stack.copy();

        if (inserted.getCount() > getMaxStackSize()) {
            inserted.setCount(
                    getMaxStackSize()
            );
        }

        items.set(
                slot,
                inserted
        );

        if (slot < INPUT_SLOTS) {
            cookProgress = 0;
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
    public ItemStack removeItem(
            int slot,
            int amount
    ) {
        ItemStack removed = ContainerHelper.removeItem(
                items,
                slot,
                amount
        );

        if (!removed.isEmpty()) {
            if (slot < INPUT_SLOTS) {
                cookProgress = 0;
            }

            setChanged();
        }

        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(
            int slot
    ) {
        ItemStack removed = ContainerHelper.takeItem(
                items,
                slot
        );

        if (slot < INPUT_SLOTS) {
            cookProgress = 0;
        }

        return removed;
    }

    @Override
    public void clearContent() {
        items.clear();
        cookProgress = 0;
        setChanged();
    }

    private Optional<ForgingFurnaceRecipe> findRecipe() {
        if (level == null) {
            return Optional.empty();
        }

        SimpleContainer container =
                createInputContainer();

        return level.getRecipeManager()
                .getRecipeFor(
                        ModRecipes.FORGING_FURNACE_TYPE,
                        container,
                        level
                );
    }

    private boolean canOutput(
            ForgingFurnaceRecipe recipe
    ) {
        if (level == null) {
            return false;
        }

        ItemStack result =
                recipe.getResultItem(
                        level.registryAccess()
                );

        ItemStack output =
                items.get(
                        OUTPUT_SLOT
                );

        if (output.isEmpty()) {
            return result.getCount()
                    <= result.getMaxStackSize();
        }

        return ItemStack.isSameItemSameTags(
                output,
                result
        ) && output.getCount() + result.getCount()
                <= output.getMaxStackSize();
    }

    private void craft(
            ForgingFurnaceRecipe recipe
    ) {
        if (level == null
                || !recipe.matches(
                        createInputContainer(),
                        level
                )) {
            return;
        }

        for (int i = 0; i < INPUT_SLOTS; i++) {
            ForgingFurnaceRecipe.Input input =
                    recipe.input(i);

            if (input.isEmpty()) {
                continue;
            }

            ItemStack stack =
                    items.get(i);

            stack.shrink(
                    input.consumeCount()
            );

            if (stack.isEmpty()) {
                items.set(
                        i,
                        ItemStack.EMPTY
                );
            }
        }

        ItemStack result =
                recipe.getResultItem(
                        level.registryAccess()
                );

        ItemStack output =
                items.get(
                        OUTPUT_SLOT
                );

        if (output.isEmpty()) {
            items.set(
                    OUTPUT_SLOT,
                    result.copy()
            );
        } else {
            output.grow(
                    result.getCount()
            );
        }

        setChanged();
    }

    private SimpleContainer createInputContainer() {
        SimpleContainer container =
                new SimpleContainer(
                        INPUT_SLOTS
                );

        for (int i = 0; i < INPUT_SLOTS; i++) {
            container.setItem(
                    i,
                    items.get(i).copy()
            );
        }

        return container;
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
        return IntStream.range(
                0,
                SLOT_COUNT
        ).toArray();
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
        return slot == OUTPUT_SLOT;
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

        cookProgress =
                tag.getInt(
                        "CookProgress"
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

        tag.putInt(
                "CookProgress",
                cookProgress
        );

        super.saveAdditional(tag);
    }
}