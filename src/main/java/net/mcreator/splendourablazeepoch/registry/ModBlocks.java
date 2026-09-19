package net.mcreator.splendourablazeepoch.registry;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.block.MigratedEntityBlock;
import net.mcreator.splendourablazeepoch.block.MigratedFacingBlock;
import net.mcreator.splendourablazeepoch.block.MigratedFacingEntityBlock;
import net.mcreator.splendourablazeepoch.block.ForgingFurnacBlock;
import net.mcreator.splendourablazeepoch.block.LegacyPillarVariantBlock;
import net.mcreator.splendourablazeepoch.block.LegacyVariantBlock;
import net.mcreator.splendourablazeepoch.block.LegacyVariantOneBlock;
import net.mcreator.splendourablazeepoch.block.LegacyVariantTwoBlock;
import net.mcreator.splendourablazeepoch.world.TopworldClosure;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<Block> FORGING_FURNAC = BLOCKS.register("forgingfurnac", ForgingFurnacBlock::new);
    public static final Map<String, RegistryObject<Block>> TOPWORLD_BLOCKS = new LinkedHashMap<>();

    static {
        for (String id : TopworldClosure.BLOCK_IDS) {
            TOPWORLD_BLOCKS.put(id, BLOCKS.register(id, () -> create(id)));
        }
        for (String id : LegacyRegistryData.RESTORED_BLOCK_IDS) {
            TOPWORLD_BLOCKS.put(id, BLOCKS.register(id, () -> create(id)));
        }
    }

    private static Block create(String id) {
        BlockBehaviour.Properties stone = BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE);
        BlockBehaviour.Properties wood = BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD);
        boolean wooden = isWooden(id);
        BlockBehaviour.Properties base = wooden ? wood : stone;
        if ("gnome".equals(id)) return new MigratedFacingEntityBlock(id, wood);
        if (TopworldClosure.BLOCK_ENTITY_IDS.contains(id)) return new MigratedEntityBlock(id, wood);
        if (id.equals("bluishstonepillar")) return new LegacyPillarVariantBlock(stone);
        if (id.equals("leekplant")) return new LegacyVariantTwoBlock(base);
        if (id.equals("milletplant")) return new LegacyVariantBlock(base);
        if (id.equals("wetwhitewashedbricksblock")) return new LegacyVariantOneBlock(base);
        if (id.equals("auroraleadenpillar")) return new LegacyVariantTwoBlock(base);
        if (id.equals("ambiguousimmortale") || id.equals("chronsovereigntystar") || id.equals("shockflamewarlord")) return new MigratedFacingBlock(base);
        if (id.endsWith("silkblock")) {
            BlockBehaviour.Properties silk = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(1.0F, 10.0F).friction(0.99F);
            return new Block(silk) {
                @Override public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) { return 13; }
            };
        }
        if (id.endsWith("stairs")) return new StairBlock(() -> wooden ? Blocks.OAK_PLANKS.defaultBlockState() : Blocks.STONE.defaultBlockState(), base);
        if (id.endsWith("slab")) return new SlabBlock(base);
        if (id.endsWith("wall")) return new WallBlock(base);
        if (id.endsWith("fence_gate")) return new FenceGateBlock(wood, WoodType.OAK);
        if (id.endsWith("_fence")) return new FenceBlock(wood);
        if (id.endsWith("trapdoor") || id.endsWith("tarpdoor")) return new TrapDoorBlock(wood, BlockSetType.OAK);
        if (id.endsWith("door")) return new DoorBlock(wood.noOcclusion(), BlockSetType.OAK);
        if (id.endsWith("pressure_plate")) return new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, wood, BlockSetType.OAK);
        if (id.endsWith("button")) return new ButtonBlock(wood.noCollission(), BlockSetType.OAK, 30, true);
        if (id.endsWith("sapling")) return new SaplingBlock(new OakTreeGrower(), BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
        if (id.equals("cogongrass") || id.equals("hollyhock") || id.equals("tallcyantwig")) return new DoublePlantBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS));
        if (id.equals("simplegrave")) return new MigratedFacingBlock(stone);
        if (id.endsWith("_log") || id.endsWith("_wood") || id.startsWith("stripped") || id.equals("lacqueredwood") || id.equals("thatchblock") || id.equals("mottledbambooblock")) return new RotatedPillarBlock(wood);
        if (id.contains("leaves")) return new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn((s,l,p,t)->false).isSuffocating((s,l,p)->false).isViewBlocking((s,l,p)->false));
        return new Block(base);
    }

    private static boolean isWooden(String id) {
        return id.contains("wutong") || id.contains("ginkgo") || id.contains("cerasus")
                || id.contains("osmanthus") || id.contains("mulberry") || id.contains("mottled_bamboo")
                || id.contains("mottledbamboo") || id.contains("dragonsophora") || id.contains("decayed")
                || id.contains("hibiscus") || id.contains("specular_pine") || id.contains("specularpine")
                || id.endsWith("bookshelf");
    }

    public static Block get(String id) {
        RegistryObject<Block> value = TOPWORLD_BLOCKS.get(id);
        if (value == null) throw new IllegalArgumentException("Unknown topworld block " + id);
        return value.get();
    }

    private ModBlocks() {
    }
}
