package net.zi_jian.splendourablazeepoch.registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.item.LegacyArmorItem;
import net.zi_jian.splendourablazeepoch.item.LegacyNoAnimationFoodItem;
import net.zi_jian.splendourablazeepoch.item.LegacyToolTiers;
import net.zi_jian.splendourablazeepoch.item.MottledBambooItem;
import net.zi_jian.splendourablazeepoch.item.SkyEmblemItem;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SplendourAblazeEpochMod.MOD_ID);

    public static final Map<String, RegistryObject<Item>> TOPWORLD_BLOCK_ITEMS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_SIMPLE_ITEMS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_EXTRA_ITEMS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_TOOLS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> LEGACY_ARMOR = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> SPAWN_EGGS = new LinkedHashMap<>();

    private static final List<String> RESTORED_EXTRA_ITEM_IDS = List.of(
            "topworld",
            "mungbeansoup",
            "positioningscroll_1",
            "commandflag",
            "initialseal",
            "ghostjadependant",
            "huntbow",
            "initialmagnet",
            "featheryfoambucket",
            "positioningscroll_2",
            "initialhandscroll",
            "dagger",
            "tigertally",
            "commandflag_1",
            "commandflag_2",
            "talisman_1",
            "talisman_2",
            "talisman_3",
            "talisman_4",
            "positioningscroll_3",
            "positioningscroll_4",
            "purplegoldbell",
            "positioningsceoll_5",
            "threebarrelblunderbuss",
            "initialfirearm",
            "barrelfirearm",
            "secretbeefstew"
    );

    public static final RegistryObject<Item> PYROTEMPER_DUST = ITEMS.register(
            "pyrotemperdust",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON))
    );

    public static final RegistryObject<Item> SKY_EMBLEM = ITEMS.register(
            "splendourablazecompass",
            () -> new SkyEmblemItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))
    );

    public static final RegistryObject<Item> FORGING_FURNAC = ITEMS.register(
            "forgingfurnac",
            () -> new BlockItem(ModBlocks.FORGING_FURNAC.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> CROAKER_BUCKET = ITEMS.register(
            "croakerbucket",
            () -> new MobBucketItem(
                    ModEntities.CROAKER,
                    () -> Fluids.WATER,
                    () -> SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1)
            )
    );

    public static final RegistryObject<Item> KOI_FISH_BUCKET = ITEMS.register(
            "koifishbucket",
            () -> new MobBucketItem(
                    ModEntities.KOI_FISH,
                    () -> Fluids.WATER,
                    () -> SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1)
            )
    );

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

    public static final RegistryObject<Item> CROAKER_SPAWN_EGG = spawnEgg(
            "croaker_spawn_egg", ModEntities.CROAKER, 0xAEAE82, 0xBF9643
    );
    public static final RegistryObject<Item> KOI_FISH_SPAWN_EGG = spawnEgg(
            "koifish_spawn_egg", ModEntities.KOI_FISH, 0xF2D7D7, 0xC33F11
    );
    public static final RegistryObject<Item> MAGPIE_SPAWN_EGG = spawnEgg(
            "magpie_spawn_egg", ModEntities.MAGPIE, 0x000033, 0xCCCCCC
    );
    public static final RegistryObject<Item> MESSENGER_SPAWN_EGG = spawnEgg(
            "messenger_spawn_egg", ModEntities.MESSENGER, 0x3B362B, 0xFFEA37
    );
    public static final RegistryObject<Item> MUSK_DEER_SPAWN_EGG = spawnEgg(
            "muskdeer_spawn_egg", ModEntities.MUSK_DEER, 0x957753, 0x4C4237
    );
    public static final RegistryObject<Item> PEACOCK_SPAWN_EGG = spawnEgg(
            "peacock_spawn_egg", ModEntities.PEACOCK, 0x0033CC, 0x009966
    );
    public static final RegistryObject<Item> PHEASANT_SPAWN_EGG = spawnEgg(
            "pheasant_spawn_egg", ModEntities.PHEASANT, 0x938585, 0xAF1025
    );
    public static final RegistryObject<Item> RACCOON_DOG_SPAWN_EGG = spawnEgg(
            "raccoondog_spawn_egg", ModEntities.RACCOON_DOG, 0xDFCDC1, 0x54453B
    );
    public static final RegistryObject<Item> RUSTED_ANCESTORS_SPAWN_EGG = spawnEgg(
            "rustedancestors_spawn_egg", ModEntities.RUSTED_ANCESTORS, 0xE19A1C, 0x664848
    );
    public static final RegistryObject<Item> RUST_HOUND_SPAWN_EGG = spawnEgg(
            "rusthound_spawn_egg", ModEntities.RUST_HOUND, 0xFF6600, 0x6B4028
    );
    public static final RegistryObject<Item> RUST_RELICS_SPAWN_EGG = spawnEgg(
            "rustrelics_spawn_egg", ModEntities.RUST_RELICS, 0x9C9889, 0x6B4028
    );
    public static final RegistryObject<Item> WATER_BUFFALO_SPAWN_EGG = spawnEgg(
            "waterbuffalo_spawn_egg", ModEntities.WATER_BUFFALO, 0x87917F, 0x57563E
    );

    static {
        for (String id : ModBlocks.TOPWORLD_BLOCKS.keySet()) {
            TOPWORLD_BLOCK_ITEMS.put(
                    id,
                    ITEMS.register(id, () -> new BlockItem(ModBlocks.get(id), new Item.Properties()))
            );
        }

        for (String id : LegacyRegistryData.SIMPLE_ITEM_IDS) {
            LEGACY_SIMPLE_ITEMS.put(id, ITEMS.register(id, () -> new Item(properties(id))));
        }

        LEGACY_SIMPLE_ITEMS.put(
                "leek",
                ITEMS.register(
                        "leek",
                        () -> new LegacyNoAnimationFoodItem(
                                new Item.Properties()
                                        .stacksTo(64)
                                        .rarity(Rarity.COMMON)
                                        .food(food(1, 0.5F, false))
                        )
                )
        );

        LEGACY_SIMPLE_ITEMS.put(
                "beefstew",
                ITEMS.register(
                        "beefstew",
                        () -> new BowlFoodItem(
                                new Item.Properties()
                                        .stacksTo(1)
                                        .rarity(Rarity.COMMON)
                                        .food(food(8, 3.0F, false))
                        )
                )
        );

        LEGACY_SIMPLE_ITEMS.put(
                "potherbsoup",
                ITEMS.register(
                        "potherbsoup",
                        () -> new BowlFoodItem(
                                new Item.Properties()
                                        .stacksTo(16)
                                        .rarity(Rarity.COMMON)
                                        .food(food(4, 1.0F, false))
                        )
                )
        );

        LEGACY_SIMPLE_ITEMS.put(
                "mottledbamboo",
                ITEMS.register(
                        "mottledbamboo",
                        () -> new MottledBambooItem(
                                new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)
                        )
                )
        );

        for (String id : RESTORED_EXTRA_ITEM_IDS) {
            LEGACY_EXTRA_ITEMS.put(id, ITEMS.register(id, () -> createRestoredExtraItem(id)));
        }

        for (String family : LegacyRegistryData.TOOL_FAMILIES) {
            registerToolFamily(family);
        }

        LEGACY_TOOLS.put(
                "tetragonalbludgeon",
                ITEMS.register("tetragonalbludgeon", ModItems::createTetragonalBludgeon)
        );

        for (String family : LegacyRegistryData.ARMOR_FAMILIES) {
            registerArmorFamily(family);
        }
    }

    public static RegistryObject<Item> byId(String id) {
        RegistryObject<Item> item = LEGACY_SIMPLE_ITEMS.get(id);
        if (item != null) {
            return item;
        }

        item = LEGACY_EXTRA_ITEMS.get(id);
        if (item != null) {
            return item;
        }

        item = LEGACY_TOOLS.get(id);
        if (item != null) {
            return item;
        }

        item = LEGACY_ARMOR.get(id);
        if (item != null) {
            return item;
        }

        item = SPAWN_EGGS.get(id);
        if (item != null) {
            return item;
        }

        return switch (id) {
            case "bronze_ingot" -> BRONZE_INGOT;
            case "pewter_ingot" -> PEWTER_INGOT;
            case "magnetic_steel_ingot" -> MAGNETIC_STEEL_INGOT;
            case "damascene_steel_ingot" -> DAMASCENE_STEEL_INGOT;
            case "cupronickel_ingot" -> CUPRONICKEL_INGOT;
            case "mold_tin_ingot" -> MOLD_TIN_INGOT;
            case "croakerbucket" -> CROAKER_BUCKET;
            case "koifishbucket" -> KOI_FISH_BUCKET;
            default -> throw new IllegalArgumentException("Unknown migrated item " + id);
        };
    }

    private static Item createRestoredExtraItem(String id) {
        return switch (id) {
            case "topworld" -> new Item(
                    new Item.Properties().durability(64).rarity(Rarity.COMMON)
            );
            case "mungbeansoup" -> new Item(
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
                            .food(
                                    new FoodProperties.Builder()
                                            .nutrition(1)
                                            .saturationMod(0.3F)
                                            .alwaysEat()
                                            .build()
                            )
            );
            case "positioningscroll_1", "positioningscroll_2", "positioningscroll_3",
                    "positioningscroll_4", "positioningsceoll_5", "commandflag", "commandflag_1",
                    "commandflag_2" -> new Item(
                    new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
            );
            case "initialseal" -> new Item(
                    new Item.Properties().durability(10).fireResistant().rarity(Rarity.EPIC)
            );
            case "ghostjadependant" -> new Item(
                    new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)
            );
            case "huntbow" -> new Item(
                    new Item.Properties().durability(350).rarity(Rarity.UNCOMMON)
            );
            case "initialmagnet" -> new Item(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
            );
            case "featheryfoambucket" -> new Item(
                    new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
            );
            case "initialhandscroll" -> new Item(
                    new Item.Properties().durability(100).fireResistant().rarity(Rarity.EPIC)
            );
            case "dagger" -> createDagger();
            case "tigertally" -> new Item(
                    new Item.Properties().durability(100).rarity(Rarity.RARE)
            );
            case "talisman_1", "talisman_2", "talisman_3", "talisman_4" -> new Item(
                    new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)
            );
            case "purplegoldbell" -> new Item(
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
            );
            case "threebarrelblunderbuss", "barrelfirearm" -> new Item(
                    new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)
            );
            case "initialfirearm" -> new Item(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
            );
            case "secretbeefstew" -> new Item(
                    new Item.Properties().durability(5).rarity(Rarity.RARE)
            );
            default -> new Item(new Item.Properties());
        };
    }

    private static Item createDagger() {
        Tier tier = new Tier() {
            @Override
            public int getUses() {
                return 540;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 6.0F;
            }

            @Override
            public int getLevel() {
                return 2;
            }

            @Override
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(BRONZE_INGOT.get());
            }
        };

        return new AxeItem(tier, 1.0F, -3.0F, new Item.Properties());
    }

    private static Item.Properties properties(String id) {
        Item.Properties properties = new Item.Properties().stacksTo(stackSize(id)).rarity(rarity(id));

        return switch (id) {
            case "ricecake" -> properties.food(food(4, 1.2F, false));
            case "rawkoifish", "rawcroaker" -> properties.food(food(1, 0.5F, true));
            case "cookedkoifish", "cookedcroaker" -> properties.food(food(3, 0.9F, true));
            case "rawvenison" -> properties.food(food(2, 1.0F, true));
            case "cookedvenison" -> properties.food(food(4, 1.6F, true));
            case "sugarcoatedhaws", "friedleekdumplings" -> properties.food(food(4, 1.5F, false));
            case "cloudberryfruit" -> properties.food(food(2, 0.2F, false));
            case "mulberry", "hawthorn" -> properties.food(food(1, 0.5F, false));
            default -> properties;
        };
    }

    private static FoodProperties food(int nutrition, float saturation, boolean meat) {
        FoodProperties.Builder builder = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationMod(saturation);

        if (meat) {
            builder.meat();
        }

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
        Tier tier = LegacyToolTiers.create(
                family,
                kind,
                () -> Ingredient.of(byId(family + "_ingot").get())
        );
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
            @Override
            public int getUses() {
                return 660;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 3.0F;
            }

            @Override
            public int getLevel() {
                return 4;
            }

            @Override
            public int getEnchantmentValue() {
                return 2;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(DAMASCENE_STEEL_INGOT.get());
            }
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
            LEGACY_ARMOR.put(
                    id,
                    ITEMS.register(
                            id,
                            () -> new LegacyArmorItem(
                                    family,
                                    type,
                                    () -> Ingredient.of(byId(family + "_ingot").get())
                            )
                    )
            );
        }
    }

    private static RegistryObject<Item> material(String id, Rarity rarity) {
        return ITEMS.register(
                id,
                () -> new Item(new Item.Properties().stacksTo(64).rarity(rarity))
        );
    }

    private static RegistryObject<Item> spawnEgg(
            String id,
            Supplier<? extends EntityType<? extends Mob>> entityType,
            int primaryColor,
            int secondaryColor
    ) {
        RegistryObject<Item> item = ITEMS.register(
                id,
                () -> new ForgeSpawnEggItem(
                        entityType,
                        primaryColor,
                        secondaryColor,
                        new Item.Properties()
                )
        );
        SPAWN_EGGS.put(id, item);
        return item;
    }

    private ModItems() {
    }
}
