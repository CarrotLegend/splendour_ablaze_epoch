package net.zi_jian.splendourablazeepoch.registry;

import net.zi_jian.splendourablazeepoch.block.TopworldLogBlock;
import net.zi_jian.splendourablazeepoch.block.TopworldSaplingBlock;
import net.zi_jian.splendourablazeepoch.world.tree.ModTreeGrowers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class TopworldWoodRegistry {

    public static void registerAll() {

        family()
                .log(
                        "wutong_log",
                        "strippedwutonglog"
                )
                .wood(
                        "wutong_wood",
                        "strippedwutongwood"
                )
                .planks("wutong_planks")
                .leaves("wutong_leaves")
                .stairs(
                        "wutong_stairs",
                        "wutong_planks"
                )
                .slab("wutong_slab")
                .fence("wutong_fence")
                .fenceGate("wutong_fence_gate")
                .door("wutongdoor")
                .trapdoor("wutongtrapdoor")
                .pressurePlate("wutong_pressure_plate")
                .button("wutong_button")
                .bookshelf("wutongbookshelf")
                .sapling(
                        "wutongsapling",
                        ModTreeGrowers.WUTONG
                );

        family()
                .log(
                        "ginkgo_log",
                        "strippedginkgolog"
                )
                .wood(
                        "ginkgo_wood",
                        "strippedginkgowood"
                )
                .planks("ginkgo_planks")
                .leaves("ginkgo_leaves")
                .stairs(
                        "ginkgo_stairs",
                        "ginkgo_planks"
                )
                .slab("ginkgo_slab")
                .fence("ginkgo_fence")
                .fenceGate("ginkgo_fence_gate")
                .door("ginkgodoor")
                .trapdoor("ginkgotrapdoor")
                .pressurePlate("ginkgo_pressure_plate")
                .button("ginkgo_button")
                .bookshelf("ginkgobookshelf")
                .sapling(
                        "ginkgosapling",
                        ModTreeGrowers.GINKGO
                );

        family()
                .log(
                        "cerasus_japonica_log",
                        "strippedcerasusjaponicalog"
                )
                .wood(
                        "cerasus_japonica_wood",
                        "strippedcerasusjaponicawood"
                )
                .planks("cerasus_japonica_planks")
                .leaves("cerasus_japonica_leaves")
                .extraLeaves(
                        "blossomcerasusjaponicaleaves"
                )
                .stairs(
                        "cerasus_japonica_stairs",
                        "cerasus_japonica_planks"
                )
                .slab("cerasus_japonica_slab")
                .fence("cerasus_japonica_fence")
                .fenceGate(
                        "cerasus_japonica_fence_gate"
                )
                .door("cerasusjaponicadoor")
                .trapdoor(
                        "cerasusjaponicatrapdoor"
                )
                .pressurePlate(
                        "cerasus_japonica_pressure_plate"
                )
                .button("cerasus_japonica_button")
                .bookshelf(
                        "cerasusjaponicabookshelf"
                )
                .sapling(
                        "cerasusjaponicasapling",
                        ModTreeGrowers.CERASUS_JAPONICA
                );

        family()
                .log(
                        "osmanthus_log",
                        "strippedosmanthuslog"
                )
                .wood(
                        "osmanthus_wood",
                        "strippedosmanthuswood"
                )
                .planks("osmanthus_planks")
                .leaves("osmanthus_leaves")
                .extraLeaves(
                        "blossomosmanthusleaves"
                )
                .treeDecoration(
                        "blossomosmanthusbranch"
                )
                .stairs(
                        "osmanthus_stairs",
                        "osmanthus_planks"
                )
                .slab("osmanthus_slab")
                .fence("osmanthus_fence")
                .fenceGate("osmanthus_fence_gate")
                .door("osmanthusdoor")
                .trapdoor("osmanthustrapdoor")
                .pressurePlate(
                        "osmanthus_pressure_plate"
                )
                .button("osmanthus_button")
                .bookshelf("osmanthusbookshelf")
                .sapling(
                        "osmanthussapling",
                        ModTreeGrowers.OSMANTHUS
                );

        family()
                .log(
                        "mulberry_log",
                        "strippedmulberrylog"
                )
                .wood(
                        "mulberry_wood",
                        "strippedmulberrywood"
                )
                .planks("mulberry_planks")
                .leaves("mulberry_leaves")
                .extraLeaves(
                        "mulberryfruitleaves"
                )
                .extraLeaves(
                        "mulberrywormleaves"
                )
                .stairs(
                        "mulberry_stairs",
                        "mulberry_planks"
                )
                .slab("mulberry_slab")
                .fence("mulberry_fence")
                .fenceGate("mulberry_fence_gate")
                .door("mulberrydoor")
                .trapdoor("mulberrytrapdoor")
                .pressurePlate(
                        "mulberry_pressure_plate"
                )
                .button("mulberry_button")
                .bookshelf("mulberrybookshelf")
                .sapling(
                        "mulberrysapling",
                        ModTreeGrowers.MULBERRY
                );

        family()
                .log(
                        "specular_pine_log",
                        "strippedspecularpinelog"
                )
                .wood(
                        "specular_pine_wood",
                        "strippedspecularpinewood"
                )
                .planks("specular_pine_planks")
                .leaves("specular_pine_leaves")
                .stairs(
                        "specular_pine_stairs",
                        "specular_pine_planks"
                )
                .slab("specular_pine_slab")
                .fence("specular_pine_fence")
                .fenceGate(
                        "specular_pine_fence_gate"
                )
                .door("specularpinedoor")
                .trapdoor(
                        "specularpinetrapdoor"
                )
                .pressurePlate(
                        "specular_pine_pressure_plate"
                )
                .button("specular_pine_button")
                .bookshelf(
                        "specularpinebookshelf"
                )
                .sapling(
                        "specularpinesapling",
                        ModTreeGrowers.SPECULAR_PINE
                );

        family()
                .log(
                        "dragonsophora_log",
                        "strippeddragonsophoralog"
                )
                .wood(
                        "dragonsophora_wood",
                        "strippeddragonsophorawood"
                )
                .planks("dragonsophora_planks")
                .leaves("dragonsophora_leaves")
                .stairs(
                        "dragonsophora_stairs",
                        "dragonsophora_planks"
                )
                .slab("dragonsophora_slab")
                .fence("dragonsophora_fence")
                .fenceGate(
                        "dragonsophora_fence_gate"
                )
                .door("dragonsophoradoor")
                .trapdoor(
                        "dragonsophoratrapdoor"
                )
                .pressurePlate(
                        "dragonsophora_pressure_plate"
                )
                .button("dragonsophora_button")
                .bookshelf(
                        "dragonsophorabookshelf"
                );

        family()
                .log(
                        "decayed_log",
                        "strippeddecayedlog"
                )
                .wood(
                        "decayed_wood",
                        "strippeddecayedwood"
                )
                .planks("decayed_planks")
                .leaves("decayedleaves")
                .stairs(
                        "decayed_stairs",
                        "decayed_planks"
                )
                .slab("decayed_slab")
                .fence("decayed_fence")
                .fenceGate("decayed_fence_gate")
                .door("decayeddoor")
                .trapdoor("decayedtarpdoor")
                .pressurePlate(
                        "decayed_pressure_plate"
                )
                .button("decayed_button")
                .bookshelf("decayedbookshelf");

        family()
                .log(
                        "hibiscus_log",
                        "strippedhibiscuslog"
                )
                .wood(
                        "hibiscus_wood",
                        "strippedhibiscuswood"
                )
                .planks("hibiscus_planks")
                .leaves("hibiscus_leaves")
                .extraLeaves(
                        "hibiscusflameleaves"
                )
                .stairs(
                        "hibiscus_stairs",
                        "hibiscus_planks"
                )
                .slab("hibiscus_slab")
                .fence("hibiscus_fence")
                .fenceGate("hibiscus_fence_gate")
                .door("hibiscus_door")
                .trapdoor("hibiscus_tarpdoor")
                .pressurePlate(
                        "hibiscus_pressure_plate"
                )
                .button("hibiscus_button");

        family()
                .extraLeaves("hawthornleaves")
                .extraLeaves("hawthornfuritleaves")
                .sapling(
                        "hawthornsapling",
                        ModTreeGrowers.HAWTHORN
                );

        family()
                .pillar("mottled_bamboo_log")
                .pillar("mottled_bamboo_wood")
                .pillar("mottledbambooblock")
                .planks("mottled_bamboo_planks")
                .leaves("mottled_bamboo_leaves")
                .stairs(
                        "mottled_bamboo_stairs",
                        "mottled_bamboo_planks"
                )
                .slab("mottled_bamboo_slab")
                .fence("mottled_bamboo_fence")
                .fenceGate(
                        "mottled_bamboo_fence_gate"
                )
                .door("mottledbamboodoor")
                .trapdoor(
                        "mottledbambootrapdoor"
                )
                .pressurePlate(
                        "mottled_bamboo_pressure_plate"
                )
                .button("mottled_bamboo_button")
                .bookshelf(
                        "mottledbamboobookshelf"
                );
    }

    private static WoodFamilyBuilder family() {
        return new WoodFamilyBuilder();
    }

    private static void register(
            String id,
            java.util.function.Supplier<Block> factory
    ) {
        ModBlocks.registerTopworldBlock(
                id,
                factory
        );
    }

    private static final class WoodFamilyBuilder {

        private WoodFamilyBuilder log(
                String id,
                String strippedId
        ) {
            register(
                    strippedId,
                    () -> new RotatedPillarBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.STRIPPED_OAK_LOG
                            )
                    )
            );

            register(
                    id,
                    () -> new TopworldLogBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_LOG
                            ),
                            () -> ModBlocks.get(strippedId)
                    )
            );

            return this;
        }

        private WoodFamilyBuilder wood(
                String id,
                String strippedId
        ) {
            register(
                    strippedId,
                    () -> new RotatedPillarBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.STRIPPED_OAK_WOOD
                            )
                    )
            );

            register(
                    id,
                    () -> new TopworldLogBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_WOOD
                            ),
                            () -> ModBlocks.get(strippedId)
                    )
            );

            return this;
        }

        private WoodFamilyBuilder pillar(
                String id
        ) {
            register(
                    id,
                    () -> new RotatedPillarBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_LOG
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder planks(
                String id
        ) {
            register(
                    id,
                    () -> new Block(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_PLANKS
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder leaves(
                String id
        ) {
            return extraLeaves(id);
        }

        private WoodFamilyBuilder extraLeaves(
                String id
        ) {
            register(
                    id,
                    () -> new LeavesBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_LEAVES
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder treeDecoration(
                String id
        ) {
            register(
                    id,
                    () -> new Block(
                            BlockBehaviour.Properties.copy(
                                    Blocks.FERN
                            )
                                    .noCollission()
                                    .instabreak()
                    )
            );

            return this;
        }

        private WoodFamilyBuilder stairs(
                String id,
                String plankId
        ) {
            register(
                    id,
                    () -> new StairBlock(
                            () -> ModBlocks.get(plankId)
                                    .defaultBlockState(),
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_STAIRS
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder slab(
                String id
        ) {
            register(
                    id,
                    () -> new SlabBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_SLAB
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder fence(
                String id
        ) {
            register(
                    id,
                    () -> new FenceBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_FENCE
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder fenceGate(
                String id
        ) {
            register(
                    id,
                    () -> new FenceGateBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_FENCE_GATE
                            ),
                            WoodType.OAK
                    )
            );

            return this;
        }

        private WoodFamilyBuilder door(
                String id
        ) {
            register(
                    id,
                    () -> new DoorBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_DOOR
                            ),
                            BlockSetType.OAK
                    )
            );

            return this;
        }

        private WoodFamilyBuilder trapdoor(
                String id
        ) {
            register(
                    id,
                    () -> new TrapDoorBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_TRAPDOOR
                            ),
                            BlockSetType.OAK
                    )
            );

            return this;
        }

        private WoodFamilyBuilder pressurePlate(
                String id
        ) {
            register(
                    id,
                    () -> new PressurePlateBlock(
                            PressurePlateBlock.Sensitivity.EVERYTHING,
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_PRESSURE_PLATE
                            ),
                            BlockSetType.OAK
                    )
            );

            return this;
        }

        private WoodFamilyBuilder button(
                String id
        ) {
            register(
                    id,
                    () -> new ButtonBlock(
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_BUTTON
                            ),
                            BlockSetType.OAK,
                            30,
                            true
                    )
            );

            return this;
        }

        private WoodFamilyBuilder bookshelf(
                String id
        ) {
            register(
                    id,
                    () -> new Block(
                            BlockBehaviour.Properties.copy(
                                    Blocks.BOOKSHELF
                            )
                    )
            );

            return this;
        }

        private WoodFamilyBuilder sapling(
                String id,
                AbstractTreeGrower grower
        ) {
            register(
                    id,
                    () -> new TopworldSaplingBlock(
                            grower,
                            BlockBehaviour.Properties.copy(
                                    Blocks.OAK_SAPLING
                            ),
                            () -> ModBlocks.get(
                                    "inkgrassblock"
                            )
                    )
            );

            return this;
        }
    }

    private TopworldWoodRegistry() {
    }
}