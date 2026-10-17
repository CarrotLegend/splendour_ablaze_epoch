package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.level.block.ComposterBlock;

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
        ComposterBlock.COMPOSTABLES.put(ModBlocks.get(id).asItem(), 0.3F);
    }

    private LegacyCompostables() {
    }
}
