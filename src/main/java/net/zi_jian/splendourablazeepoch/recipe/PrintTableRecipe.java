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

public final class PrintTableRecipe
        implements Recipe<SimpleContainer> {

    public static final int INPUT_COUNT = 9;

    /*
     * Legacy print-table layout:
     * 0-5 movable type, 6 enchanted-book master,
     * 7 blank book, 8 black dye.
     *
     * The master is deliberately copied rather than consumed.
     */
    public static final int MASTER_SLOT = 6;
    public static final int BOOK_SLOT = 7;
    public static final int INK_SLOT = 8;

    private final ResourceLocation id;
    private final List<Ingredient> inputs;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack displayResult;

    public PrintTableRecipe(
            ResourceLocation id,
            List<Ingredient> inputs,
            ItemStack displayResult
    ) {
        if (inputs.size() != INPUT_COUNT) {
            throw new IllegalArgumentException(
                    "Print table recipe requires exactly 9 inputs"
            );
        }

        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.displayResult = displayResult.copy();

        this.ingredients =
                NonNullList.withSize(
                        INPUT_COUNT,
                        Ingredient.EMPTY
                );

        for (int i = 0; i < INPUT_COUNT; i++) {
            ingredients.set(
                    i,
                    inputs.get(i)
            );
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
            Ingredient ingredient =
                    inputs.get(i);

            ItemStack stack =
                    container.getItem(i);

            if (ingredient == Ingredient.EMPTY) {
                if (!stack.isEmpty()) {
                    return false;
                }

                continue;
            }

            if (!ingredient.test(stack)) {
                return false;
            }
        }

        return true;
    }

    public Ingredient input(
            int index
    ) {
        return inputs.get(index);
    }

    public List<ItemStack> getDisplayStacks(
            int index
    ) {
        Ingredient ingredient =
                inputs.get(index);

        if (ingredient == Ingredient.EMPTY) {
            return List.of();
        }

        List<ItemStack> stacks =
                new ArrayList<>();

        for (ItemStack stack : ingredient.getItems()) {
            stacks.add(
                    stack.copy()
            );
        }

        return stacks;
    }

    /**
     * JEI needs a stable output even though the real printed item is copied
     * from the enchanted-book master at runtime.
     */
    public ItemStack getResult() {
        return displayResult.copy();
    }

    @Override
    public ItemStack assemble(
            SimpleContainer container,
            RegistryAccess access
    ) {
        ItemStack master =
                container.getItem(
                        MASTER_SLOT
                );

        if (master.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack printed =
                master.copy();

        printed.setCount(
                displayResult.getCount()
        );

        return printed;
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
        return displayResult.copy();
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
        return ModRecipes.PRINT_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.PRINT_TABLE_TYPE;
    }

    public static final class Serializer
            implements RecipeSerializer<PrintTableRecipe> {

        @Override
        public PrintTableRecipe fromJson(
                ResourceLocation id,
                JsonObject json
        ) {
            JsonArray array =
                    GsonHelper.getAsJsonArray(
                            json,
                            "ingredients"
                    );

            if (array.size() != INPUT_COUNT) {
                throw new JsonParseException(
                        "Print table recipe must contain exactly 9 ingredients: "
                                + id
                );
            }

            List<Ingredient> inputs =
                    new ArrayList<>(
                            INPUT_COUNT
                    );

            for (JsonElement element : array) {
                if (element.isJsonObject()
                        && element.getAsJsonObject().size() == 0) {

                    inputs.add(
                            Ingredient.EMPTY
                    );
                } else {
                    inputs.add(
                            Ingredient.fromJson(
                                    element
                            )
                    );
                }
            }

            ItemStack output =
                    ShapedRecipe.itemStackFromJson(
                            GsonHelper.getAsJsonObject(
                                    json,
                                    "output"
                            )
                    );

            return new PrintTableRecipe(
                    id,
                    inputs,
                    output
            );
        }

        @Nullable
        @Override
        public PrintTableRecipe fromNetwork(
                ResourceLocation id,
                FriendlyByteBuf buffer
        ) {
            List<Ingredient> inputs =
                    new ArrayList<>(
                            INPUT_COUNT
                    );

            for (int i = 0; i < INPUT_COUNT; i++) {
                inputs.add(
                        Ingredient.fromNetwork(
                                buffer
                        )
                );
            }

            ItemStack result =
                    buffer.readItem();

            return new PrintTableRecipe(
                    id,
                    inputs,
                    result
            );
        }

        @Override
        public void toNetwork(
                FriendlyByteBuf buffer,
                PrintTableRecipe recipe
        ) {
            for (Ingredient ingredient
                    : recipe.inputs) {
                ingredient.toNetwork(
                        buffer
                );
            }

            buffer.writeItem(
                    recipe.displayResult
            );
        }
    }
}
