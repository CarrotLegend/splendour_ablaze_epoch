package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register(
            "splendourablazeepochitem",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(
                            "item_group.splendour_ablaze_epoch.splendourablazeepochitem"
                    ))
                    .icon(() -> ModItems.PYROTEMPER_DUST.get().getDefaultInstance())
                    .displayItems((parameters, output) -> ModItems.ITEMS.getEntries().stream()
                            .map(RegistryObject::get)
                            .filter(item -> !(item instanceof BlockItem))
                            .forEach(output::accept))
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register(
            "splendour_ablaze_epoch",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(
                            "item_group.splendour_ablaze_epoch.splendour_ablaze_epoch"
                    ))
                    .icon(() -> ModBlocks.CUT_POLISHED_CAST_RADIANT_LIMESTONE.get().asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> ModItems.ITEMS.getEntries().stream()
                            .map(RegistryObject::get)
                            .filter(BlockItem.class::isInstance)
                            .forEach(output::accept))
                    .withTabsBefore(ITEMS.getId())
                    .build()
    );

    private ModCreativeTabs() {
    }
}
