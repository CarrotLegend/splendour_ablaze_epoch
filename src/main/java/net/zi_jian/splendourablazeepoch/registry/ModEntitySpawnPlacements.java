package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;

public final class ModEntitySpawnPlacements {
    public static void register() {
        SpawnPlacements.register(
                ModEntities.CROAKER.get(),
                SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) ->
                        level.getFluidState(pos).is(Fluids.WATER)
                                && level.getFluidState(pos.above()).is(Fluids.WATER)
        );

        SpawnPlacements.register(
                ModEntities.KOI_FISH.get(),
                SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) ->
                        level.getFluidState(pos).is(Fluids.WATER)
                                && level.getFluidState(pos.above()).is(Fluids.WATER)
        );

        registerAnimal(ModEntities.MAGPIE.get());
        registerAnimal(ModEntities.MUSK_DEER.get());
        registerAnimal(ModEntities.PEACOCK.get());
        registerAnimal(ModEntities.PHEASANT.get());
        registerAnimal(ModEntities.RACCOON_DOG.get());
        registerAnimal(ModEntities.WATER_BUFFALO.get());

        SpawnPlacements.register(
                ModEntities.MESSENGER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) ->
                        level.getDifficulty() != Difficulty.PEACEFUL
                                && Monster.isDarkEnoughToSpawn(level, pos, random)
                                && Mob.checkMobSpawnRules(type, level, reason, pos, random)
        );

        registerUnrestricted(ModEntities.RUSTED_ANCESTORS.get());
        registerUnrestricted(ModEntities.RUSTED_WOMAN.get());
        registerUnrestricted(ModEntities.RUST_HOUND.get());
        registerUnrestricted(ModEntities.RUST_RELICS.get());
    }

    private static <T extends Mob> void registerAnimal(net.minecraft.world.entity.EntityType<T> type) {
        SpawnPlacements.register(
                type,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, reason, pos, random) ->
                        level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON)
                                && level.getRawBrightness(pos, 0) > 8
        );
    }

    private static <T extends Mob> void registerUnrestricted(net.minecraft.world.entity.EntityType<T> type) {
        SpawnPlacements.register(
                type,
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, reason, pos, random) ->
                        Mob.checkMobSpawnRules(entityType, level, reason, pos, random)
        );
    }

    private ModEntitySpawnPlacements() {
    }
}
