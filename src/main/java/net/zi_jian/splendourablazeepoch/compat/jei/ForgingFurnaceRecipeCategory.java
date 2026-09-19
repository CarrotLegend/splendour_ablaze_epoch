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
import net.zi_jian.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public final class ForgingFurnaceRecipeCategory
        implements IRecipeCategory<ForgingFurnaceRecipe> {

    public static final ResourceLocation UID =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "forgingfurnacejei"
            );

    public static final RecipeType<ForgingFurnaceRecipe> TYPE =
            new RecipeType<>(
                    UID,
                    ForgingFurnaceRecipe.class
            );

    private static final ResourceLocation BACKGROUND =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "textures/screens/forgeing.png"
            );

    private final IDrawable background;
    private final IDrawable icon;

    public ForgingFurnaceRecipeCategory(
            IGuiHelper helper
    ) {
        background =
                helper.createDrawable(
                        BACKGROUND,
                        0,
                        0,
                        176,
                        90
                );

        icon =
                helper.createDrawableIngredient(
                        VanillaTypes.ITEM_STACK,
                        new ItemStack(
                                ModItems.FORGING_FURNAC.get()
                        )
                );
    }

    @Override
    public RecipeType<ForgingFurnaceRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal(
                "镕铸炉铸造"
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
            ForgingFurnaceRecipe recipe,
            IFocusGroup focuses
    ) {
        if (!recipe.getDisplayStacks(0).isEmpty()) {
            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            31,
                            16
                    )
                    .addItemStacks(
                            recipe.getDisplayStacks(0)
                    );
        }

        if (!recipe.getDisplayStacks(1).isEmpty()) {
            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            31,
                            38
                    )
                    .addItemStacks(
                            recipe.getDisplayStacks(1)
                    );
        }

        if (!recipe.getDisplayStacks(2).isEmpty()) {
            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            31,
                            59
                    )
                    .addItemStacks(
                            recipe.getDisplayStacks(2)
                    );
        }

        if (!recipe.getDisplayStacks(3).isEmpty()) {
            builder.addSlot(
                            RecipeIngredientRole.INPUT,
                            82,
                            14
                    )
                    .addItemStacks(
                            recipe.getDisplayStacks(3)
                    );
        }

        builder.addSlot(
                        RecipeIngredientRole.OUTPUT,
                        146,
                        38
                )
                .addItemStack(
                        recipe.getResultItem(null)
                );
    }
}