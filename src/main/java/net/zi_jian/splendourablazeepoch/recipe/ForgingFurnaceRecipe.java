package net.zi_jian.splendourablazeepoch.recipe;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;

public final class ForgingFurnaceRecipe implements Recipe<SimpleContainer> {

    public static final int INPUT_COUNT = 4;

    private final ResourceLocation id;
    private final List<Input> inputs;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public ForgingFurnaceRecipe(
            ResourceLocation id,
            List<Input> inputs,
            ItemStack result
    ) {
        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.result = result.copy();
        this.ingredients = NonNullList.withSize(INPUT_COUNT, Ingredient.EMPTY);

        for (int i = 0; i < INPUT_COUNT; i++) {
            ingredients.set(i, inputs.get(i).ingredient());
        }
    }

    @Override
    public boolean matches(
            SimpleContainer container,
            Level level
    ) {
        if (container.getContainerSize() < INPUT_COUNT) {
            return false;
        }

        for (int i = 0; i < INPUT_COUNT; i++) {
            if (!inputs.get(i).matches(container.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    public Input input(
            int index
    ) {
        return inputs.get(index);
    }

    public List<ItemStack> getDisplayStacks(
            int index
    ) {
        Input input = inputs.get(index);

        if (input.isEmpty()) {
            return List.of();
        }

        List<ItemStack> stacks = new ArrayList<>();

        for (ItemStack stack : input.ingredient().getItems()) {
            ItemStack display = stack.copy();
            display.setCount(input.consumeCount());
            stacks.add(display);
        }

        return stacks;
    }

    @Override
    public ItemStack assemble(
            SimpleContainer container,
            RegistryAccess access
    ) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(
            int width,
            int height
    ) {
        return true;
    }

    @Override
    public ItemStack getResultItem(
            RegistryAccess access
    ) {
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

    public record Input(
            Ingredient ingredient,
            int consumeCount
    ) {

        public boolean isEmpty() {
            return ingredient == Ingredient.EMPTY;
        }

        public boolean matches(
                ItemStack stack
        ) {
            if (isEmpty()) {
                return stack.isEmpty();
            }

            return ingredient.test(stack)
                    && stack.getCount() >= consumeCount;
        }
    }

    public static final class Serializer
            implements RecipeSerializer<ForgingFurnaceRecipe> {

        @Override
        public ForgingFurnaceRecipe fromJson(
                ResourceLocation id,
                JsonObject json
        ) {
            JsonArray array = GsonHelper.getAsJsonArray(
                    json,
                    "ingredients"
            );

            if (array.size() != INPUT_COUNT) {
                throw new JsonParseException(
                        "Forging furnace recipe must contain exactly four ingredients: " + id
                );
            }

            List<Input> inputs = new ArrayList<>(INPUT_COUNT);

            for (JsonElement element : array) {
                JsonObject object = GsonHelper.convertToJsonObject(
                        element,
                        "ingredient"
                );

                if (object.size() == 0) {
                    inputs.add(
                            new Input(
                                    Ingredient.EMPTY,
                                    0
                            )
                    );
                    continue;
                }

                int consumeCount = GsonHelper.getAsInt(
                        object,
                        "consume_count",
                        1
                );

                if (consumeCount < 1) {
                    throw new JsonParseException(
                            "consume_count must be at least 1 in " + id
                    );
                }

                JsonObject ingredientJson = object.deepCopy();

                ingredientJson.remove("consume_count");
                ingredientJson.remove("count");
                ingredientJson.remove("consume_chance");

                inputs.add(
                        new Input(
                                Ingredient.fromJson(ingredientJson),
                                consumeCount
                        )
                );
            }

            ItemStack output = ShapedRecipe.itemStackFromJson(
                    GsonHelper.getAsJsonObject(
                            json,
                            "output"
                    )
            );

            return new ForgingFurnaceRecipe(
                    id,
                    inputs,
                    output
            );
        }

        @Override
        public @Nullable ForgingFurnaceRecipe fromNetwork(
                ResourceLocation id,
                FriendlyByteBuf buffer
        ) {
            List<Input> inputs = new ArrayList<>(INPUT_COUNT);

            for (int i = 0; i < INPUT_COUNT; i++) {
                inputs.add(
                        new Input(
                                Ingredient.fromNetwork(buffer),
                                buffer.readVarInt()
                        )
                );
            }

            return new ForgingFurnaceRecipe(
                    id,
                    inputs,
                    buffer.readItem()
            );
        }

        @Override
        public void toNetwork(
                FriendlyByteBuf buffer,
                ForgingFurnaceRecipe recipe
        ) {
            for (Input input : recipe.inputs) {
                input.ingredient().toNetwork(buffer);
                buffer.writeVarInt(input.consumeCount());
            }

            buffer.writeItem(recipe.result);
        }
    }
}