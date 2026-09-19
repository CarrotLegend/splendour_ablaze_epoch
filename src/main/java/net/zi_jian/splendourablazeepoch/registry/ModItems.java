package net.zi_jian.splendourablazeepoch.registry;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.item.SkyEmblemItem;
import net.zi_jian.splendourablazeepoch.item.LegacyArmorItem;
import net.zi_jian.splendourablazeepoch.item.LegacyToolTiers;
import net.zi_jian.splendourablazeepoch.item.LegacyNoAnimationFoodItem;
import net.zi_jian.splendourablazeepoch.item.MottledBambooItem;
import net.zi_jian.splendourablazeepoch.world.TopworldClosure;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<Item> PYROTEMPER_DUST = ITEMS.register("pyrotemperdust",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SKY_EMBLEM = ITEMS.register("splendourablazecompass",
            () -> new SkyEmblemItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FORGING_FURNAC = ITEMS.register("forgingfurnac",
            () -> new BlockItem(ModBlocks.FORGING_FURNAC.get(), new Item.Properties()));
    public static final RegistryObject<Item> TIN_NUGGET = material("tinnugget", Rarity.COMMON);
    public static final RegistryObject<Item> LEAD_NUGGET = material("leadnugget", Rarity.COMMON);
    public static final RegistryObject<Item> BRONZE_INGOT = material("bronze_ingot", Rarity.COMMON);
    public static final RegistryObject<Item> TIN_INGOT = material("tin_ingot", Rarity.COMMON);
    public static final RegistryObject<Item> PEWTER_INGOT = material("pewter_ingot", Rarity.UNCOMMON);
    public static final RegistryObject<Item> MAGNETITE = material("magnetite", Rarity.COMMON);
    public static final RegistryObject<Item> MAGNETIC_STEEL_INGOT = material("magnetic_steel_ingot", Rarity.COMMON);
    public static final RegistryObject<Item> DAMASCENE_STEEL_INGOT = material("damascene_steel_ingot", Rarity.COMMON);
    public static final RegistryObject<Item> CUPRONICKEL_INGOT = material("cupronickel_ingot", Rarity.COMMON);
    public static final RegistryObject<Item> MOLD_TIN_INGOT = material("mold_tin_ingot", Rarity.COMMON);
    public static final RegistryObject<Item> SIMILAR_TIN = material("similartin", Rarity.COMMON);
    public static final RegistryObject<Item> DEMON_COPPER = material("demoncopper", Rarity.COMMON);
    public static final RegistryObject<Item> DARK_SALTPETER = material("darksaltpeter", Rarity.COMMON);
    public static final Map<String, RegistryObject<Item>> TOPWORLD_BLOCK_ITEMS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_SIMPLE_ITEMS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_TOOLS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_ARMOR = new LinkedHashMap<>();

    static {
        for (String id : ModBlocks.TOPWORLD_BLOCKS.keySet()) {
            TOPWORLD_BLOCK_ITEMS.put(id, ITEMS.register(id,
                    () -> new BlockItem(ModBlocks.get(id), new Item.Properties())));
        }
        for (String id : LegacyRegistryData.SIMPLE_ITEM_IDS) {
            LEGACY_SIMPLE_ITEMS.put(id, ITEMS.register(id, () -> new Item(properties(id))));
        }
        LEGACY_SIMPLE_ITEMS.put("leek", ITEMS.register("leek", () -> new LegacyNoAnimationFoodItem(
                new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(food(1, .5F, false)))));
        LEGACY_SIMPLE_ITEMS.put("beefstew", ITEMS.register("beefstew", () -> new BowlFoodItem(
                new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).food(food(8, 3F, false)))));
        LEGACY_SIMPLE_ITEMS.put("potherbsoup", ITEMS.register("potherbsoup", () -> new BowlFoodItem(
                new Item.Properties().stacksTo(16).rarity(Rarity.COMMON).food(food(4, 1F, false)))));
        LEGACY_SIMPLE_ITEMS.put("mottledbamboo", ITEMS.register("mottledbamboo", () -> new MottledBambooItem(
                new Item.Properties().stacksTo(64).rarity(Rarity.COMMON))));
        for (String family : LegacyRegistryData.TOOL_FAMILIES) {
            registerToolFamily(family);
        }
        LEGACY_TOOLS.put("tetragonalbludgeon", ITEMS.register("tetragonalbludgeon", ModItems::createTetragonalBludgeon));
        for (String family : LegacyRegistryData.ARMOR_FAMILIES) {
            registerArmorFamily(family);
        }
    }

    public static RegistryObject<Item> byId(String id) {
        RegistryObject<Item> item = LEGACY_SIMPLE_ITEMS.get(id);
        if (item != null) return item;
        item = LEGACY_TOOLS.get(id);
        if (item != null) return item;
        item = LEGACY_ARMOR.get(id);
        if (item != null) return item;
        return switch (id) {
            case "bronze_ingot" -> BRONZE_INGOT;
            case "pewter_ingot" -> PEWTER_INGOT;
            case "magnetic_steel_ingot" -> MAGNETIC_STEEL_INGOT;
            case "damascene_steel_ingot" -> DAMASCENE_STEEL_INGOT;
            case "cupronickel_ingot" -> CUPRONICKEL_INGOT;
            case "mold_tin_ingot" -> MOLD_TIN_INGOT;
            default -> throw new IllegalArgumentException("Unknown migrated item " + id);
        };
    }

    private static Item.Properties properties(String id) {
        Item.Properties properties = new Item.Properties().stacksTo(stackSize(id)).rarity(rarity(id));
        return switch (id) {
            case "ricecake" -> properties.food(food(4, 1.2F, false));
            case "rawkoifish", "rawcroaker" -> properties.food(food(1, .5F, true));
            case "cookedkoifish", "cookedcroaker" -> properties.food(food(3, .9F, true));
            case "rawvenison" -> properties.food(food(2, 1F, true));
            case "cookedvenison" -> properties.food(food(4, 1.6F, true));
            case "sugarcoatedhaws", "friedleekdumplings" -> properties.food(food(4, 1.5F, false));
            case "cloudberryfruit" -> properties.food(food(2, .2F, false));
            case "mulberry", "hawthorn" -> properties.food(food(1, .5F, false));
            default -> properties;
        };
    }

    private static FoodProperties food(int nutrition, float saturation, boolean meat) {
        FoodProperties.Builder builder = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation);
        if (meat) builder.meat();
        return builder.build();
    }

    private static int stackSize(String id) {
        return switch (id) {
            case "previousletter", "delicatebowl" -> 1;
            case "firearmtube", "friedleekdumplings" -> 16;
            default -> 64;
        };
    }

    private static Rarity rarity(String id) {
        return switch (id) {
            case "celes_terral_ingot" -> Rarity.EPIC;
            case "pyrotempertinder", "celesterralupgradesmithingtemplate", "celesterraldebris" -> Rarity.RARE;
            default -> Rarity.COMMON;
        };
    }

    private static void registerToolFamily(String family) {
        for (String kind : new String[]{"pickaxe", "axe", "sword", "shovel", "hoe"}) {
            String id = family + "_" + kind;
            LEGACY_TOOLS.put(id, ITEMS.register(id, () -> createTool(family, kind)));
        }
    }

    private static Item createTool(String family, String kind) {
        Tier tier = LegacyToolTiers.create(family, kind, () -> Ingredient.of(byId(family + "_ingot").get()));
        float attack = LegacyToolTiers.attackModifier(family, kind);
        float speed = LegacyToolTiers.speedModifier(family, kind);
        return switch (kind) {
            case "pickaxe" -> new PickaxeItem(tier, (int) attack, speed, new Item.Properties());
            case "axe" -> new AxeItem(tier, attack, speed, new Item.Properties());
            case "sword" -> new SwordItem(tier, (int) attack, speed, new Item.Properties());
            case "shovel" -> new ShovelItem(tier, attack, speed, new Item.Properties());
            case "hoe" -> new HoeItem(tier, (int) attack, speed, new Item.Properties());
            default -> throw new IllegalArgumentException(kind);
        };
    }

    private static Item createTetragonalBludgeon() {
        Tier tier = new Tier() {
            @Override public int getUses() { return 660; }
            @Override public float getSpeed() { return 4; }
            @Override public float getAttackDamageBonus() { return 3; }
            @Override public int getLevel() { return 4; }
            @Override public int getEnchantmentValue() { return 2; }
            @Override public Ingredient getRepairIngredient() { return Ingredient.of(DAMASCENE_STEEL_INGOT.get()); }
        };
        return new SwordItem(tier, 3, -3.2F, new Item.Properties());
    }

    private static void registerArmorFamily(String family) {
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            String suffix = switch (type) {
                case HELMET -> "helmet";
                case CHESTPLATE -> "chestplate";
                case LEGGINGS -> "leggings";
                case BOOTS -> "boots";
            };
            String id = family + "_armor_" + suffix;
            LEGACY_ARMOR.put(id, ITEMS.register(id,
                    () -> new LegacyArmorItem(family, type, () -> Ingredient.of(byId(family + "_ingot").get()))));
        }
    }

    private static RegistryObject<Item> material(String id, Rarity rarity) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().stacksTo(64).rarity(rarity)));
    }

    private ModItems() {
    }
}
