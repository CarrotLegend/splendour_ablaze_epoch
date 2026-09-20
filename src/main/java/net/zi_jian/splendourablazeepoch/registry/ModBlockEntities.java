package net.zi_jian.splendourablazeepoch.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.block.entity.ForgingFurnacBlockEntity;
import net.zi_jian.splendourablazeepoch.block.entity.GnomeBlockEntity;
import net.zi_jian.splendourablazeepoch.block.entity.LegacyChestBlockEntity;
import net.zi_jian.splendourablazeepoch.block.entity.PrintTableBlockEntity;
import net.zi_jian.splendourablazeepoch.world.TopworldClosure;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>>
            BLOCK_ENTITY_TYPES =
            DeferredRegister.create(
                    ForgeRegistries.BLOCK_ENTITY_TYPES,
                    SplendourAblazeEpochMod.MOD_ID
            );

    public static final Map<
            String,
            RegistryObject<? extends BlockEntityType<?>>>
            TOPWORLD_TYPES =
            new LinkedHashMap<>();

    public static final RegistryObject<
            BlockEntityType<ForgingFurnacBlockEntity>>
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

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            ABSTRUSE_CHEST =
            registerStorage(
                    "abstrusechest",
                    ModBlocks.ABSTRUSE_CHEST
            );

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            BEACON_FIRE_CHEST =
            registerStorage(
                    "beacon_fire_chest",
                    ModBlocks.BEACON_FIRE_CHEST
            );

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            CHILL_IRON_CHEST =
            registerStorage(
                    "chillironchest",
                    ModBlocks.CHILL_IRON_CHEST
            );

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            IMMORTAL_MOTH_CHEST =
            registerStorage(
                    "immortalmothchest",
                    ModBlocks.IMMORTAL_MOTH_CHEST
            );

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            CLAY_POT =
            registerStorage(
                    "claypot",
                    ModBlocks.CLAY_POT
            );

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            EXQUISITE_WOODEN_BOX =
            registerStorage(
                    "exquisitewoodenbox",
                    ModBlocks.EXQUISITE_WOODEN_BOX
            );

    public static final RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
            WOODEN_CRATE =
            registerStorage(
                    "woodencrate",
                    ModBlocks.WOODEN_CRATE
            );

    public static final RegistryObject<
            BlockEntityType<PrintTableBlockEntity>>
            PRINT_TABLE =
            registerTopworldType(
                    "printtable",
                    () -> BlockEntityType.Builder
                            .of(
                                    PrintTableBlockEntity::create,
                                    ModBlocks.PRINT_TABLE.get()
                            )
                            .build(null)
            );

    public static final RegistryObject<
            BlockEntityType<GnomeBlockEntity>>
            GNOME =
            registerTopworldType(
                    "gnome",
                    () -> BlockEntityType.Builder
                            .of(
                                    GnomeBlockEntity::create,
                                    ModBlocks.GNOME.get()
                            )
                            .build(null)
            );

    static {
        for (String id : TopworldClosure.BLOCK_ENTITY_IDS) {
            if (!TOPWORLD_TYPES.containsKey(id)) {
                throw new IllegalStateException(
                        "Missing explicit block entity registration: "
                                + SplendourAblazeEpochMod.MOD_ID
                                + ":"
                                + id
                );
            }
        }
    }

    private static RegistryObject<
            BlockEntityType<LegacyChestBlockEntity>>
    registerStorage(
            String id,
            RegistryObject<net.minecraft.world.level.block.Block> block
    ) {
        return registerTopworldType(
                id,
                () -> BlockEntityType.Builder
                        .of(
                                (pos, state) ->
                                        LegacyChestBlockEntity.create(
                                                id,
                                                pos,
                                                state
                                        ),
                                block.get()
                        )
                        .build(null)
        );
    }

    private static <T extends BlockEntity>
    RegistryObject<BlockEntityType<T>>
    registerTopworldType(
            String id,
            Supplier<BlockEntityType<T>> factory
    ) {
        if (TOPWORLD_TYPES.containsKey(id)) {
            throw new IllegalStateException(
                    "Duplicate block entity registration: "
                            + SplendourAblazeEpochMod.MOD_ID
                            + ":"
                            + id
            );
        }

        RegistryObject<BlockEntityType<T>> registered =
                BLOCK_ENTITY_TYPES.register(
                        id,
                        factory
                );

        TOPWORLD_TYPES.put(
                id,
                registered
        );

        return registered;
    }

    public static BlockEntityType<?> get(
            String id
    ) {
        RegistryObject<? extends BlockEntityType<?>>
                value =
                TOPWORLD_TYPES.get(id);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Unknown topworld block entity "
                            + SplendourAblazeEpochMod.MOD_ID
                            + ":"
                            + id
            );
        }

        return value.get();
    }

    private ModBlockEntities() {
    }
}