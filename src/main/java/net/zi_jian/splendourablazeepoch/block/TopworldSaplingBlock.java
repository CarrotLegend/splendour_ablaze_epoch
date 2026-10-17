package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

import java.util.function.Supplier;

public final class TopworldSaplingBlock extends SaplingBlock {

    private final Supplier<? extends Block> extraSoil;
    private final String structure;

    public TopworldSaplingBlock(
            AbstractTreeGrower grower,
            BlockBehaviour.Properties properties,
            Supplier<? extends Block> extraSoil,
            String structure
    ) {
        super(grower, properties);
        this.extraSoil = extraSoil;
        this.structure = structure;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (structure == null) {
            super.randomTick(state, level, pos, random);
        } else {
            tryGrow(level, pos, random);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return structure != null || super.isValidBonemealTarget(level, pos, state, isClient);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return structure != null || super.isBonemealSuccess(level, random, pos, state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (structure == null) {
            super.performBonemeal(level, random, pos, state);
        } else {
            tryGrow(level, pos, random);
        }
    }

    private void tryGrow(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) != 0) {
            return;
        }
        String selected = structure.equals("mulberry1") && random.nextBoolean()
                ? "mulberry3" : structure;
        ResourceLocation id = new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, selected);
        StructureTemplate template = level.getStructureManager().get(id).orElse(null);
        if (template == null) {
            SplendourAblazeEpochMod.LOGGER.error("Missing sapling structure {}", id);
            return;
        }
        BlockPos origin = switch (structure) {
            case "wutong_small" -> pos.offset(-3, 0, -6);
            case "osmanthus" -> pos.offset(-4, 0, -4);
            case "mulberry1" -> pos.offset(-2, 0, -2);
            default -> pos.offset(-3, 0, -3);
        };
        template.placeInWorld(level, origin, origin,
                new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                random, 3);
    }

    @Override
    protected boolean mayPlaceOn(
            BlockState state,
            BlockGetter level,
            BlockPos pos
    ) {
        if (state.is(extraSoil.get())) {
            return true;
        }

        return super.mayPlaceOn(
                state,
                level,
                pos
        );
    }
}
