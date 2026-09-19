package net.zi_jian.splendourablazeepoch.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.block.entity.ForgingFurnacBlockEntity;
import net.zi_jian.splendourablazeepoch.block.entity.LegacyChestBlockEntity;
import net.zi_jian.splendourablazeepoch.block.entity.MigratedBlockEntity;
import net.zi_jian.splendourablazeepoch.world.TopworldClosure;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(
                    ForgeRegistries.BLOCK_ENTITY_TYPES,
                    SplendourAblazeEpochMod.MOD_ID
            );

    public static final RegistryObject<BlockEntityType<ForgingFurnacBlockEntity>>
            FORGING_FURNAC =
            BLOCK_ENTITY_TYPES.register(
                    "forgingfurnac",
                    () -> BlockEntityType.Builder
                            .of(
                                    ForgingFurnacBlockEntity::new,
                                    ModBlocks.FORGING_FURNAC.get()
                            )
                            .build(null)
            );

    public static final Map<String, RegistryObject<BlockEntityType<?>>>
            TOPWORLD_TYPES =
            new LinkedHashMap<>();

    private static final Set<String> CHEST_TYPES =
            Set.of(
                    "abstrusechest",
                    "beacon_fire_chest",
                    "chillironchest",
                    "immortalmothchest"
            );

    static {
        for (String id : TopworldClosure.BLOCK_ENTITY_IDS) {
            TOPWORLD_TYPES.put(
                    id,
                    BLOCK_ENTITY_TYPES.register(
                            id,
                            () -> createType(id)
                    )
            );
        }
    }

    private static BlockEntityType<?> createType(
            String id
    ) {
        if (CHEST_TYPES.contains(id)) {
            return BlockEntityType.Builder
                    .of(
                            (pos, state) ->
                                    LegacyChestBlockEntity.create(
                                            id,
                                            pos,
                                            state
                                    ),
                            ModBlocks.get(id)
                    )
                    .build(null);
        }

        return BlockEntityType.Builder
                .of(
                        (pos, state) ->
                                MigratedBlockEntity.create(
                                        id,
                                        pos,
                                        state
                                ),
                        ModBlocks.get(id)
                )
                .build(null);
    }

    public static BlockEntityType<?> get(
            String id
    ) {
        RegistryObject<BlockEntityType<?>> value =
                TOPWORLD_TYPES.get(id);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Unknown migrated block entity "
                            + id
            );
        }

        return value.get();
    }

    private ModBlockEntities() {
    }
}