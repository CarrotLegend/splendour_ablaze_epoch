package net.zi_jian.splendourablazeepoch.recipe.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public final class LuckycraftBrewingRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        return (input.is(Items.POTION)
                || input.is(Items.SPLASH_POTION)
                || input.is(Items.LINGERING_POTION))
                && PotionUtils.getPotion(input) == Potions.AWKWARD;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.is(ModItems.byId("rawkoifish").get());
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!isInput(input) || !isIngredient(ingredient)) {
            return ItemStack.EMPTY;
        }

        // The original creates a fresh bottle, discarding any other input NBT.
        return PotionUtils.setPotion(new ItemStack(input.getItem()), Potions.LUCK);
    }
}
