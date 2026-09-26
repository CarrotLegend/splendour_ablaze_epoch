package net.zi_jian.splendourablazeepoch.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.sounds.SoundEvents;
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
    private static final Map<String, RegistryObject<Item>> ALL_ITEMS = new LinkedHashMap<>();

    private static final Set<String> EXTRA_ITEM_IDS = Set.of(
            "topworld", "mungbeansoup", "positioningscroll_1", "commandflag", "initialseal",
            "ghostjadependant", "huntbow", "initialmagnet", "featheryfoambucket", "positioningscroll_2",
            "initialhandscroll", "dagger", "tigertally", "commandflag_1", "commandflag_2",
            "talisman_1", "talisman_2", "talisman_3", "talisman_4", "positioningscroll_3",
            "positioningscroll_4", "purplegoldbell", "positioningsceoll_5", "threebarrelblunderbuss",
            "initialfirearm", "barrelfirearm", "secretbeefstew"
    );

    private static final Set<String> MAIN_SPAWN_EGG_IDS = Set.of(
            "pheasant_spawn_egg",
            "muskdeer_spawn_egg",
            "waterbuffalo_spawn_egg",
            "koifish_spawn_egg",
            "magpie_spawn_egg",
            "peacock_spawn_egg",
            "raccoondog_spawn_egg",
            "croaker_spawn_egg",
            "rustedancestors_spawn_egg",
            "rustrelics_spawn_egg",
            "rusthound_spawn_egg",
            "messenger_spawn_egg",
            "girlghost_spawn_egg",
            "goldenhairhou_spawn_egg",
            "rustedchef_spawn_egg",
            "cauldronbeast_spawn_egg",
            "terracottawarriorsguard_spawn_egg",
            "terracottageneral_spawn_egg",
            "terracottawarriors_spawn_egg",
            "alivepictograph_spawn_egg",
            "castinscribedautomaton_spawn_egg",
            "flyarrowhead_spawn_egg",
            "skyadministrator_spawn_egg",
            "darkworm_spawn_egg",
            "pagewraith_spawn_egg",
            "pagegnat_spawn_egg",
            "firearmtigerguard_spawn_egg"
    );

    private static final String[] LEGACY_REGISTRATION_ORDER = {
            "inkdirt", "radiantlimestone", "fadedradiantlimestone", "wutong_wood", "wutong_log",
            "wutong_planks", "wutong_leaves", "wutong_stairs", "wutong_slab", "wutong_fence",
            "wutong_fence_gate", "wutong_pressure_plate", "wutong_button", "castradiantlimestonebricks", "castradiantlimestone",
            "radiantlimestonecopperore", "radiantlimestoneironore", "radiantlimestoneleadore", "rawlead", "lead_ingot",
            "lead_block", "cyantwig", "polishedradiantlimestone", "polishedcastradiantlimestone", "cutpolishedcastradiantlimestone",
            "polishedfigurecastradiantlimestone", "chiseledcastradiantlimestone", "auroraleadenblock", "auroraleadentile", "auroraleadenpillar",
            "topworld", "wutongsapling", "bellflower", "castradiantlimestonebricksslab", "cutpolishedcastradiantlimestoneslab",
            "castradiantlimestoneslab", "castradiantlimestonebricksstairs", "cutpolishedcastradiantlimestonestairs", "castradiantlimestonestairs", "auroraleadenblockslab",
            "auroraleadenblockstairs", "bluishstone", "bluishstonebricks", "bluishstonebricksstairs", "strangeprintcitystart",
            "wutongbookshelf", "alivepictograph_spawn_egg", "alivepictographspwner", "radiantlimestonetinore", "tin_ingot",
            "tin_block", "abstrusechest", "pyrotemperdust", "rawtin", "celes_terral_ingot",
            "celes_terral_block", "celes_terral_pickaxe", "celes_terral_axe", "celes_terral_sword", "celes_terral_shovel",
            "celes_terral_hoe", "celes_terral_armor_helmet", "celes_terral_armor_chestplate", "celes_terral_armor_leggings", "celes_terral_armor_boots",
            "sagesfragments", "typefirst", "typesecond", "typethird", "typefourth",
            "typefifth", "typesixth", "printtable", "ricecake", "specular_pine_wood",
            "specular_pine_log", "specular_pine_planks", "specular_pine_leaves", "specular_pine_stairs", "specular_pine_slab",
            "specular_pine_fence", "specular_pine_fence_gate", "specular_pine_pressure_plate", "specular_pine_button", "pheasant_spawn_egg",
            "muskdeer_spawn_egg", "terracottawarriorsguard_spawn_egg", "hosta", "ophiopogon", "artemisia",
            "strawmushroom", "ginkgo_wood", "ginkgo_log", "ginkgo_planks", "ginkgo_leaves",
            "ginkgo_stairs", "ginkgo_slab", "ginkgo_fence", "ginkgo_fence_gate", "ginkgo_pressure_plate",
            "ginkgo_button", "pyrotempertinder", "koifish_spawn_egg",
            "pewter_ingot", "pewter_block", "pewter_pickaxe", "pewter_axe", "pewter_sword",
            "pewter_shovel", "pewter_hoe", "classics", "potteryclay", "potteryclayball",
            "bronze_ingot", "bronze_block", "bronze_pickaxe", "bronze_axe", "bronze_sword",
            "bronze_shovel", "bronze_hoe", "bronze_armor_helmet", "bronze_armor_chestplate", "bronze_armor_leggings",
            "bronze_armor_boots", "ribbonrock", "magnetite", "magnetiteribbonrockore", "splendourablazecompass", "magnetic_steel_ingot", "magnetic_steel_block", "magnetic_steel_pickaxe", "magnetic_steel_axe",
            "magnetic_steel_sword", "magnetic_steel_shovel", "magnetic_steel_hoe", "magnetic_steel_armor_helmet", "magnetic_steel_armor_chestplate",
            "magnetic_steel_armor_leggings", "magnetic_steel_armor_boots", "rawkoifish", "cookedkoifish", "rawvenison",
            "cookedvenison", "flintstone", "koifishbucket", "ribbonrockcopperore", "ribbonrockcoalore",
            "polished_ribbonrock", "sugarcoatedhaws", "mungbeansoup", "thatchblock", "bronzenugget",
            "woodencrate", "thatchblockstairs", "thatchblockslab", "saxifrage", "cloudberry",
            "stubbleawn", "cloudberryfruit", "hydrangea", "positioningscroll_1", "cerasus_japonica_wood",
            "cerasus_japonica_log", "cerasus_japonica_planks", "cerasus_japonica_leaves", "cerasus_japonica_stairs", "cerasus_japonica_slab",
            "cerasus_japonica_fence", "cerasus_japonica_fence_gate", "cerasus_japonica_pressure_plate", "cerasus_japonica_button", "jadesand",
            "featheryfoam", "blossomcerasusjaponicaleaves", "jadedebris", "jadeblock", "jade",
            "whitefleabane", "scabish", "xyfzadder", "wordbullet_spawn_egg",
            "bigtype_spawn_egg", "girlghost_spawn_egg", "rustedancestors_spawn_egg", "previousletter", "forgingfurnac",
            "leadnugget", "tinnugget", "commandflag", "floatingheart",
            "darksteelbricks", "darksteelstone", "chiseleddarksteelbricks", "patternedsilverdarksteelbricks", "cyxadder",
            "sagemperorplatformadder", "darksteelbrickswall", "chillironchest", "arcaneprintsovereign", "initialseal",
            "arcaneprintshield_spawn_egg", "ghostjadependant", "osmanthus_wood", "osmanthus_log", "osmanthus_planks",
            "osmanthus_leaves", "osmanthus_stairs", "osmanthus_slab", "osmanthus_fence", "osmanthus_fence_gate",
            "osmanthus_pressure_plate", "osmanthus_button", "flyarrowhead_spawn_egg", "whistlingarrowr", "shrillfragments",
            "cerasusjaponicabookshelf", "specularpinebookshelf", "osmanthusbookshelf", "ginkgobookshelf", "ginkgosapling",
            "cerasusjaponicasapling", "blossomosmanthusleaves", "blossomosmanthusbranch", "huntbow", "initialmagnet",
            "featheryfoambucket", "positioningscroll_2", "osmanthussapling", "specularpinesapling", "ribbonrockbricks",
            "chiseledribbonrockbricks", "celesterralupgradesmithingtemplate", "chronsovereigntystar", "raccoondog_spawn_egg", "rustrelics_spawn_egg", "yellowhemp", "rusthound_spawn_egg", "bluishstonebricksslab",
            "polishedbluishstone", "castradiantlimestonebrickswall", "bluishstonebrickswall", "auroraleadenblockwall", "ribbonrockbrickswall",
            "castradiantlimestonewall", "ribbonrockbricksstairs", "ribbonrockbricksslab", "rawtinblock",
            "rawleadblock", "magnetiteblock", "suspiciousradiantlimestonecopperore", "demoncopper", "suspiciousradiantlimestonetinore",
            "similartin", "radiantlimestonegoldore", "ribbonrocksilverore", "rawsilver", "gnome",
            "sliveringot", "celesterraldebris", "basella", "osmunda", "portulaca",
            "potherbsoup", "sliverblock", "silkblock", "magpie_spawn_egg", "radiantlimestonecinnabarore",
            "cinnabar", "radiantlimestonegraphiteore", "graphite", "crystalstone", "croaker_spawn_egg",
            "rawcroaker", "cookedcroaker", "croakerbucket", "cinnabarblock", "graphiteblock",
            "demoncopperblock", "similartinblock", "wutongtrapdoor", "ginkgotrapdoor", "cerasusjaponicatrapdoor",
            "osmanthustrapdoor", "specularpinetrapdoor", "mulberry_wood", "mulberry_log", "mulberry_planks",
            "mulberry_leaves", "mulberry_stairs", "mulberry_slab", "mulberry_fence", "mulberry_fence_gate",
            "mulberry_pressure_plate", "mulberry_button", "mulberrytrapdoor", "mulberrywormleaves", "mulberryfruitleaves",
            "mulberry", "mulberrysapling", "mottled_bamboo_wood", "mottled_bamboo_log", "mottled_bamboo_planks",
            "mottled_bamboo_leaves", "mottled_bamboo_stairs", "mottled_bamboo_slab", "mottled_bamboo_fence", "mottled_bamboo_fence_gate",
            "mottled_bamboo_pressure_plate", "mottled_bamboo_button", "mottledbamboos", "mottledbamboo", "mottledbambootrapdoor",
            "mottledbamboobud", "darkworm_spawn_egg", "damascene_steel_ingot", "damascene_steel_block",
            "damascene_steel_pickaxe", "damascene_steel_axe", "damascene_steel_sword", "damascene_steel_shovel", "damascene_steel_hoe",
            "damascene_steel_armor_helmet", "damascene_steel_armor_chestplate", "damascene_steel_armor_leggings", "damascene_steel_armor_boots", "inkbuliteitem",
            "sericulturehive", "sericulturehiveleaves", "sericulturehivefull", "silkworm", "silkwormcocoon",
            "mottledbambooblock", "redsilkblock", "orangesilkblock", "yellowsilkblock", "limesilkblock",
            "greensilkblock", "cyansilkblock", "bluesilkblock", "lightbluesilkblock", "purplesilkblock",
            "magentasilkblock", "pinksilkblock", "brownsilkblock", "graysilkblock", "lightgraysilkblock",
            "blacksilkblock", "mulberrybookshelf", "mottledbamboobookshelf", "lime", "whitewashedbricksblock",
            "potterytile", "darktile", "darktilestairs", "darktileslab", "darktilewall",
            "immortalmothchest", "waterbuffalo_spawn_egg", "ambiguousimmortale", "darksteelbricksstairs", "darksteelbricksslab",
            "antigravitdevice", "skyadministrator_spawn_egg", "cauldronbeast_spawn_egg", "dragonsophora_wood", "dragonsophora_log",
            "dragonsophora_planks", "dragonsophora_leaves", "dragonsophora_stairs", "dragonsophora_slab", "dragonsophora_fence",
            "dragonsophora_fence_gate", "dragonsophora_pressure_plate", "dragonsophora_button", "tetragonalbludgeon", "terracottageneral_spawn_egg",
            "terracottageneraladder", "lacqueredwood", "carvingterracottabricks", "exquisitewoodenbox", "mausoleumadder",
            "terracottabricks", "mottledterracottabricks", "terracottabricksstairs", "terracottabricksslab", "candlestick",
            "whiteterracottabricks", "potteryjar", "fatpotteryjar", "thinpotteryjar", "initialhandscroll",
            "luminousjewel", "dagger", "terracottawarriors_spawn_egg", "tigertally", "commandflag_1",
            "commandflag_2", "talisman", "talisman_1", "talisman_2", "talisman_3",
            "talisman_4", "pagewraith_spawn_egg", "pagegnat_spawn_egg", "alchemypage_spawn_egg", "patchywhitewashedbricksblock",
            "viridiandescendacademyadder", "mossywhitewashedbricksblock", "papercocoon", "zxxjadder", "rustedmoss",
            "whitestinkhorn", "paperleaves", "positioningscroll_3", "positioningscroll_4", "charredsand",
            "firmrock", "firmrocksulfurore", "firmrockdarksaltpeterore", "firmrockironore", "firmrockcoalore",
            "darksaltpeter", "sulfur", "firmrockbricks", "firmrockbricksstairs", "firmrockbricksslab",
            "firmrockbrickwall", "polishedfirmrock", "firmrocksaltpeterore", "saltpeter", "blackpowder",
            "dragonsophoratrapdoor", "crackedbluishstonebricks", "bluishstonepillar", "chiseledfirmrock", "crackedfirmrockbricks",
            "chiseledbluishstonebricks", "wetwhitewashedbricksblock", "mottledfirmrockbricks", "burntfirmrockbricks", "burntpolishedfirmrock",
            "charredfirmrock", "peacock_spawn_egg", "inkgrassblock", "mossybluishstonebricks", "decayed_wood",
            "decayed_log", "decayed_planks", "decayed_stairs", "decayed_slab", "decayed_fence",
            "decayed_fence_gate", "decayed_pressure_plate", "decayed_button", "decayedtarpdoor",
            "goldenhairhou_spawn_egg", "beacon_fire_chest", "purplegoldbell",
            "jmhadder", "qianyang_stronghold", "zytsadder", "simplegrave", "shockflamewarlord",
            "cupronickel_ingot", "cupronickel_block", "cupronickel_pickaxe", "cupronickel_axe", "cupronickel_sword",
            "cupronickel_shovel", "cupronickel_hoe", "cupronickel_armor_helmet", "cupronickel_armor_chestplate", "cupronickel_armor_leggings",
            "cupronickel_armor_boots", "positioningsceoll_5", "threebarrelblunderbuss", "leadbullet", "initialfirearm",
            "firearmtube", "barrelfirearm", "decayedleaves", "mold_tin_ingot", "mold_tin_block",
            "mold_tin_pickaxe", "mold_tin_axe", "mold_tin_sword", "mold_tin_shovel", "mold_tin_hoe",
            "mold_tin_armor_helmet", "mold_tin_armor_chestplate", "mold_tin_armor_leggings", "mold_tin_armor_boots", "messenger_spawn_egg",
            "firearmtigerguard_spawn_egg", "strippedwutonglog", "strippedwutongwood", "strippedspecularpinelog", "strippedspecularpinewood",
            "strippedginkgolog", "strippedginkgowood", "strippedcerasusjaponicalog", "strippedcerasusjaponicawood", "strippedosmanthuslog",
            "strippedosmanthuswood", "strippedmulberrylog", "strippedmulberrywood", "strippeddragonsophoralog", "strippeddragonsophorawood",
            "strippeddecayedlog", "strippeddecayedwood", "dragonsophorabookshelf", "decayedbookshelf", "castinscribedautomaton_spawn_egg",
            "staranise", "sichuanpepper", "fennel", "clove", "cinnamonbark",
            "bayleaf", "nutmeg", "dahurianangelica", "beefstew",
            "millet", "leek", "milletplant", "leekplant", "delicatebowl",
            "secretbeefstew", "rustedchef_spawn_egg", "claypot", "hawthornleaves", "hawthorn",
            "hawthornfuritleaves", "hawthornsapling", "friedleekdumplings", "hibiscus_wood",
            "hibiscus_log", "hibiscus_planks", "hibiscus_leaves", "hibiscus_stairs", "hibiscus_slab",
            "hibiscus_fence", "hibiscus_fence_gate", "hibiscus_pressure_plate", "hibiscus_button", "hibiscus_tarpdoor",
            "hibiscusflameleaves", "strippedhibiscuslog", "strippedhibiscuswood"
    };

    public static final RegistryObject<Item> PYROTEMPER_DUST;
    public static final RegistryObject<Item> SKY_EMBLEM;
    public static final RegistryObject<Item> FORGING_FURNAC;
    public static final RegistryObject<Item> CROAKER_BUCKET;
    public static final RegistryObject<Item> KOI_FISH_BUCKET;
    public static final RegistryObject<Item> TIN_NUGGET;
    public static final RegistryObject<Item> LEAD_NUGGET;
    public static final RegistryObject<Item> BRONZE_INGOT;
    public static final RegistryObject<Item> TIN_INGOT;
    public static final RegistryObject<Item> PEWTER_INGOT;
    public static final RegistryObject<Item> MAGNETITE;
    public static final RegistryObject<Item> MAGNETIC_STEEL_INGOT;
    public static final RegistryObject<Item> DAMASCENE_STEEL_INGOT;
    public static final RegistryObject<Item> CUPRONICKEL_INGOT;
    public static final RegistryObject<Item> MOLD_TIN_INGOT;
    public static final RegistryObject<Item> SIMILAR_TIN;
    public static final RegistryObject<Item> DEMON_COPPER;
    public static final RegistryObject<Item> DARK_SALTPETER;

    public static final RegistryObject<Item> PHEASANT_SPAWN_EGG;
    public static final RegistryObject<Item> MUSK_DEER_SPAWN_EGG;
    public static final RegistryObject<Item> WATER_BUFFALO_SPAWN_EGG;
    public static final RegistryObject<Item> KOI_FISH_SPAWN_EGG;
    public static final RegistryObject<Item> MAGPIE_SPAWN_EGG;
    public static final RegistryObject<Item> PEACOCK_SPAWN_EGG;
    public static final RegistryObject<Item> RACCOON_DOG_SPAWN_EGG;
    public static final RegistryObject<Item> CROAKER_SPAWN_EGG;
    public static final RegistryObject<Item> RUSTED_ANCESTORS_SPAWN_EGG;
    public static final RegistryObject<Item> RUST_RELICS_SPAWN_EGG;
    public static final RegistryObject<Item> RUST_HOUND_SPAWN_EGG;
    public static final RegistryObject<Item> MESSENGER_SPAWN_EGG;
    public static final RegistryObject<Item> GIRL_GHOST_SPAWN_EGG;
    public static final RegistryObject<Item> GOLDEN_HAIR_HOU_SPAWN_EGG;
    public static final RegistryObject<Item> RUSTED_CHEF_SPAWN_EGG;
    public static final RegistryObject<Item> CAULDRON_BEAST_SPAWN_EGG;
    public static final RegistryObject<Item> TERRACOTTA_WARRIORS_GUARD_SPAWN_EGG;
    public static final RegistryObject<Item> TERRACOTTA_GENERAL_SPAWN_EGG;
    public static final RegistryObject<Item> TERRACOTTA_WARRIORS_SPAWN_EGG;
    public static final RegistryObject<Item> ALIVE_PICTOGRAPH_SPAWN_EGG;
    public static final RegistryObject<Item> CAST_INSCRIBED_AUTOMATON_SPAWN_EGG;
    public static final RegistryObject<Item> FLY_ARROWHEAD_SPAWN_EGG;
    public static final RegistryObject<Item> SKY_ADMINISTRATOR_SPAWN_EGG;
    public static final RegistryObject<Item> DARK_WORM_SPAWN_EGG;
    public static final RegistryObject<Item> PAGE_WRAITH_SPAWN_EGG;
    public static final RegistryObject<Item> PAGE_GNAT_SPAWN_EGG;
    public static final RegistryObject<Item> FIREARM_TIGER_GUARD_SPAWN_EGG;

    static {
        for (String id : LEGACY_REGISTRATION_ORDER) {
            if (ALL_ITEMS.containsKey(id)) {
                continue;
            }

            RegistryObject<Item> entry = null;

            if (id.equals("forgingfurnac")) {
                entry = ITEMS.register(id, () -> new BlockItem(ModBlocks.FORGING_FURNAC.get(), new Item.Properties()));
                TOPWORLD_BLOCK_ITEMS.put(id, entry);
            } else if (ModBlocks.TOPWORLD_BLOCKS.containsKey(id)) {
                entry = ITEMS.register(id, () -> new BlockItem(ModBlocks.get(id), new Item.Properties()));
                TOPWORLD_BLOCK_ITEMS.put(id, entry);
            } else if (MAIN_SPAWN_EGG_IDS.contains(id)) {
                entry = ITEMS.register(id, () -> createMainSpawnEgg(id));
                SPAWN_EGGS.put(id, entry);
            } else if (isKnownNonBlockItem(id)) {
                entry = ITEMS.register(id, () -> createKnownNonBlockItem(id));
                classifyNonBlockItem(id, entry);
            }

            if (entry != null) {
                ALL_ITEMS.put(id, entry);
            }
        }

        PYROTEMPER_DUST = require("pyrotemperdust");
        SKY_EMBLEM = require("splendourablazecompass");
        FORGING_FURNAC = require("forgingfurnac");
        CROAKER_BUCKET = require("croakerbucket");
        KOI_FISH_BUCKET = require("koifishbucket");
        TIN_NUGGET = require("tinnugget");
        LEAD_NUGGET = require("leadnugget");
        BRONZE_INGOT = require("bronze_ingot");
        TIN_INGOT = require("tin_ingot");
        PEWTER_INGOT = require("pewter_ingot");
        MAGNETITE = require("magnetite");
        MAGNETIC_STEEL_INGOT = require("magnetic_steel_ingot");
        DAMASCENE_STEEL_INGOT = require("damascene_steel_ingot");
        CUPRONICKEL_INGOT = require("cupronickel_ingot");
        MOLD_TIN_INGOT = require("mold_tin_ingot");
        SIMILAR_TIN = require("similartin");
        DEMON_COPPER = require("demoncopper");
        DARK_SALTPETER = require("darksaltpeter");

        PHEASANT_SPAWN_EGG = require("pheasant_spawn_egg");
        MUSK_DEER_SPAWN_EGG = require("muskdeer_spawn_egg");
        WATER_BUFFALO_SPAWN_EGG = require("waterbuffalo_spawn_egg");
        KOI_FISH_SPAWN_EGG = require("koifish_spawn_egg");
        MAGPIE_SPAWN_EGG = require("magpie_spawn_egg");
        PEACOCK_SPAWN_EGG = require("peacock_spawn_egg");
        RACCOON_DOG_SPAWN_EGG = require("raccoondog_spawn_egg");
        CROAKER_SPAWN_EGG = require("croaker_spawn_egg");
        RUSTED_ANCESTORS_SPAWN_EGG = require("rustedancestors_spawn_egg");
        RUST_RELICS_SPAWN_EGG = require("rustrelics_spawn_egg");
        RUST_HOUND_SPAWN_EGG = require("rusthound_spawn_egg");
        MESSENGER_SPAWN_EGG = require("messenger_spawn_egg");
        GIRL_GHOST_SPAWN_EGG = require("girlghost_spawn_egg");
        GOLDEN_HAIR_HOU_SPAWN_EGG = require("goldenhairhou_spawn_egg");
        RUSTED_CHEF_SPAWN_EGG = require("rustedchef_spawn_egg");
        CAULDRON_BEAST_SPAWN_EGG = require("cauldronbeast_spawn_egg");
        TERRACOTTA_WARRIORS_GUARD_SPAWN_EGG = require("terracottawarriorsguard_spawn_egg");
        TERRACOTTA_GENERAL_SPAWN_EGG = require("terracottageneral_spawn_egg");
        TERRACOTTA_WARRIORS_SPAWN_EGG = require("terracottawarriors_spawn_egg");
        ALIVE_PICTOGRAPH_SPAWN_EGG = require("alivepictograph_spawn_egg");
        CAST_INSCRIBED_AUTOMATON_SPAWN_EGG = require("castinscribedautomaton_spawn_egg");
        FLY_ARROWHEAD_SPAWN_EGG = require("flyarrowhead_spawn_egg");
        SKY_ADMINISTRATOR_SPAWN_EGG = require("skyadministrator_spawn_egg");
        DARK_WORM_SPAWN_EGG = require("darkworm_spawn_egg");
        PAGE_WRAITH_SPAWN_EGG = require("pagewraith_spawn_egg");
        PAGE_GNAT_SPAWN_EGG = require("pagegnat_spawn_egg");
        FIREARM_TIGER_GUARD_SPAWN_EGG = require("firearmtigerguard_spawn_egg");
    }

    public static RegistryObject<Item> byId(String id) {
        RegistryObject<Item> item = ALL_ITEMS.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Unknown migrated item " + id);
        }
        return item;
    }

    public static RegistryObject<Item> byIdOrNull(String id) {
        return ALL_ITEMS.get(id);
    }

    private static RegistryObject<Item> require(String id) {
        RegistryObject<Item> item = ALL_ITEMS.get(id);
        if (item == null) {
            throw new IllegalStateException("Legacy item was not registered: " + id);
        }
        return item;
    }

    private static boolean isKnownNonBlockItem(String id) {
        if (id.equals("pyrotemperdust")
                || id.equals("splendourablazecompass")
                || id.equals("croakerbucket")
                || id.equals("koifishbucket")
                || id.equals("leek")
                || id.equals("beefstew")
                || id.equals("potherbsoup")
                || id.equals("mottledbamboo")
                || id.equals("tetragonalbludgeon")
                || EXTRA_ITEM_IDS.contains(id)
                || LegacyRegistryData.SIMPLE_ITEM_IDS.contains(id)
                || isMaterialId(id)
                || isToolId(id)
                || isArmorId(id)) {
            return true;
        }
        return false;
    }

    private static void classifyNonBlockItem(String id, RegistryObject<Item> entry) {
        if (EXTRA_ITEM_IDS.contains(id)) {
            LEGACY_EXTRA_ITEMS.put(id, entry);
        } else if (isToolId(id) || id.equals("tetragonalbludgeon")) {
            LEGACY_TOOLS.put(id, entry);
        } else if (isArmorId(id)) {
            LEGACY_ARMOR.put(id, entry);
        } else {
            LEGACY_SIMPLE_ITEMS.put(id, entry);
        }
    }

    private static Item createKnownNonBlockItem(String id) {
        if (id.equals("pyrotemperdust")) {
            return new Item(new Item.Properties().rarity(Rarity.UNCOMMON));
        }
        if (id.equals("splendourablazecompass")) {
            return new SkyEmblemItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
        }
        if (id.equals("croakerbucket")) {
            return new MobBucketItem(
                    ModEntities.CROAKER,
                    () -> Fluids.WATER,
                    () -> SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1)
            );
        }
        if (id.equals("koifishbucket")) {
            return new MobBucketItem(
                    ModEntities.KOI_FISH,
                    () -> Fluids.WATER,
                    () -> SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1)
            );
        }
        if (id.equals("leek")) {
            return new LegacyNoAnimationFoodItem(
                    new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(food(1, 0.5F, false))
            );
        }
        if (id.equals("beefstew")) {
            return new BowlFoodItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).food(food(8, 3.0F, false))
            );
        }
        if (id.equals("potherbsoup")) {
            return new BowlFoodItem(
                    new Item.Properties().stacksTo(16).rarity(Rarity.COMMON).food(food(4, 1.0F, false))
            );
        }
        if (id.equals("mottledbamboo")) {
            return new MottledBambooItem(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
        }
        if (id.equals("tetragonalbludgeon")) {
            return createTetragonalBludgeon();
        }
        if (EXTRA_ITEM_IDS.contains(id)) {
            return createRestoredExtraItem(id);
        }
        if (isMaterialId(id)) {
            return new Item(new Item.Properties().stacksTo(64).rarity(materialRarity(id)));
        }
        if (isToolId(id)) {
            return createTool(toolFamily(id), toolKind(id));
        }
        if (isArmorId(id)) {
            return createArmor(armorFamily(id), armorType(id));
        }
        if (LegacyRegistryData.SIMPLE_ITEM_IDS.contains(id)) {
            return new Item(properties(id));
        }
        throw new IllegalArgumentException("Unknown legacy item factory: " + id);
    }

    private static Item createMainSpawnEgg(String id) {
        return switch (id) {
            case "pheasant_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.PHEASANT, 0x938585, 0xAF1025, new Item.Properties());
            case "muskdeer_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.MUSK_DEER, 0x957753, 0x4C4237, new Item.Properties());
            case "waterbuffalo_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.WATER_BUFFALO, 0x87917F, 0x57563E, new Item.Properties());
            case "koifish_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.KOI_FISH, 0xF2D7D7, 0xC33F11, new Item.Properties());
            case "magpie_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.MAGPIE, 0x000033, 0xCCCCCC, new Item.Properties());
            case "peacock_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.PEACOCK, 0x0033CC, 0x009966, new Item.Properties());
            case "raccoondog_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.RACCOON_DOG, 0xDFCDC1, 0x54453B, new Item.Properties());
            case "croaker_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.CROAKER, 0xAEAE82, 0xBF9643, new Item.Properties());
            case "rustedancestors_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.RUSTED_ANCESTORS, 0xE19A1C, 0x664848, new Item.Properties());
            case "rustrelics_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.RUST_RELICS, 0x9C9889, 0x6B4028, new Item.Properties());
            case "rusthound_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.RUST_HOUND, 0xFF6600, 0x6B4028, new Item.Properties());
            case "messenger_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.MESSENGER, 0x3B362B, 0xFFEA37, new Item.Properties());
            case "girlghost_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.GIRL_GHOST, 0xC1CFCF, 0xE2FFFD, new Item.Properties());
            case "goldenhairhou_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.GOLDEN_HAIR_HOU, 0xF5E7BE, 0x7F5C45, new Item.Properties());
            case "rustedchef_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.RUSTED_CHEF, 0xCC6600, 0x464B57, new Item.Properties());
            case "cauldronbeast_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.CAULDRON_BEAST, 0xBE6513, 0xFFE789, new Item.Properties());
            case "terracottawarriorsguard_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.TERRACOTTA_WARRIORS_GUARD, 0x623C3C, 0xAC735C, new Item.Properties());
            case "terracottageneral_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.TERRACOTTA_GENERAL, 0x91604F, 0xBD0000, new Item.Properties());
            case "terracottawarriors_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.TERRACOTTA_WARRIORS, 0x623C3C, 0xAC735C, new Item.Properties());
            case "alivepictograph_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.ALIVE_PICTOGRAPH, 0x999999, 0x804D3C, new Item.Properties());
            case "castinscribedautomaton_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.CAST_INSCRIBED_AUTOMATON, 0x404448, 0xCCCCCC, new Item.Properties());
            case "flyarrowhead_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.FLY_ARROWHEAD, 0x595B5E, 0x22252C, new Item.Properties());
            case "skyadministrator_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.SKY_ADMINISTRATOR, 0xA6B4CC, 0x2E3A3E, new Item.Properties());
            case "darkworm_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.DARK_WORM, 0x2B2931, 0x0D7C2C, new Item.Properties());
            case "pagewraith_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.PAGE_WRAITH, 0xE9E2D6, 0x323030, new Item.Properties());
            case "pagegnat_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.PAGE_GNAT, 0xE3D4BA, 0x1F1C1C, new Item.Properties());
            case "firearmtigerguard_spawn_egg" -> new ForgeSpawnEggItem(ModEntities.FIREARM_TIGER_GUARD, 0x514242, 0xC4AEB8, new Item.Properties());
            default -> throw new IllegalArgumentException("Not a main legacy spawn egg: " + id);
        };
    }

    private static Item createRestoredExtraItem(String id) {
        return switch (id) {
            case "topworld" -> new Item(new Item.Properties().durability(64).rarity(Rarity.COMMON));
            case "mungbeansoup" -> new Item(
                    new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(
                            new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).alwaysEat().build()
                    )
            );
            case "positioningscroll_1", "positioningscroll_2", "positioningscroll_3",
                    "positioningscroll_4", "positioningsceoll_5", "commandflag", "commandflag_1",
                    "commandflag_2" -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
            case "initialseal" -> new Item(new Item.Properties().durability(10).fireResistant().rarity(Rarity.EPIC));
            case "ghostjadependant" -> new Item(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON));
            case "huntbow" -> new Item(new Item.Properties().durability(350).rarity(Rarity.UNCOMMON));
            case "initialmagnet" -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
            case "featheryfoambucket" -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
            case "initialhandscroll" -> new Item(new Item.Properties().durability(100).fireResistant().rarity(Rarity.EPIC));
            case "dagger" -> createDagger();
            case "tigertally" -> new Item(new Item.Properties().durability(100).rarity(Rarity.RARE));
            case "talisman_1", "talisman_2", "talisman_3", "talisman_4" ->
                    new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
            case "purplegoldbell" -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
            case "threebarrelblunderbuss", "barrelfirearm" ->
                    new Item(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON));
            case "initialfirearm" -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
            case "secretbeefstew" -> new Item(new Item.Properties().durability(5).rarity(Rarity.RARE));
            default -> new Item(new Item.Properties());
        };
    }

    private static Item createDagger() {
        Tier tier = new Tier() {
            @Override public int getUses() { return 540; }
            @Override public float getSpeed() { return 4.0F; }
            @Override public float getAttackDamageBonus() { return 6.0F; }
            @Override public int getLevel() { return 2; }
            @Override public int getEnchantmentValue() { return 15; }
            @Override public Ingredient getRepairIngredient() { return Ingredient.of(BRONZE_INGOT.get()); }
        };
        return new AxeItem(tier, 1.0F, -3.0F, new Item.Properties());
    }

    private static Item createTetragonalBludgeon() {
        Tier tier = new Tier() {
            @Override public int getUses() { return 660; }
            @Override public float getSpeed() { return 4.0F; }
            @Override public float getAttackDamageBonus() { return 3.0F; }
            @Override public int getLevel() { return 4; }
            @Override public int getEnchantmentValue() { return 2; }
            @Override public Ingredient getRepairIngredient() { return Ingredient.of(DAMASCENE_STEEL_INGOT.get()); }
        };
        return new SwordItem(tier, 3, -3.2F, new Item.Properties());
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

    private static Item createArmor(String family, ArmorItem.Type type) {
        return new LegacyArmorItem(
                family,
                type,
                () -> Ingredient.of(byId(family + "_ingot").get())
        );
    }

    private static boolean isToolId(String id) {
        for (String family : LegacyRegistryData.TOOL_FAMILIES) {
            for (String kind : new String[]{"pickaxe", "axe", "sword", "shovel", "hoe"}) {
                if (id.equals(family + "_" + kind)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String toolFamily(String id) {
        for (String family : LegacyRegistryData.TOOL_FAMILIES) {
            if (id.startsWith(family + "_")) {
                return family;
            }
        }
        throw new IllegalArgumentException(id);
    }

    private static String toolKind(String id) {
        String family = toolFamily(id);
        return id.substring(family.length() + 1);
    }

    private static boolean isArmorId(String id) {
        for (String family : LegacyRegistryData.ARMOR_FAMILIES) {
            if (id.startsWith(family + "_armor_")) {
                return true;
            }
        }
        return false;
    }

    private static String armorFamily(String id) {
        for (String family : LegacyRegistryData.ARMOR_FAMILIES) {
            if (id.startsWith(family + "_armor_")) {
                return family;
            }
        }
        throw new IllegalArgumentException(id);
    }

    private static ArmorItem.Type armorType(String id) {
        if (id.endsWith("_helmet")) return ArmorItem.Type.HELMET;
        if (id.endsWith("_chestplate")) return ArmorItem.Type.CHESTPLATE;
        if (id.endsWith("_leggings")) return ArmorItem.Type.LEGGINGS;
        if (id.endsWith("_boots")) return ArmorItem.Type.BOOTS;
        throw new IllegalArgumentException(id);
    }

    private static boolean isMaterialId(String id) {
        return switch (id) {
            case "tinnugget", "leadnugget", "bronze_ingot", "tin_ingot", "pewter_ingot",
                    "magnetite", "magnetic_steel_ingot", "damascene_steel_ingot",
                    "cupronickel_ingot", "mold_tin_ingot", "similartin", "demoncopper",
                    "darksaltpeter" -> true;
            default -> false;
        };
    }

    private static Rarity materialRarity(String id) {
        return id.equals("pewter_ingot") ? Rarity.UNCOMMON : Rarity.COMMON;
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

    private ModItems() {
    }
}
