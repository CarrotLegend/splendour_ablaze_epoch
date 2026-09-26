package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SplendourAblazeEpochMod.MOD_ID);

    private static final String[] LEGACY_ITEM_TAB_ORDER = {
            "pyrotemperdust", "sagesfragments", "pyrotempertinder", "previousletter", "splendourablazecompass",
            "mottledbamboo", "cloudberryfruit", "mulberry", "hawthorn", "millet",
            "leek", "staranise", "sichuanpepper", "fennel", "clove",
            "cinnamonbark", "bayleaf", "nutmeg", "dahurianangelica", "delicatebowl",
            "secretbeefstew", "potteryclayball", "lime", "sugarcoatedhaws", "ricecake",
            "friedleekdumplings", "mungbeansoup", "potherbsoup", "beefstew", "rawkoifish",
            "cookedkoifish", "rawcroaker", "cookedcroaker", "rawvenison", "cookedvenison",
            "shrillfragments", "rawlead", "rawtin", "magnetite", "rawsilver",
            "demoncopper", "similartin", "cinnabar", "graphite", "darksaltpeter",
            "sulfur", "saltpeter", "blackpowder", "jadedebris", "jade",
            "luminousjewel", "silkworm", "silkwormcocoon", "lead_ingot", "leadnugget",
            "tin_ingot", "tinnugget", "sliveringot", "bronze_ingot", "bronzenugget",
            "pewter_ingot", "magnetic_steel_ingot", "damascene_steel_ingot", "cupronickel_ingot", "mold_tin_ingot",
            "celesterraldebris", "celes_terral_ingot", "typefirst", "typesecond", "typethird",
            "typefourth", "typefifth", "typesixth", "featheryfoambucket", "koifishbucket",
            "croakerbucket", "commandflag", "commandflag_1", "commandflag_2", "tigertally",
            "initialseal", "initialmagnet", "initialhandscroll", "initialfirearm", "ghostjadependant",
            "purplegoldbell", "classics", "positioningscroll_1", "positioningscroll_2", "positioningscroll_4",
            "positioningscroll_3", "positioningsceoll_5", "talisman", "talisman_1", "talisman_2",
            "talisman_3", "talisman_4", "huntbow", "whistlingarrowr", "tetragonalbludgeon",
            "firearmtube", "barrelfirearm", "threebarrelblunderbuss", "leadbullet", "pewter_sword",
            "pewter_shovel", "pewter_pickaxe", "pewter_hoe", "pewter_axe", "magnetic_steel_pickaxe",
            "magnetic_steel_sword", "magnetic_steel_axe", "magnetic_steel_shovel", "magnetic_steel_hoe", "magnetic_steel_armor_helmet",
            "magnetic_steel_armor_chestplate", "magnetic_steel_armor_leggings", "magnetic_steel_armor_boots", "bronze_pickaxe", "bronze_sword",
            "bronze_axe", "bronze_shovel", "bronze_hoe", "dagger", "bronze_armor_helmet",
            "bronze_armor_chestplate", "bronze_armor_leggings", "bronze_armor_boots", "damascene_steel_sword", "damascene_steel_shovel",
            "damascene_steel_pickaxe", "damascene_steel_hoe", "damascene_steel_axe", "damascene_steel_armor_helmet", "damascene_steel_armor_chestplate",
            "damascene_steel_armor_leggings", "damascene_steel_armor_boots", "cupronickel_pickaxe", "cupronickel_axe", "cupronickel_sword",
            "cupronickel_shovel", "cupronickel_hoe", "cupronickel_armor_helmet", "cupronickel_armor_chestplate", "cupronickel_armor_leggings",
            "cupronickel_armor_boots", "mold_tin_pickaxe", "mold_tin_axe", "mold_tin_sword", "mold_tin_shovel",
            "mold_tin_hoe", "mold_tin_armor_helmet", "mold_tin_armor_chestplate", "mold_tin_armor_leggings", "mold_tin_armor_boots",
            "celesterralupgradesmithingtemplate", "celes_terral_pickaxe", "celes_terral_axe", "celes_terral_sword", "celes_terral_shovel",
            "celes_terral_hoe", "celes_terral_armor_helmet", "celes_terral_armor_chestplate", "celes_terral_armor_leggings", "celes_terral_armor_boots",
            "pheasant_spawn_egg", "muskdeer_spawn_egg", "waterbuffalo_spawn_egg", "koifish_spawn_egg", "magpie_spawn_egg",
            "peacock_spawn_egg", "raccoondog_spawn_egg", "croaker_spawn_egg", "girlghost_spawn_egg", "goldenhairhou_spawn_egg",
            "rustedancestors_spawn_egg", "rustedchef_spawn_egg", "rustrelics_spawn_egg", "rusthound_spawn_egg", "cauldronbeast_spawn_egg",
            "terracottawarriorsguard_spawn_egg", "terracottageneral_spawn_egg", "terracottawarriors_spawn_egg", "alivepictograph_spawn_egg", "castinscribedautomaton_spawn_egg",
            "flyarrowhead_spawn_egg", "skyadministrator_spawn_egg", "darkworm_spawn_egg",
            "pagewraith_spawn_egg", "pagegnat_spawn_egg", "messenger_spawn_egg", "firearmtigerguard_spawn_egg",
    };

    private static final String[] LEGACY_BLOCK_TAB_ORDER = {
            "inkgrassblock", "inkdirt", "rustedmoss", "jadesand", "radiantlimestone",
            "fadedradiantlimestone", "flintstone", "crystalstone", "featheryfoam", "charredsand",
            "wutong_log", "strippedwutonglog", "wutong_wood", "strippedwutongwood", "wutong_planks",
            "wutong_leaves", "wutongsapling", "wutong_stairs", "wutong_slab", "wutong_fence",
            "wutong_fence_gate", "wutong_pressure_plate", "wutong_button", "wutongtrapdoor", "wutongdoor",
            "wutongbookshelf", "ginkgo_log", "strippedginkgolog", "ginkgo_wood", "strippedginkgowood",
            "ginkgo_planks", "ginkgo_leaves", "ginkgosapling", "ginkgo_stairs", "ginkgo_slab",
            "ginkgo_fence", "ginkgo_fence_gate", "ginkgo_pressure_plate", "ginkgo_button", "ginkgotrapdoor",
            "ginkgodoor", "ginkgobookshelf", "specular_pine_log", "strippedspecularpinelog", "specular_pine_wood",
            "strippedspecularpinewood", "specular_pine_planks", "specular_pine_leaves", "specularpinesapling", "specular_pine_stairs",
            "specular_pine_slab", "specular_pine_fence", "specular_pine_fence_gate", "specular_pine_pressure_plate", "specular_pine_button",
            "specularpinetrapdoor", "specularpinedoor", "specularpinebookshelf", "cerasus_japonica_log", "strippedcerasusjaponicalog",
            "cerasus_japonica_wood", "strippedcerasusjaponicawood", "cerasus_japonica_planks", "cerasus_japonica_leaves", "blossomcerasusjaponicaleaves",
            "cerasusjaponicasapling", "cerasus_japonica_stairs", "cerasus_japonica_slab", "cerasus_japonica_fence", "cerasus_japonica_fence_gate",
            "cerasus_japonica_pressure_plate", "cerasus_japonica_button", "cerasusjaponicatrapdoor", "cerasusjaponicadoor", "cerasusjaponicabookshelf",
            "osmanthus_log", "strippedosmanthuslog", "osmanthus_wood", "strippedosmanthuswood", "osmanthus_planks",
            "osmanthus_leaves", "osmanthussapling", "blossomosmanthusleaves", "blossomosmanthusbranch", "osmanthus_stairs",
            "osmanthus_slab", "osmanthus_fence", "osmanthus_fence_gate", "osmanthus_pressure_plate", "osmanthus_button",
            "osmanthustrapdoor", "osmanthusdoor", "osmanthusbookshelf", "mulberry_log", "strippedmulberrylog",
            "mulberry_wood", "strippedmulberrywood", "mulberry_planks", "mulberry_leaves", "mulberrysapling",
            "mulberrywormleaves", "mulberryfruitleaves", "mulberry_stairs", "mulberry_slab", "mulberry_fence",
            "mulberry_fence_gate", "mulberry_pressure_plate", "mulberry_button", "mulberrytrapdoor", "mulberrydoor",
            "mulberrybookshelf", "mottledbambooblock", "mottled_bamboo_wood", "mottled_bamboo_leaves", "mottledbamboobud",
            "mottled_bamboo_planks", "mottled_bamboo_stairs", "mottled_bamboo_slab", "mottled_bamboo_fence", "mottled_bamboo_fence_gate",
            "mottled_bamboo_pressure_plate", "mottled_bamboo_button", "mottledbambootrapdoor", "mottledbamboodoor", "mottledbamboobookshelf",
            "dragonsophora_log", "strippeddragonsophoralog", "dragonsophora_wood", "strippeddragonsophorawood", "dragonsophora_planks",
            "dragonsophora_leaves", "dragonsophora_stairs", "dragonsophora_slab", "dragonsophora_fence", "dragonsophora_fence_gate",
            "dragonsophora_pressure_plate", "dragonsophora_button", "dragonsophoratrapdoor", "dragonsophoradoor", "dragonsophorabookshelf",
            "hibiscus_log", "strippedhibiscuslog", "hibiscus_wood", "strippedhibiscuswood", "hibiscus_leaves",
            "hibiscusflameleaves", "hibiscus_stairs", "hibiscus_slab", "hibiscus_fence", "hibiscus_fence_gate",
            "hibiscus_pressure_plate", "hibiscus_button", "hibiscus_tarpdoor", "hibiscus_planks", "hibiscus_door",
            "silkblock", "redsilkblock", "orangesilkblock", "yellowsilkblock", "limesilkblock",
            "greensilkblock", "cyansilkblock", "lightbluesilkblock", "bluesilkblock", "purplesilkblock",
            "magentasilkblock", "pinksilkblock", "brownsilkblock", "graysilkblock", "lightgraysilkblock",
            "blacksilkblock", "radiantlimestonecopperore", "radiantlimestoneironore", "radiantlimestoneleadore", "radiantlimestonetinore",
            "suspiciousradiantlimestonecopperore", "suspiciousradiantlimestonetinore", "radiantlimestonegoldore", "radiantlimestonecinnabarore", "radiantlimestonegraphiteore",
            "magnetiteribbonrockore", "ribbonrockcopperore", "ribbonrockcoalore", "ribbonrocksilverore", "firmrockdarksaltpeterore",
            "firmrockironore", "firmrocksulfurore", "firmrocksaltpeterore", "firmrockcoalore", "rawtinblock",
            "rawleadblock", "magnetiteblock", "demoncopperblock", "similartinblock", "cinnabarblock",
            "graphiteblock", "jadeblock", "lead_block", "tin_block", "sliverblock",
            "bronze_block", "pewter_block", "damascene_steel_block", "magnetic_steel_block", "celes_terral_block",
            "cupronickel_block", "mold_tin_block", "hosta", "ophiopogon", "hollyhock",
            "bellflower", "hydrangea", "whitefleabane", "scabish", "yellowhemp",
            "floatingheart", "basella", "osmunda", "portulaca", "cloudberry",
            "artemisia", "saxifrage", "stubbleawn", "cyantwig", "tallcyantwig",
            "cogongrass", "strawmushroom", "whitestinkhorn", "hawthornleaves", "hawthornfuritleaves",
            "hawthornsapling", "thatchblock", "thatchblockstairs", "thatchblockslab", "decayed_log",
            "strippeddecayedlog", "decayed_wood", "strippeddecayedwood", "decayedleaves", "decayed_planks",
            "decayed_stairs", "decayed_slab", "decayed_fence", "decayed_fence_gate", "decayed_pressure_plate",
            "decayed_button", "decayedtarpdoor", "decayeddoor", "decayedbookshelf", "bluishstone",
            "polishedbluishstone", "bluishstonebricks", "crackedbluishstonebricks", "chiseledbluishstonebricks", "mossybluishstonebricks",
            "bluishstonepillar", "bluishstonebricksstairs", "bluishstonebricksslab", "bluishstonebrickswall", "polishedradiantlimestone",
            "cutpolishedcastradiantlimestone", "cutpolishedcastradiantlimestonestairs", "cutpolishedcastradiantlimestoneslab", "chiseledcastradiantlimestone", "polishedfigurecastradiantlimestone",
            "castradiantlimestone", "castradiantlimestonestairs", "castradiantlimestoneslab", "castradiantlimestonewall", "polishedcastradiantlimestone",
            "castradiantlimestonebricks", "castradiantlimestonebricksstairs", "castradiantlimestonebricksslab", "castradiantlimestonebrickswall", "auroraleadenblock",
            "auroraleadenblockstairs", "auroraleadenblockslab", "auroraleadenblockwall", "auroraleadentile", "auroraleadenpillar",
            "potteryclay", "lacqueredwood", "terracottabricks", "terracottabricksstairs", "terracottabricksslab",
            "carvingterracottabricks", "mottledterracottabricks", "whiteterracottabricks", "candlestick", "potteryjar",
            "claypot", "potterytile", "darktile", "darktilestairs", "darktileslab",
            "darktilewall", "whitewashedbricksblock", "wetwhitewashedbricksblock", "patchywhitewashedbricksblock", "mossywhitewashedbricksblock",
            "papercocoon", "paperleaves", "firmrock", "polishedfirmrock", "firmrockbricks",
            "chiseledfirmrock", "crackedfirmrockbricks", "mottledfirmrockbricks", "burntfirmrockbricks", "burntpolishedfirmrock",
            "charredfirmrock", "firmrockbricksstairs", "firmrockbricksslab", "firmrockbrickwall", "simplegrave",
            "ribbonrock", "polished_ribbonrock", "ribbonrockbricks", "ribbonrockbricksstairs", "ribbonrockbricksslab",
            "ribbonrockbrickswall", "chiseledribbonrockbricks", "darksteelstone", "darksteelbricks", "darksteelbricksstairs",
            "darksteelbricksslab", "darksteelbrickswall", "chiseleddarksteelbricks", "patternedsilverdarksteelbricks", "strangeprintcitystart",
            "sagemperorplatformadder", "viridiandescendacademyadder", "qianyang_stronghold", "mausoleumadder", "woodencrate",
            "exquisitewoodenbox", "abstrusechest", "chillironchest", "immortalmothchest", "beacon_fire_chest",
            "xyfzadder", "cyxadder", "zxxjadder", "terracottageneraladder", "jmhadder",
            "zytsadder", "forgingfurnac", "printtable", "sericulturehive", "gnome",
            "antigravitdevice", "arcaneprintsovereign", "chronsovereigntystar", "ambiguousimmortale", "shockflamewarlord"
    };

    public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register(
            "splendourablazeepochitem",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(
                            "item_group.splendour_ablaze_epoch.splendourablazeepochitem"
                    ))
                    .icon(() -> ModItems.PYROTEMPER_DUST.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (String id : LEGACY_ITEM_TAB_ORDER) {
                            RegistryObject<net.minecraft.world.item.Item> item = ModItems.byIdOrNull(id);
                            if (item != null) {
                                output.accept(item.get());
                            }
                        }
                    })
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register(
            "splendour_ablaze_epoch",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(
                            "item_group.splendour_ablaze_epoch.splendour_ablaze_epoch"
                    ))
                    .icon(() -> ModBlocks.CUT_POLISHED_CAST_RADIANT_LIMESTONE.get().asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (String id : LEGACY_BLOCK_TAB_ORDER) {
                            RegistryObject<net.minecraft.world.item.Item> item = ModItems.byIdOrNull(id);
                            if (item != null) {
                                output.accept(item.get());
                            }
                        }
                    })
                    .withTabsBefore(ITEMS.getId())
                    .build()
    );

    private ModCreativeTabs() {
    }
}
