package net.zi_jian.splendourablazeepoch.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.recipe.PrintTableRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;

public final class PrintTableRecipeCategory
        implements IRecipeCategory<PrintTableRecipe> {

    public static final ResourceLocation UID =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "printinguui"
            );

    public static final RecipeType<PrintTableRecipe> TYPE =
            new RecipeType<>(
                    UID,
                    PrintTableRecipe.class
            );

    private static final ResourceLocation BACKGROUND =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "textures/screens/printing1.png"
            );

    private static final int[][] INPUT_POSITIONS = {
            {17, 15},
            {37, 15},
            {17, 35},
            {37, 35},
            {17, 55},
            {37, 55},
            {74, 23},
            {66, 54},
            {85, 54}
    };

    private static final int OUTPUT_X = 142;
    private static final int OUTPUT_Y = 24;

    private final IDrawable background;
    private final IDrawable icon;

    public PrintTableRecipeCategory(
            IGuiHelper helper
    ) {
        background =
                helper.createDrawable(
                        BACKGROUND,
                        0,
                        0,
                        176,
                        80
                );

        icon =
                helper.createDrawableIngredient(
                        VanillaTypes.ITEM_STACK,
                        new ItemStack(
                                ModBlocks.PRINT_TABLE.get()
                        )
                );
    }

    @Override
    public RecipeType<PrintTableRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(
                "block.splendour_ablaze_epoch.printtable"
        );
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(
            IRecipeLayoutBuilder builder,
            PrintTableRecipe recipe,
            IFocusGroup focuses
    ) {
        for (int i = 0; i < 9; i++) {
            if (recipe.getDisplayStacks(i).isEmpty()) {
                continue;
            }

            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            INPUT_POSITIONS[i][0],
                            INPUT_POSITIONS[i][1]
                    )
                    .addItemStacks(
                            recipe.getDisplayStacks(i)
                    );
        }

        builder.addSlot(
                        RecipeIngredientRole.OUTPUT,
                        OUTPUT_X,
                        OUTPUT_Y
                )
                .addItemStack(
                        recipe.getResult()
                );
    }
}