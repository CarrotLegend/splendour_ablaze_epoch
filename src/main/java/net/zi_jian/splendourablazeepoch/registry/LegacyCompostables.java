package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

public final class LegacyCompostables {
    public static void register() {
        add("osmanthussapling");
        add("specularpinesapling");
        add("wutongsapling");
        add("mulberrysapling");
        add("ginkgosapling");
        add("cerasusjaponicasapling");
    }

    private static void add(String id) {
        RegistryObject<Block> block = ModBlocks.TOPWORLD_BLOCKS.get(id);
        if (block == null || !block.isPresent()) {
            SplendourAblazeEpochMod.LOGGER.warn("Compostable sapling {} is not registered", id);
            return;
        }

        Item item = block.get().asItem();
        if (ComposterBlock.COMPOSTABLES.containsKey(item)) {
            float existing = ComposterBlock.COMPOSTABLES.getFloat(item);
            if (existing != 0.3F) {
                SplendourAblazeEpochMod.LOGGER.warn(
                        "Compostable sapling {} already has probability {}, expected 0.3", id, existing);
            }
            return;
        }
        ComposterBlock.COMPOSTABLES.put(item, 0.3F);
    }

    private LegacyCompostables() {
    }
}
