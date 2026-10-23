package net.zi_jian.splendourablazeepoch.event;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

@Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class LegacyFuelEvents {
    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) {
            return;
        }
        Item item = stack.getItem();

        if (isBlockItem(item, "wutong_log") || isBlockItem(item, "wutong_wood")) {
            event.setBurnTime(700);
        } else if (isBlockItem(item, "specular_pine_log") || isBlockItem(item, "specular_pine_wood")) {
            event.setBurnTime(800);
        } else if (isBlockItem(item, "cerasus_japonica_log") || isBlockItem(item, "cerasus_japonica_wood")) {
            event.setBurnTime(700);
        } else if (isBlockItem(item, "ginkgo_log") || isBlockItem(item, "ginkgo_wood")) {
            event.setBurnTime(700);
        } else if (isBlockItem(item, "osmanthus_log") || isBlockItem(item, "osmanthus_wood")) {
            event.setBurnTime(700);
        } else if (isBlockItem(item, "thatchblock")) {
            event.setBurnTime(800);
        } else if (isBlockItem(item, "cogongrass")) {
            event.setBurnTime(200);
        } else if (isBlockItem(item, "thatchblockstairs")) {
            event.setBurnTime(600);
        } else if (isBlockItem(item, "thatchblockslab")) {
            event.setBurnTime(400);
        } else if (isBlockItem(item, "osmanthussapling")
                || isBlockItem(item, "specularpinesapling")
                || isBlockItem(item, "wutongsapling")
                || isBlockItem(item, "mulberrysapling")
                || isBlockItem(item, "ginkgosapling")
                || isBlockItem(item, "cerasusjaponicasapling")) {
            event.setBurnTime(100);
        } else if (isRegisteredItem(item, "mottledbamboo")) {
            event.setBurnTime(50);
        }
    }

    private static boolean isBlockItem(Item item, String id) {
        RegistryObject<Block> block = ModBlocks.TOPWORLD_BLOCKS.get(id);
        return block != null && block.isPresent() && item == block.get().asItem();
    }

    private static boolean isRegisteredItem(Item item, String id) {
        RegistryObject<Item> registered = ModItems.byIdOrNull(id);
        return registered != null && registered.isPresent() && item == registered.get();
    }

    private LegacyFuelEvents() {
    }
}
