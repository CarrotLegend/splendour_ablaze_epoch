package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.zi_jian.splendourablazeepoch.recipe.PrintTableRecipe;

public final class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>>
            RECIPE_SERIALIZERS =
            DeferredRegister.create(
                    ForgeRegistries.RECIPE_SERIALIZERS,
                    SplendourAblazeEpochMod.MOD_ID
            );

    public static final RegistryObject<
            RecipeSerializer<ForgingFurnaceRecipe>>
            FORGING_FURNACE_SERIALIZER =
            RECIPE_SERIALIZERS.register(
                    "forgingfurnacejei",
                    ForgingFurnaceRecipe.Serializer::new
            );

    public static final RegistryObject<
            RecipeSerializer<PrintTableRecipe>>
            PRINT_TABLE_SERIALIZER =
            RECIPE_SERIALIZERS.register(
                    "printinguui",
                    PrintTableRecipe.Serializer::new
            );

    public static final RecipeType<ForgingFurnaceRecipe>
            FORGING_FURNACE_TYPE =
            new RecipeType<>() {
                @Override
                public String toString() {
                    return new ResourceLocation(
                            SplendourAblazeEpochMod.MOD_ID,
                            "forgingfurnacejei"
                    ).toString();
                }
            };

    public static final RecipeType<PrintTableRecipe>
            PRINT_TABLE_TYPE =
            new RecipeType<>() {
                @Override
                public String toString() {
                    return new ResourceLocation(
                            SplendourAblazeEpochMod.MOD_ID,
                            "printinguui"
                    ).toString();
                }
            };

    private ModRecipes() {
    }
}
