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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        if (inputs.size() != INPUT_COUNT) {
            throw new IllegalArgumentException(
                    "Forging furnace recipes require exactly four input definitions"
            );
        }

        this.id = id;
        this.inputs = List.copyOf(inputs);

        this.ingredients =
                NonNullList.withSize(
                        INPUT_COUNT,
                        Ingredient.EMPTY
                );

        for (int i = 0; i < INPUT_COUNT; i++) {
            this.ingredients.set(
                    i,
                    inputs.get(i).ingredient()
            );
        }

        this.result = result.copy();
    }

    @Override
    public boolean matches(
            SimpleContainer container,
            Level level
    ) {
        return findMatchingSlots(container).isPresent();
    }

    public Optional<int[]> findMatchingSlots(
            SimpleContainer container
    ) {
        if (container.getContainerSize() < INPUT_COUNT) {
            return Optional.empty();
        }

        List<Integer> requiredInputs =
                new ArrayList<>();

        for (int i = 0; i < INPUT_COUNT; i++) {
            if (!inputs.get(i).isEmpty()) {
                requiredInputs.add(i);
            }
        }

        List<Integer> occupiedSlots =
                new ArrayList<>();

        for (int slot = 0; slot < INPUT_COUNT; slot++) {
            if (!container.getItem(slot).isEmpty()) {
                occupiedSlots.add(slot);
            }
        }

        if (requiredInputs.size() != occupiedSlots.size()) {
            return Optional.empty();
        }

        int[] mapping =
                new int[INPUT_COUNT];

        Arrays.fill(
                mapping,
                -1
        );

        boolean[] usedSlots =
                new boolean[INPUT_COUNT];

        boolean matched =
                matchRecursive(
                        container,
                        requiredInputs,
                        occupiedSlots,
                        0,
                        mapping,
                        usedSlots
                );

        if (!matched) {
            return Optional.empty();
        }

        return Optional.of(mapping);
    }

    private boolean matchRecursive(
            SimpleContainer container,
            List<Integer> requiredInputs,
            List<Integer> occupiedSlots,
            int index,
            int[] mapping,
            boolean[] usedSlots
    ) {
        if (index >= requiredInputs.size()) {
            return true;
        }

        int recipeInputIndex =
                requiredInputs.get(index);

        Input expected =
                inputs.get(recipeInputIndex);

        for (int actualSlot : occupiedSlots) {

            if (usedSlots[actualSlot]) {
                continue;
            }

            ItemStack actual =
                    container.getItem(actualSlot);

            if (!expected.matches(actual)) {
                continue;
            }

            usedSlots[actualSlot] = true;

            mapping[recipeInputIndex] =
                    actualSlot;

            if (matchRecursive(
                    container,
                    requiredInputs,
                    occupiedSlots,
                    index + 1,
                    mapping,
                    usedSlots
            )) {
                return true;
            }

            mapping[recipeInputIndex] = -1;
            usedSlots[actualSlot] = false;
        }

        return false;
    }

    public Input input(
            int index
    ) {
        return inputs.get(index);
    }

    public List<ItemStack> getDisplayStacks(
            int index
    ) {
        Input input =
                inputs.get(index);

        if (input.isEmpty()) {
            return List.of();
        }

        List<ItemStack> result =
                new ArrayList<>();

        for (ItemStack original
                : input.ingredient().getItems()) {

            ItemStack display =
                    original.copy();

            display.setCount(
                    input.requiredCount()
            );

            result.add(display);
        }

        return result;
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
            int requiredCount,
            int consumeCount,
            float consumeChance
    ) {

        public Input {
            if (requiredCount < 0) {
                throw new IllegalArgumentException(
                        "requiredCount cannot be negative"
                );
            }

            if (consumeCount < 0) {
                throw new IllegalArgumentException(
                        "consumeCount cannot be negative"
                );
            }

            if (consumeCount > requiredCount) {
                throw new IllegalArgumentException(
                        "consumeCount cannot exceed requiredCount"
                );
            }

            if (consumeChance < 0.0F
                    || consumeChance > 1.0F) {
                throw new IllegalArgumentException(
                        "consumeChance must be between 0 and 1"
                );
            }

            if (ingredient == Ingredient.EMPTY
                    && (requiredCount != 0
                    || consumeCount != 0)) {

                throw new IllegalArgumentException(
                        "Empty input cannot require or consume items"
                );
            }
        }

        public boolean isEmpty() {
            return ingredient == Ingredient.EMPTY;
        }

        public boolean matches(
                ItemStack stack
        ) {
            if (isEmpty()) {
                return stack.isEmpty();
            }

            if (stack.isEmpty()) {
                return false;
            }

            if (stack.getCount() < requiredCount) {
                return false;
            }

            return ingredient.test(stack);
        }
    }

    public static final class Serializer
            implements RecipeSerializer<ForgingFurnaceRecipe> {

        @Override
        public ForgingFurnaceRecipe fromJson(
                ResourceLocation id,
                JsonObject json
        ) {
            JsonArray ingredientArray =
                    GsonHelper.getAsJsonArray(
                            json,
                            "ingredients"
                    );

            if (ingredientArray.size() != INPUT_COUNT) {
                throw new JsonParseException(
                        "Forging furnace recipe "
                                + id
                                + " must contain exactly four ingredient entries"
                );
            }

            NonNullList<Input> inputs =
                    NonNullList.create();

            for (JsonElement element
                    : ingredientArray) {

                JsonObject object =
                        GsonHelper.convertToJsonObject(
                                element,
                                "ingredient"
                        );

                if (object.size() == 0) {
                    inputs.add(
                            new Input(
                                    Ingredient.EMPTY,
                                    0,
                                    0,
                                    0.0F
                            )
                    );

                    continue;
                }

                int requiredCount =
                        GsonHelper.getAsInt(
                                object,
                                "count",
                                1
                        );

                int consumeCount =
                        GsonHelper.getAsInt(
                                object,
                                "consume_count",
                                requiredCount
                        );

                float consumeChance =
                        GsonHelper.getAsFloat(
                                object,
                                "consume_chance",
                                1.0F
                        );

                if (requiredCount < 1) {
                    throw new JsonParseException(
                            "Invalid count in forging furnace recipe "
                                    + id
                    );
                }

                if (consumeCount < 0
                        || consumeCount > requiredCount) {

                    throw new JsonParseException(
                            "Invalid consume_count in forging furnace recipe "
                                    + id
                    );
                }

                if (consumeChance < 0.0F
                        || consumeChance > 1.0F) {

                    throw new JsonParseException(
                            "Invalid consume_chance in forging furnace recipe "
                                    + id
                    );
                }

                JsonObject ingredientJson =
                        object.deepCopy();

                ingredientJson.remove("count");
                ingredientJson.remove("consume_count");
                ingredientJson.remove("consume_chance");

                Ingredient ingredient =
                        Ingredient.fromJson(
                                ingredientJson
                        );

                inputs.add(
                        new Input(
                                ingredient,
                                requiredCount,
                                consumeCount,
                                consumeChance
                        )
                );
            }

            ItemStack output =
                    ShapedRecipe.itemStackFromJson(
                            GsonHelper.getAsJsonObject(
                                    json,
                                    "output"
                            )
                    );

            if (output.isEmpty()) {
                throw new JsonParseException(
                        "Forging furnace recipe "
                                + id
                                + " has an empty output"
                );
            }

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
            NonNullList<Input> inputs =
                    NonNullList.create();

            for (int i = 0; i < INPUT_COUNT; i++) {
                Ingredient ingredient =
                        Ingredient.fromNetwork(buffer);

                int requiredCount =
                        buffer.readVarInt();

                int consumeCount =
                        buffer.readVarInt();

                float consumeChance =
                        buffer.readFloat();

                inputs.add(
                        new Input(
                                ingredient,
                                requiredCount,
                                consumeCount,
                                consumeChance
                        )
                );
            }

            ItemStack result =
                    buffer.readItem();

            return new ForgingFurnaceRecipe(
                    id,
                    inputs,
                    result
            );
        }

        @Override
        public void toNetwork(
                FriendlyByteBuf buffer,
                ForgingFurnaceRecipe recipe
        ) {
            for (Input input
                    : recipe.inputs) {

                input.ingredient().toNetwork(buffer);

                buffer.writeVarInt(
                        input.requiredCount()
                );

                buffer.writeVarInt(
                        input.consumeCount()
                );

                buffer.writeFloat(
                        input.consumeChance()
                );
            }

            buffer.writeItem(
                    recipe.result
            );
        }
    }
}