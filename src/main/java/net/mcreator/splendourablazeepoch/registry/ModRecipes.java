package net.mcreator.splendourablazeepoch.registry;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.recipe.ForgingFurnaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<RecipeSerializer<ForgingFurnaceRecipe>> FORGING_FURNACE_SERIALIZER =
            RECIPE_SERIALIZERS.register("forgingfurnacejei", ForgingFurnaceRecipe.Serializer::new);
    public static final RecipeType<ForgingFurnaceRecipe> FORGING_FURNACE_TYPE = new RecipeType<>() {
        @Override
        public String toString() {
            return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "forgingfurnacejei").toString();
        }
    };

    private ModRecipes() {
    }
}
