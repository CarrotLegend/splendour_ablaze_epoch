package net.zi_jian.splendourablazeepoch.registry;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.block.ForgingFurnacBlock;
import net.zi_jian.splendourablazeepoch.block.LegacyPillarVariantBlock;
import net.zi_jian.splendourablazeepoch.block.LegacyVariantBlock;
import net.zi_jian.splendourablazeepoch.block.LegacyVariantOneBlock;
import net.zi_jian.splendourablazeepoch.block.LegacyVariantTwoBlock;
import net.zi_jian.splendourablazeepoch.block.MigratedEntityBlock;
import net.zi_jian.splendourablazeepoch.block.MigratedFacingBlock;
import net.zi_jian.splendourablazeepoch.block.MigratedFacingEntityBlock;
import net.zi_jian.splendourablazeepoch.block.MottledBambooBudBlock;
import net.zi_jian.splendourablazeepoch.block.MottledBambooStalkBlock;
import net.zi_jian.splendourablazeepoch.block.TopworldPlantBlock;
import net.zi_jian.splendourablazeepoch.world.TopworldClosure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(
                    ForgeRegistries.BLOCKS,
                    SplendourAblazeEpochMod.MOD_ID
            );

    public static final Map<String, RegistryObject<Block>> TOPWORLD_BLOCKS =
            new LinkedHashMap<>();

    public static final RegistryObject<Block> INK_DIRT =
            registerLike("inkdirt", Blocks.DIRT);

    public static final RegistryObject<Block> INK_GRASS_BLOCK =
            registerLike("inkgrassblock", Blocks.GRASS_BLOCK);

    public static final RegistryObject<Block> FIRMROCK =
            registerLike("firmrock", Blocks.STONE);

    public static final RegistryObject<Block> POLISHED_FIRMROCK =
            registerLike("polishedfirmrock", Blocks.POLISHED_ANDESITE);

    public static final RegistryObject<Block> FIRMROCK_BRICKS =
            registerLike("firmrockbricks", Blocks.STONE_BRICKS);

    public static final RegistryObject<Block> FIRMROCK_BRICKS_SLAB =
            registerSlab("firmrockbricksslab");

    public static final RegistryObject<Block> FIRMROCK_BRICKS_STAIRS =
            registerStairs("firmrockbricksstairs", "firmrockbricks");

    public static final RegistryObject<Block> FIRMROCK_BRICK_WALL =
            registerWall("firmrockbrickwall");

    public static final RegistryObject<Block> CRACKED_FIRMROCK_BRICKS =
            registerLike("crackedfirmrockbricks", Blocks.CRACKED_STONE_BRICKS);

    public static final RegistryObject<Block> CHISELED_FIRMROCK =
            registerLike("chiseledfirmrock", Blocks.CHISELED_STONE_BRICKS);

    public static final RegistryObject<Block> MOTTLED_FIRMROCK_BRICKS =
            registerLike("mottledfirmrockbricks", Blocks.MOSSY_STONE_BRICKS);

    public static final RegistryObject<Block> BURNT_FIRMROCK_BRICKS =
            registerLike("burntfirmrockbricks", Blocks.STONE_BRICKS);

    public static final RegistryObject<Block> BURNT_POLISHED_FIRMROCK =
            registerLike("burntpolishedfirmrock", Blocks.POLISHED_ANDESITE);

    public static final RegistryObject<Block> CHARRED_FIRMROCK =
            registerLike("charredfirmrock", Blocks.STONE);

    public static final RegistryObject<Block> BLUISHSTONE =
            registerLike("bluishstone", Blocks.STONE);

    public static final RegistryObject<Block> POLISHED_BLUISHSTONE =
            registerLike("polishedbluishstone", Blocks.POLISHED_ANDESITE);

    public static final RegistryObject<Block> BLUISHSTONE_BRICKS =
            registerLike("bluishstonebricks", Blocks.STONE_BRICKS);

    public static final RegistryObject<Block> BLUISHSTONE_BRICKS_SLAB =
            registerSlab("bluishstonebricksslab");

    public static final RegistryObject<Block> BLUISHSTONE_BRICKS_STAIRS =
            registerStairs("bluishstonebricksstairs", "bluishstonebricks");

    public static final RegistryObject<Block> BLUISHSTONE_BRICKS_WALL =
            registerWall("bluishstonebrickswall");

    public static final RegistryObject<Block> CRACKED_BLUISHSTONE_BRICKS =
            registerLike("crackedbluishstonebricks", Blocks.CRACKED_STONE_BRICKS);

    public static final RegistryObject<Block> MOSSY_BLUISHSTONE_BRICKS =
            registerLike("mossybluishstonebricks", Blocks.MOSSY_STONE_BRICKS);

    public static final RegistryObject<Block> CHISELED_BLUISHSTONE_BRICKS =
            registerLike("chiseledbluishstonebricks", Blocks.CHISELED_STONE_BRICKS);

    public static final RegistryObject<Block> RIBBONROCK =
            registerLike("ribbonrock", Blocks.STONE);

    public static final RegistryObject<Block> POLISHED_RIBBONROCK =
            registerLike("polished_ribbonrock", Blocks.POLISHED_ANDESITE);

    public static final RegistryObject<Block> RIBBONROCK_BRICKS =
            registerLike("ribbonrockbricks", Blocks.STONE_BRICKS);

    public static final RegistryObject<Block> RIBBONROCK_BRICKS_SLAB =
            registerSlab("ribbonrockbricksslab");

    public static final RegistryObject<Block> RIBBONROCK_BRICKS_STAIRS =
            registerStairs("ribbonrockbricksstairs", "ribbonrockbricks");

    public static final RegistryObject<Block> RIBBONROCK_BRICKS_WALL =
            registerWall("ribbonrockbrickswall");

    public static final RegistryObject<Block> CHISELED_RIBBONROCK_BRICKS =
            registerLike("chiseledribbonrockbricks", Blocks.CHISELED_STONE_BRICKS);

    public static final RegistryObject<Block> RADIANT_LIMESTONE =
            registerLike("radiantlimestone", Blocks.CALCITE);

    public static final RegistryObject<Block> FADED_RADIANT_LIMESTONE =
            registerLike("fadedradiantlimestone", Blocks.CALCITE);

    public static final RegistryObject<Block> POLISHED_RADIANT_LIMESTONE =
            registerLike("polishedradiantlimestone", Blocks.POLISHED_DIORITE);

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE =
            registerLike("castradiantlimestone", Blocks.STONE);

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_SLAB =
            registerSlab("castradiantlimestoneslab");

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_STAIRS =
            registerStairs(
                    "castradiantlimestonestairs",
                    "castradiantlimestone"
            );

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_WALL =
            registerWall("castradiantlimestonewall");

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_BRICKS =
            registerLike("castradiantlimestonebricks", Blocks.STONE_BRICKS);

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_BRICKS_SLAB =
            registerSlab("castradiantlimestonebricksslab");

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_BRICKS_STAIRS =
            registerStairs(
                    "castradiantlimestonebricksstairs",
                    "castradiantlimestonebricks"
            );

    public static final RegistryObject<Block> CAST_RADIANT_LIMESTONE_BRICKS_WALL =
            registerWall("castradiantlimestonebrickswall");

    public static final RegistryObject<Block> CHISELED_CAST_RADIANT_LIMESTONE =
            registerLike(
                    "chiseledcastradiantlimestone",
                    Blocks.CHISELED_STONE_BRICKS
            );

    public static final RegistryObject<Block> POLISHED_CAST_RADIANT_LIMESTONE =
            registerLike(
                    "polishedcastradiantlimestone",
                    Blocks.POLISHED_DIORITE
            );

    public static final RegistryObject<Block> POLISHED_FIGURE_CAST_RADIANT_LIMESTONE =
            registerLike(
                    "polishedfigurecastradiantlimestone",
                    Blocks.POLISHED_DIORITE
            );

    public static final RegistryObject<Block> CUT_POLISHED_CAST_RADIANT_LIMESTONE =
            registerLike(
                    "cutpolishedcastradiantlimestone",
                    Blocks.CUT_SANDSTONE
            );

    public static final RegistryObject<Block> CUT_POLISHED_CAST_RADIANT_LIMESTONE_SLAB =
            registerSlab("cutpolishedcastradiantlimestoneslab");

    public static final RegistryObject<Block> CUT_POLISHED_CAST_RADIANT_LIMESTONE_STAIRS =
            registerStairs(
                    "cutpolishedcastradiantlimestonestairs",
                    "cutpolishedcastradiantlimestone"
            );

    public static final RegistryObject<Block> DARKSTEEL_STONE =
            registerLike("darksteelstone", Blocks.DEEPSLATE);

    public static final RegistryObject<Block> DARKSTEEL_BRICKS =
            registerLike("darksteelbricks", Blocks.DEEPSLATE_BRICKS);

    public static final RegistryObject<Block> DARKSTEEL_BRICKS_SLAB =
            registerSlab("darksteelbricksslab");

    public static final RegistryObject<Block> DARKSTEEL_BRICKS_STAIRS =
            registerStairs("darksteelbricksstairs", "darksteelbricks");

    public static final RegistryObject<Block> DARKSTEEL_BRICKS_WALL =
            registerWall("darksteelbrickswall");

    public static final RegistryObject<Block> CHISELED_DARKSTEEL_BRICKS =
            registerLike("chiseleddarksteelbricks", Blocks.CHISELED_DEEPSLATE);

    public static final RegistryObject<Block> PATTERNED_SILVER_DARKSTEEL_BRICKS =
            registerLike(
                    "patternedsilverdarksteelbricks",
                    Blocks.DEEPSLATE_BRICKS
            );

    public static final RegistryObject<Block> TERRACOTTA_BRICKS =
            registerLike("terracottabricks", Blocks.BRICKS);

    public static final RegistryObject<Block> TERRACOTTA_BRICKS_SLAB =
            registerSlab("terracottabricksslab");

    public static final RegistryObject<Block> TERRACOTTA_BRICKS_STAIRS =
            registerStairs("terracottabricksstairs", "terracottabricks");

    public static final RegistryObject<Block> MOTTLED_TERRACOTTA_BRICKS =
            registerLike("mottledterracottabricks", Blocks.BRICKS);

    public static final RegistryObject<Block> CARVING_TERRACOTTA_BRICKS =
            registerLike(
                    "carvingterracottabricks",
                    Blocks.CHISELED_STONE_BRICKS
            );

    public static final RegistryObject<Block> POTTERY_TILE =
            registerLike("potterytile", Blocks.TERRACOTTA);

    public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICKS =
            registerLike("whiteterracottabricks", Blocks.WHITE_TERRACOTTA);

    public static final RegistryObject<Block> AURORA_LEADEN_BLOCK =
            registerLike("auroraleadenblock", Blocks.STONE);

    public static final RegistryObject<Block> AURORA_LEADEN_BLOCK_SLAB =
            registerSlab("auroraleadenblockslab");

    public static final RegistryObject<Block> AURORA_LEADEN_BLOCK_STAIRS =
            registerStairs(
                    "auroraleadenblockstairs",
                    "auroraleadenblock"
            );

    public static final RegistryObject<Block> AURORA_LEADEN_BLOCK_WALL =
            registerWall("auroraleadenblockwall");

    public static final RegistryObject<Block> AURORA_LEADEN_TILE =
            registerLike("auroraleadentile", Blocks.STONE);

    public static final RegistryObject<Block> BRONZE_BLOCK =
            registerLike("bronze_block", Blocks.COPPER_BLOCK);

    public static final RegistryObject<Block> TIN_BLOCK =
            registerLike("tin_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> LEAD_BLOCK =
            registerLike("lead_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> PEWTER_BLOCK =
            registerLike("pewter_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> CUPRONICKEL_BLOCK =
            registerLike("cupronickel_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> MAGNETIC_STEEL_BLOCK =
            registerLike("magnetic_steel_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> DAMASCENE_STEEL_BLOCK =
            registerLike("damascene_steel_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> MOLD_TIN_BLOCK =
            registerLike("mold_tin_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> CELES_TERRAL_BLOCK =
            registerLike("celes_terral_block", Blocks.NETHERITE_BLOCK);

    public static final RegistryObject<Block> DEMON_COPPER_BLOCK =
            registerLike("demoncopperblock", Blocks.COPPER_BLOCK);

    public static final RegistryObject<Block> MAGNETITE_BLOCK =
            registerLike("magnetiteblock", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> SIMILAR_TIN_BLOCK =
            registerLike("similartinblock", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> SILVER_BLOCK =
            registerLike("sliverblock", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> RAW_LEAD_BLOCK =
            registerLike("rawleadblock", Blocks.RAW_IRON_BLOCK);

    public static final RegistryObject<Block> RAW_TIN_BLOCK =
            registerLike("rawtinblock", Blocks.RAW_IRON_BLOCK);

    public static final RegistryObject<Block> CINNABAR_BLOCK =
            registerLike("cinnabarblock", Blocks.REDSTONE_BLOCK);

    public static final RegistryObject<Block> GRAPHITE_BLOCK =
            registerLike("graphiteblock", Blocks.COAL_BLOCK);

    public static final RegistryObject<Block> JADE_BLOCK =
            registerLike("jadeblock", Blocks.EMERALD_BLOCK);

    public static final RegistryObject<Block> CRYSTAL_STONE =
            registerLike("crystalstone", Blocks.AMETHYST_BLOCK);

    public static final RegistryObject<Block> FIRMROCK_COAL_ORE =
            registerLike("firmrockcoalore", Blocks.COAL_ORE);

    public static final RegistryObject<Block> FIRMROCK_IRON_ORE =
            registerLike("firmrockironore", Blocks.IRON_ORE);

    public static final RegistryObject<Block> FIRMROCK_SALTPETER_ORE =
            registerLike("firmrocksaltpeterore", Blocks.COAL_ORE);

    public static final RegistryObject<Block> FIRMROCK_DARK_SALTPETER_ORE =
            registerLike("firmrockdarksaltpeterore", Blocks.COAL_ORE);

    public static final RegistryObject<Block> FIRMROCK_SULFUR_ORE =
            registerLike("firmrocksulfurore", Blocks.COAL_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_CINNABAR_ORE =
            registerLike("radiantlimestonecinnabarore", Blocks.REDSTONE_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_COPPER_ORE =
            registerLike("radiantlimestonecopperore", Blocks.COPPER_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_GOLD_ORE =
            registerLike("radiantlimestonegoldore", Blocks.GOLD_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_GRAPHITE_ORE =
            registerLike("radiantlimestonegraphiteore", Blocks.COAL_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_IRON_ORE =
            registerLike("radiantlimestoneironore", Blocks.IRON_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_LEAD_ORE =
            registerLike("radiantlimestoneleadore", Blocks.IRON_ORE);

    public static final RegistryObject<Block> RADIANT_LIMESTONE_TIN_ORE =
            registerLike("radiantlimestonetinore", Blocks.IRON_ORE);

    public static final RegistryObject<Block> RIBBONROCK_COAL_ORE =
            registerLike("ribbonrockcoalore", Blocks.COAL_ORE);

    public static final RegistryObject<Block> RIBBONROCK_COPPER_ORE =
            registerLike("ribbonrockcopperore", Blocks.COPPER_ORE);

    public static final RegistryObject<Block> RIBBONROCK_SILVER_ORE =
            registerLike("ribbonrocksilverore", Blocks.IRON_ORE);

    public static final RegistryObject<Block> MAGNETITE_RIBBONROCK_ORE =
            registerLike("magnetiteribbonrockore", Blocks.IRON_ORE);

    public static final RegistryObject<Block> CHARRED_SAND =
            registerTopworldBlock(
                    "charredsand",
                    () -> new FallingBlock(
                            BlockBehaviour.Properties.copy(Blocks.SAND)
                    )
            );

    public static final RegistryObject<Block> JADE_SAND =
            registerTopworldBlock(
                    "jadesand",
                    () -> new FallingBlock(
                            BlockBehaviour.Properties.copy(Blocks.SAND)
                    )
            );

    public static final RegistryObject<Block> POTTERY_CLAY =
            registerLike("potteryclay", Blocks.CLAY);

    public static final RegistryObject<Block> FLINTSTONE =
            registerLike("flintstone", Blocks.COBBLESTONE);

    public static final RegistryObject<Block> RUSTED_MOSS =
            registerLike("rustedmoss", Blocks.MOSS_BLOCK);

    public static final RegistryObject<Block> DARK_TILE =
            registerLike("darktile", Blocks.DEEPSLATE_TILES);

    public static final RegistryObject<Block> DARK_TILE_SLAB =
            registerTopworldBlock(
                    "darktileslab",
                    () -> new SlabBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.DEEPSLATE_TILE_SLAB
                            )
                    )
            );

    public static final RegistryObject<Block> DARK_TILE_STAIRS =
            registerTopworldBlock(
                    "darktilestairs",
                    () -> new StairBlock(
                            () -> DARK_TILE.get().defaultBlockState(),
                            BlockBehaviour.Properties.copy(
                                    Blocks.DEEPSLATE_TILE_STAIRS
                            )
                    )
            );

    public static final RegistryObject<Block> DARK_TILE_WALL =
            registerTopworldBlock(
                    "darktilewall",
                    () -> new WallBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.DEEPSLATE_TILE_WALL
                            )
                    )
            );

    public static final RegistryObject<Block> THATCH_BLOCK =
            registerTopworldBlock(
                    "thatchblock",
                    () -> new RotatedPillarBlock(
                            BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)
                    )
            );

    public static final RegistryObject<Block> THATCH_BLOCK_SLAB =
            registerTopworldBlock(
                    "thatchblockslab",
                    () -> new SlabBlock(
                            BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)
                    )
            );

    public static final RegistryObject<Block> THATCH_BLOCK_STAIRS =
            registerTopworldBlock(
                    "thatchblockstairs",
                    () -> new StairBlock(
                            () -> THATCH_BLOCK.get().defaultBlockState(),
                            BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)
                    )
            );

    public static final RegistryObject<Block> LACQUERED_WOOD =
            registerTopworldBlock(
                    "lacqueredwood",
                    () -> new RotatedPillarBlock(
                            BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    )
            );

    public static final RegistryObject<Block> WHITEWASHED_BRICKS_BLOCK =
            registerLike(
                    "whitewashedbricksblock",
                    Blocks.STONE_BRICKS
            );

    public static final RegistryObject<Block> MOSSY_WHITEWASHED_BRICKS_BLOCK =
            registerLike(
                    "mossywhitewashedbricksblock",
                    Blocks.MOSSY_STONE_BRICKS
            );

    public static final RegistryObject<Block> PATCHY_WHITEWASHED_BRICKS_BLOCK =
            registerLike(
                    "patchywhitewashedbricksblock",
                    Blocks.STONE_BRICKS
            );

    public static final RegistryObject<Block> WET_WHITEWASHED_BRICKS_BLOCK =
            registerTopworldBlock(
                    "wetwhitewashedbricksblock",
                    () -> new LegacyVariantOneBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.STONE_BRICKS
                            )
                    )
            );

    public static final RegistryObject<Block> SILK_BLOCK =
            registerSilk("silkblock");

    public static final RegistryObject<Block> BLACK_SILK_BLOCK =
            registerSilk("blacksilkblock");

    public static final RegistryObject<Block> RED_SILK_BLOCK =
            registerSilk("redsilkblock");

    public static final RegistryObject<Block> BLUE_SILK_BLOCK =
            registerSilk("bluesilkblock");

    public static final RegistryObject<Block> BROWN_SILK_BLOCK =
            registerSilk("brownsilkblock");

    public static final RegistryObject<Block> CYAN_SILK_BLOCK =
            registerSilk("cyansilkblock");

    public static final RegistryObject<Block> GRAY_SILK_BLOCK =
            registerSilk("graysilkblock");

    public static final RegistryObject<Block> GREEN_SILK_BLOCK =
            registerSilk("greensilkblock");

    public static final RegistryObject<Block> LIGHT_BLUE_SILK_BLOCK =
            registerSilk("lightbluesilkblock");

    public static final RegistryObject<Block> LIGHT_GRAY_SILK_BLOCK =
            registerSilk("lightgraysilkblock");

    public static final RegistryObject<Block> LIME_SILK_BLOCK =
            registerSilk("limesilkblock");

    public static final RegistryObject<Block> MAGENTA_SILK_BLOCK =
            registerSilk("magentasilkblock");

    public static final RegistryObject<Block> ORANGE_SILK_BLOCK =
            registerSilk("orangesilkblock");

    public static final RegistryObject<Block> PINK_SILK_BLOCK =
            registerSilk("pinksilkblock");

    public static final RegistryObject<Block> PURPLE_SILK_BLOCK =
            registerSilk("purplesilkblock");

    public static final RegistryObject<Block> YELLOW_SILK_BLOCK =
            registerSilk("yellowsilkblock");

    public static final RegistryObject<Block> MOTTLED_BAMBOO_BUD =
            registerTopworldBlock(
                    "mottledbamboobud",
                    () -> new MottledBambooBudBlock(
                            BlockBehaviour.Properties
                                    .copy(Blocks.BAMBOO_SAPLING)
                                    .randomTicks()
                    )
            );

    public static final RegistryObject<Block> MOTTLED_BAMBOO_STALK =
            registerTopworldBlock(
                    "mottledbamboos",
                    () -> new MottledBambooStalkBlock(
                            BlockBehaviour.Properties.copy(Blocks.BAMBOO)
                    )
            );

    public static final RegistryObject<Block> ARTEMISIA =
            registerSimplePlant("artemisia");

    public static final RegistryObject<Block> BASELLA =
            registerSimplePlant("basella");

    public static final RegistryObject<Block> BELLFLOWER =
            registerSimplePlant("bellflower");

    public static final RegistryObject<Block> CLOUDBERRY =
            registerSimplePlant("cloudberry");

    public static final RegistryObject<Block> CYAN_TWIG =
            registerSimplePlant("cyantwig");

    public static final RegistryObject<Block> HOSTA =
            registerSimplePlant("hosta");

    public static final RegistryObject<Block> HYDRANGEA =
            registerSimplePlant("hydrangea");

    public static final RegistryObject<Block> OPHIOPOGON =
            registerSimplePlant("ophiopogon");

    public static final RegistryObject<Block> OSMUNDA =
            registerSimplePlant("osmunda");

    public static final RegistryObject<Block> PORTULACA =
            registerSimplePlant("portulaca");

    public static final RegistryObject<Block> SAXIFRAGE =
            registerSimplePlant("saxifrage");

    public static final RegistryObject<Block> SCABISH =
            registerSimplePlant("scabish");

    public static final RegistryObject<Block> STRAW_MUSHROOM =
            registerSimplePlant("strawmushroom");

    public static final RegistryObject<Block> STUBBLE_AWN =
            registerSimplePlant("stubbleawn");

    public static final RegistryObject<Block> WHITE_FLEABANE =
            registerSimplePlant("whitefleabane");

    public static final RegistryObject<Block> WHITE_STINKHORN =
            registerSimplePlant("whitestinkhorn");

    public static final RegistryObject<Block> YELLOW_HEMP =
            registerSimplePlant("yellowhemp");

    public static final RegistryObject<Block> COGON_GRASS =
            registerDoublePlant("cogongrass");

    public static final RegistryObject<Block> HOLLYHOCK =
            registerDoublePlant("hollyhock");

    public static final RegistryObject<Block> TALL_CYAN_TWIG =
            registerDoublePlant("tallcyantwig");

    public static final RegistryObject<Block> FORGING_FURNAC =
            BLOCKS.register(
                    "forgingfurnac",
                    ForgingFurnacBlock::new
            );

    static {
        TopworldWoodRegistry.registerAll();

        for (String id : TopworldClosure.BLOCK_IDS) {
            registerLegacyFallback(id);
        }

        for (String id : LegacyRegistryData.RESTORED_BLOCK_IDS) {
            registerLegacyFallback(id);
        }
    }

    private static RegistryObject<Block> registerLike(
            String id,
            Block source
    ) {
        return registerTopworldBlock(
                id,
                () -> new Block(
                        BlockBehaviour.Properties.copy(source)
                )
        );
    }

    private static RegistryObject<Block> registerStairs(
            String id,
            String baseId
    ) {
        return registerTopworldBlock(
                id,
                () -> new StairBlock(
                        () -> get(baseId).defaultBlockState(),
                        BlockBehaviour.Properties.copy(
                                Blocks.STONE_STAIRS
                        )
                )
        );
    }

    private static RegistryObject<Block> registerSlab(
            String id
    ) {
        return registerTopworldBlock(
                id,
                () -> new SlabBlock(
                        BlockBehaviour.Properties.copy(
                                Blocks.STONE_SLAB
                        )
                )
        );
    }

    private static RegistryObject<Block> registerWall(
            String id
    ) {
        return registerTopworldBlock(
                id,
                () -> new WallBlock(
                        BlockBehaviour.Properties.copy(
                                Blocks.COBBLESTONE_WALL
                        )
                )
        );
    }

    private static RegistryObject<Block> registerSilk(
            String id
    ) {
        return registerTopworldBlock(
                id,
                () -> new Block(
                        BlockBehaviour.Properties.of()
                                .sound(SoundType.WOOL)
                                .strength(1.0F, 10.0F)
                                .friction(0.99F)
                ) {
                    @Override
                    public int getLightBlock(
                            BlockState state,
                            BlockGetter level,
                            BlockPos pos
                    ) {
                        return 13;
                    }
                }
        );
    }

    private static RegistryObject<Block> registerSimplePlant(
            String id
    ) {
        return registerTopworldBlock(
                id,
                () -> new TopworldPlantBlock(
                        BlockBehaviour.Properties.copy(
                                Blocks.GRASS
                        )
                )
        );
    }

    private static RegistryObject<Block> registerDoublePlant(
            String id
    ) {
        return registerTopworldBlock(
                id,
                () -> new DoublePlantBlock(
                        BlockBehaviour.Properties.copy(
                                Blocks.TALL_GRASS
                        )
                )
        );
    }

    static RegistryObject<Block> registerTopworldBlock(
            String id,
            Supplier<Block> factory
    ) {
        if (TOPWORLD_BLOCKS.containsKey(id)) {
            throw new IllegalStateException(
                    "Duplicate block registration: "
                            + SplendourAblazeEpochMod.MOD_ID
                            + ":"
                            + id
            );
        }

        RegistryObject<Block> registered =
                BLOCKS.register(
                        id,
                        factory
                );

        TOPWORLD_BLOCKS.put(
                id,
                registered
        );

        return registered;
    }

    private static void registerLegacyFallback(
            String id
    ) {
        if (!TOPWORLD_BLOCKS.containsKey(id)) {
            registerTopworldBlock(
                    id,
                    () -> createLegacyFallback(id)
            );
        }
    }

    private static Block createLegacyFallback(
            String id
    ) {
        BlockBehaviour.Properties stone =
                BlockBehaviour.Properties
                        .of()
                        .strength(1.5F, 6.0F)
                        .sound(SoundType.STONE);

        BlockBehaviour.Properties wood =
                BlockBehaviour.Properties
                        .of()
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD);

        if ("gnome".equals(id)) {
            return new MigratedFacingEntityBlock(
                    id,
                    wood
            );
        }

        if (TopworldClosure.BLOCK_ENTITY_IDS.contains(id)) {
            return new MigratedEntityBlock(
                    id,
                    wood
            );
        }

        if ("bluishstonepillar".equals(id)) {
            return new LegacyPillarVariantBlock(
                    stone
            );
        }

        if ("leekplant".equals(id)) {
            return new LegacyVariantTwoBlock(
                    stone
            );
        }

        if ("milletplant".equals(id)) {
            return new LegacyVariantBlock(
                    stone
            );
        }

        if ("auroraleadenpillar".equals(id)) {
            return new LegacyVariantTwoBlock(
                    stone
            );
        }

        if ("ambiguousimmortale".equals(id)
                || "chronsovereigntystar".equals(id)
                || "shockflamewarlord".equals(id)
                || "simplegrave".equals(id)) {
            return new MigratedFacingBlock(
                    stone
            );
        }

        if (id.endsWith("stairs")) {
            return new StairBlock(
                    () -> Blocks.STONE.defaultBlockState(),
                    stone
            );
        }

        if (id.endsWith("slab")) {
            return new SlabBlock(
                    stone
            );
        }

        if (id.endsWith("wall")) {
            return new WallBlock(
                    stone
            );
        }

        return new Block(
                stone
        );
    }

    public static Block get(
            String id
    ) {
        RegistryObject<Block> value =
                TOPWORLD_BLOCKS.get(id);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Unknown topworld block "
                            + SplendourAblazeEpochMod.MOD_ID
                            + ":"
                            + id
            );
        }

        return value.get();
    }

    private ModBlocks() {
    }
}