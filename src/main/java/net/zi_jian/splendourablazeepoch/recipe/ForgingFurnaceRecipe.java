package net.zi_jian.splendourablazeepoch.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ForgingFurnaceRecipe implements Recipe<SimpleContainer> {
    public static final int INPUT_COUNT = 4;

    private final ResourceLocation id;
    private final List<Input> inputs;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public ForgingFurnaceRecipe(ResourceLocation id, List<Input> inputs, ItemStack result) {
        if (inputs.size() != INPUT_COUNT) {
            throw new IllegalArgumentException("Forging furnace recipes require exactly four inputs");
        }
        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.ingredients = NonNullList.withSize(INPUT_COUNT, Ingredient.EMPTY);
        for (int i = 0; i < INPUT_COUNT; i++) {
            this.ingredients.set(i, inputs.get(i).ingredient());
        }
        this.result = result.copy();
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (container.getContainerSize() < INPUT_COUNT) return false;
        for (int i = 0; i < INPUT_COUNT; i++) {
            Input expected = inputs.get(i);
            ItemStack actual = container.getItem(i);
            if (expected.isEmpty()) {
                if (!actual.isEmpty()) return false;
            } else if (actual.getCount() < expected.requiredCount() || !expected.ingredient().test(actual)) {
                return false;
            }
        }
        return true;
    }

    public Input input(int slot) {
        return inputs.get(slot);
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FORGING_FURNACE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FORGING_FURNACE_TYPE;
    }

    public record Input(Ingredient ingredient, int requiredCount, int consumeCount, float consumeChance) {
        public Input {
            if (requiredCount < 0 || consumeCount < 0) throw new IllegalArgumentException("Recipe counts cannot be negative");
            if (consumeChance < 0.0F || consumeChance > 1.0F) throw new IllegalArgumentException("consume_chance must be in [0, 1]");
            if (ingredient == Ingredient.EMPTY && (requiredCount != 0 || consumeCount != 0)) {
                throw new IllegalArgumentException("Empty ingredients cannot require or consume items");
            }
        }

        public boolean isEmpty() {
            return ingredient == Ingredient.EMPTY;
        }
    }

    public static final class Serializer implements RecipeSerializer<ForgingFurnaceRecipe> {
        @Override
        public ForgingFurnaceRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray ingredientArray = GsonHelper.getAsJsonArray(json, "ingredients");
            if (ingredientArray.size() != INPUT_COUNT) {
                throw new JsonParseException("Forging furnace recipe " + id + " must contain exactly four ingredients");
            }

            NonNullList<Input> inputs = NonNullList.create();
            for (JsonElement element : ingredientArray) {
                JsonObject object = GsonHelper.convertToJsonObject(element, "ingredient");
                if (object.size() == 0) {
                    inputs.add(new Input(Ingredient.EMPTY, 0, 0, 0.0F));
                    continue;
                }
                Ingredient ingredient = Ingredient.fromJson(object);
                int requiredCount = GsonHelper.getAsInt(object, "count", 1);
                int consumeCount = GsonHelper.getAsInt(object, "consume_count", requiredCount);
                float consumeChance = GsonHelper.getAsFloat(object, "consume_chance", 1.0F);
                if (requiredCount < 1 || consumeCount < 0 || consumeCount > requiredCount) {
                    throw new JsonParseException("Invalid counts in forging furnace recipe " + id);
                }
                if (consumeChance < 0.0F || consumeChance > 1.0F) {
                    throw new JsonParseException("Invalid consume_chance in forging furnace recipe " + id);
                }
                inputs.add(new Input(ingredient, requiredCount, consumeCount, consumeChance));
            }

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            if (output.isEmpty()) throw new JsonParseException("Forging furnace recipe " + id + " has an empty output");
            return new ForgingFurnaceRecipe(id, inputs, output);
        }

        @Override
        public @Nullable ForgingFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NonNullList<Input> inputs = NonNullList.create();
            for (int i = 0; i < INPUT_COUNT; i++) {
                inputs.add(new Input(Ingredient.fromNetwork(buffer), buffer.readVarInt(), buffer.readVarInt(), buffer.readFloat()));
            }
            return new ForgingFurnaceRecipe(id, inputs, buffer.readItem());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ForgingFurnaceRecipe recipe) {
            for (Input input : recipe.inputs) {
                input.ingredient().toNetwork(buffer);
                buffer.writeVarInt(input.requiredCount());
                buffer.writeVarInt(input.consumeCount());
                buffer.writeFloat(input.consumeChance());
            }
            buffer.writeItem(recipe.result);
        }
    }
}
