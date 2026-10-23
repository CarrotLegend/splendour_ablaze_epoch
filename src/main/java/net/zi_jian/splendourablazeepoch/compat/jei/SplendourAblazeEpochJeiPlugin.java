package net.zi_jian.splendourablazeepoch.compat.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.zi_jian.splendourablazeepoch.recipe.PrintTableRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

@JeiPlugin
public final class SplendourAblazeEpochJeiPlugin
        implements IModPlugin {

    private static final ResourceLocation UID =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "jei_plugin"
            );

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(
            IRecipeCategoryRegistration registration
    ) {
        registration.addRecipeCategories(
                new ForgingFurnaceRecipeCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                ),
                new PrintTableRecipeCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                )
        );
    }

    @Override
    public void registerRecipes(
            IRecipeRegistration registration
    ) {
        List<IJeiBrewingRecipe> brewingRecipes = new ArrayList<>(3);
        for (Item bottle : List.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION)) {
            brewingRecipes.add(registration.getVanillaRecipeFactory().createBrewingRecipe(
                    List.of(new ItemStack(ModItems.byId("rawkoifish").get())),
                    PotionUtils.setPotion(new ItemStack(bottle), Potions.AWKWARD),
                    PotionUtils.setPotion(new ItemStack(bottle), Potions.LUCK)
            ));
        }
        registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);

        if (Minecraft.getInstance().level == null) {
            return;
        }

        List<ForgingFurnaceRecipe>
                forgingRecipes =
                Minecraft.getInstance()
                        .level
                        .getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipes
                                        .FORGING_FURNACE_TYPE
                        );

        registration.addRecipes(
                ForgingFurnaceRecipeCategory.TYPE,
                forgingRecipes
        );

        List<PrintTableRecipe>
                printRecipes =
                Minecraft.getInstance()
                        .level
                        .getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipes.PRINT_TABLE_TYPE
                        );

        registration.addRecipes(
                PrintTableRecipeCategory.TYPE,
                printRecipes
        );
    }

    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {
        registration.addRecipeCatalyst(
                new ItemStack(
                        ModItems.FORGING_FURNAC.get()
                ),
                ForgingFurnaceRecipeCategory.TYPE
        );

        registration.addRecipeCatalyst(
                new ItemStack(
                        ModBlocks.PRINT_TABLE.get()
                ),
                PrintTableRecipeCategory.TYPE
        );
    }
}
