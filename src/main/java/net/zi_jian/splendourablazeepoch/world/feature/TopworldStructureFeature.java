package net.zi_jian.splendourablazeepoch.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public final class TopworldStructureFeature extends Feature<StructureFeatureConfiguration> {
    public TopworldStructureFeature(Codec<StructureFeatureConfiguration> codec) { super(codec); }

    @Override
    public boolean place(FeaturePlaceContext<StructureFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        StructureFeatureConfiguration config = context.config();
        Rotation rotation = config.randomRotation() ? Rotation.getRandom(random) : Rotation.NONE;
        Mirror mirror = config.randomMirror() ? Mirror.values()[random.nextInt(2)] : Mirror.NONE;
        BlockPos pos = context.origin().offset(config.offset());
        StructureTemplate template = level.getLevel().getStructureManager().getOrCreate(config.structure());
        StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setMirror(mirror)
                .setRandom(random).setKnownShape(false)
                .addProcessor(new BlockIgnoreProcessor(config.ignoredBlocks().stream().map(Holder::value).toList()));
        return template.placeInWorld(level, pos, pos, settings, random, 4);
    }
}
