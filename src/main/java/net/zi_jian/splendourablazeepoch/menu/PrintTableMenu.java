package net.zi_jian.splendourablazeepoch.menu;

import java.util.Optional;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.block.entity.PrintTableBlockEntity;
import net.zi_jian.splendourablazeepoch.recipe.PrintTableRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

public final class PrintTableMenu
        extends AbstractContainerMenu {

    private static final int INPUT_COUNT =
            PrintTableRecipe.INPUT_COUNT;

    private static final int RESULT_INDEX = 9;
    private static final int PLAYER_INVENTORY_START = 10;
    private static final int PLAYER_HOTBAR_START = 37;
    private static final int PLAYER_END = 46;

    private static final int[][] INPUT_POSITIONS = {
            {15, 17},
            {15, 35},
            {15, 54},
            {34, 17},
            {34, 35},
            {34, 54},
            {69, 25},
            {61, 53},
            {79, 53}
    };

    private static final int RESULT_X = 133;
    private static final int RESULT_Y = 26;

    private static final ResourceLocation LIFES_WORK_ADVANCEMENT =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "lifeswork"
            );

    private final PrintTableBlockEntity table;

    private final ResultContainer result =
            new ResultContainer();

    private final ContainerLevelAccess access;
    private final Player player;

    public PrintTableMenu(
            int containerId,
            Inventory inventory,
            FriendlyByteBuf data
    ) {
        this(
                containerId,
                inventory,
                findTable(
                        inventory,
                        data.readBlockPos()
                )
        );
    }

    public PrintTableMenu(
            int containerId,
            Inventory inventory,
            PrintTableBlockEntity table
    ) {
        super(
                ModMenus.PRINT_TABLE_GUI.get(),
                containerId
        );

        this.table = table;
        this.player = inventory.player;

        this.access =
                ContainerLevelAccess.create(
                        inventory.player.level(),
                        table.getBlockPos()
                );

        table.startOpen(
                inventory.player
        );

        for (int i = 0;
             i < INPUT_COUNT;
             i++) {

            int slotIndex = i;

            addSlot(
                    new Slot(
                            table,
                            slotIndex,
                            INPUT_POSITIONS[slotIndex][0],
                            INPUT_POSITIONS[slotIndex][1]
                    ) {
                        @Override
                        public boolean mayPlace(
                                ItemStack stack
                        ) {
                            return table.canPlaceItem(
                                    slotIndex,
                                    stack
                            );
                        }
                    }
            );
        }

        addSlot(
                new Slot(
                        result,
                        0,
                        RESULT_X,
                        RESULT_Y
                ) {
                    @Override
                    public boolean mayPlace(
                            ItemStack stack
                    ) {
                        return false;
                    }

                    @Override
                    public boolean mayPickup(
                            Player player
                    ) {
                        if (getItem().isEmpty()) {
                            return false;
                        }

                        SimpleContainer input =
                                PrintTableMenu.this
                                        .createInputContainer();

                        return PrintTableMenu.this
                                .findRecipe(input)
                                .isPresent();
                    }

                    @Override
                    public void onTake(
                            Player player,
                            ItemStack stack
                    ) {
                        PrintTableMenu.this
                                .finishPrint(
                                        player
                                );

                        super.onTake(
                                player,
                                stack
                        );
                    }
                }
        );

        for (int row = 0;
             row < 3;
             row++) {

            for (int column = 0;
                 column < 9;
                 column++) {

                addSlot(
                        new Slot(
                                inventory,
                                column
                                        + (row + 1) * 9,
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

        updateResult();
    }

    private static PrintTableBlockEntity findTable(
            Inventory inventory,
            BlockPos pos
    ) {
        if (inventory.player
                .level()
                .getBlockEntity(pos)
                instanceof PrintTableBlockEntity table) {
            return table;
        }

        throw new IllegalStateException(
                "Print table block entity not found at "
                        + pos
        );
    }

    private SimpleContainer createInputContainer() {
        SimpleContainer container =
                new SimpleContainer(
                        INPUT_COUNT
                );

        for (int i = 0;
             i < INPUT_COUNT;
             i++) {

            container.setItem(
                    i,
                    table.getItem(i)
                            .copy()
            );
        }

        return container;
    }

    private Optional<PrintTableRecipe> findRecipe(
            SimpleContainer input
    ) {
        return player.level()
                .getRecipeManager()
                .getRecipeFor(
                        ModRecipes.PRINT_TABLE_TYPE,
                        input,
                        player.level()
                );
    }

    private void updateResult() {
        if (player.level()
                .isClientSide) {
            return;
        }

        SimpleContainer input =
                createInputContainer();

        Optional<PrintTableRecipe> recipe =
                findRecipe(
                        input
                );

        ItemStack output =
                recipe.map(
                                value ->
                                        value.assemble(
                                                input,
                                                player.level()
                                                        .registryAccess()
                                        )
                        )
                        .orElse(
                                ItemStack.EMPTY
                        );

        result.setItem(
                0,
                output
        );

        recipe.ifPresent(
                result::setRecipeUsed
        );
    }

    private void finishPrint(
            Player craftingPlayer
    ) {
        if (craftingPlayer.level()
                .isClientSide) {
            return;
        }

        SimpleContainer input =
                createInputContainer();

        Optional<PrintTableRecipe> recipe =
                findRecipe(
                        input
                );

        if (recipe.isEmpty()) {
            result.setItem(
                    0,
                    ItemStack.EMPTY
            );

            return;
        }

        RandomSource random =
                craftingPlayer.getRandom();

        consumeMovableTypePair(
                0,
                1,
                random
        );

        consumeMovableTypePair(
                2,
                3,
                random
        );

        consumeMovableTypePair(
                4,
                5,
                random
        );

        shrinkInput(
                PrintTableRecipe.BOOK_SLOT
        );

        shrinkInput(
                PrintTableRecipe.INK_SLOT
        );

        table.setChanged();

        grantLifesWork(
                craftingPlayer
        );

        updateResult();
    }

    private void consumeMovableTypePair(
            int firstSlot,
            int secondSlot,
            RandomSource random
    ) {
        if (random.nextInt(3) == 2) {
            shrinkInput(
                    firstSlot
            );

            return;
        }

        if (random.nextInt(3) == 1) {
            shrinkInput(
                    secondSlot
            );
        }
    }

    private void shrinkInput(
            int slot
    ) {
        ItemStack stack =
                table.getItem(
                        slot
                );

        if (stack.isEmpty()) {
            return;
        }

        table.removeItem(
                slot,
                1
        );
    }

    private static void grantLifesWork(
            Player player
    ) {
        if (!(player
                instanceof ServerPlayer serverPlayer)) {
            return;
        }

        MinecraftServer server =
                serverPlayer.getServer();

        if (server == null) {
            return;
        }

        Advancement advancement =
                server.getAdvancements()
                        .getAdvancement(
                                LIFES_WORK_ADVANCEMENT
                        );

        if (advancement == null) {
            return;
        }

        AdvancementProgress progress =
                serverPlayer.getAdvancements()
                        .getOrStartProgress(
                                advancement
                        );

        if (progress.isDone()) {
            return;
        }

        for (String criterion
                : progress.getRemainingCriteria()) {

            serverPlayer.getAdvancements()
                    .award(
                            advancement,
                            criterion
                    );
        }
    }

    @Override
    public void slotsChanged(
            Container container
    ) {
        super.slotsChanged(
                container
        );

        updateResult();
    }

    @Override
    public void broadcastChanges() {
        updateResult();

        super.broadcastChanges();
    }

    @Override
    public boolean stillValid(
            Player player
    ) {
        return stillValid(
                access,
                player,
                ModBlocks.PRINT_TABLE.get()
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

        ItemStack stack =
                sourceSlot.getItem();

        ItemStack original =
                stack.copy();

        if (index == RESULT_INDEX) {
            if (!moveItemStackTo(
                    stack,
                    PLAYER_INVENTORY_START,
                    PLAYER_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onQuickCraft(
                    stack,
                    original
            );
        } else if (index < INPUT_COUNT) {
            if (!moveItemStackTo(
                    stack,
                    PLAYER_INVENTORY_START,
                    PLAYER_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else if (index >= PLAYER_INVENTORY_START) {
            int targetSlot =
                    table.findBestInputSlot(
                            stack
                    );

            boolean movedToTable =
                    targetSlot >= 0
                            && moveItemStackTo(
                                    stack,
                                    targetSlot,
                                    targetSlot + 1,
                                    false
                            );

            if (!movedToTable) {
                if (index < PLAYER_HOTBAR_START) {
                    if (!moveItemStackTo(
                            stack,
                            PLAYER_HOTBAR_START,
                            PLAYER_END,
                            false
                    )) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!moveItemStackTo(
                            stack,
                            PLAYER_INVENTORY_START,
                            PLAYER_HOTBAR_START,
                            false
                    )) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (stack.isEmpty()) {
            sourceSlot.setByPlayer(
                    ItemStack.EMPTY
            );
        } else {
            sourceSlot.setChanged();
        }

        if (stack.getCount()
                == original.getCount()) {
            return ItemStack.EMPTY;
        }

        sourceSlot.onTake(
                player,
                stack
        );

        return original;
    }

    @Override
    public void removed(
            Player player
    ) {
        super.removed(
                player
        );

        table.stopOpen(
                player
        );
    }
}