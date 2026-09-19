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
            registerTopworldBlock(
                    "inkdirt",
                    () -> new Block(
                            BlockBehaviour.Properties.copy(
                                    Blocks.DIRT
                            )
                    )
            );

    public static final RegistryObject<Block> INK_GRASS_BLOCK =
            registerTopworldBlock(
                    "inkgrassblock",
                    () -> new Block(
                            BlockBehaviour.Properties.copy(
                                    Blocks.GRASS_BLOCK
                            )
                    )
            );

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
                            BlockBehaviour.Properties.copy(
                                    Blocks.BAMBOO
                            )
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
        if (TOPWORLD_BLOCKS.containsKey(id)) {
            return;
        }

        registerTopworldBlock(
                id,
                () -> createLegacyFallback(id)
        );
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

        if ("wetwhitewashedbricksblock".equals(id)) {
            return new LegacyVariantOneBlock(
                    stone
            );
        }

        if ("auroraleadenpillar".equals(id)) {
            return new LegacyVariantTwoBlock(
                    stone
            );
        }

        if (
                "ambiguousimmortale".equals(id)
                        || "chronsovereigntystar".equals(id)
                        || "shockflamewarlord".equals(id)
        ) {
            return new MigratedFacingBlock(
                    stone
            );
        }

        if ("simplegrave".equals(id)) {
            return new MigratedFacingBlock(
                    stone
            );
        }

        if (id.endsWith("silkblock")) {

            BlockBehaviour.Properties silk =
                    BlockBehaviour.Properties
                            .of()
                            .sound(SoundType.WOOL)
                            .strength(1.0F, 10.0F)
                            .friction(0.99F);

            return new Block(silk) {

                @Override
                public int getLightBlock(
                        BlockState state,
                        BlockGetter level,
                        BlockPos pos
                ) {
                    return 13;
                }
            };
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
        if (
                "lacqueredwood".equals(id)
                        || "thatchblock".equals(id)
        ) {
            return new RotatedPillarBlock(
                    wood
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