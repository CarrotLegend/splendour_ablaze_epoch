package net.mcreator.splendourablazeepoch.registry;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("splendourablazeepochitem",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.splendour_ablaze_epoch.splendourablazeepochitem"))
                    .icon(() -> ModItems.PYROTEMPER_DUST.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.PYROTEMPER_DUST.get());
                        output.accept(ModItems.SKY_EMBLEM.get());
                        output.accept(ModItems.FORGING_FURNAC.get());
                        output.accept(ModItems.TIN_NUGGET.get());
                        output.accept(ModItems.LEAD_NUGGET.get());
                        output.accept(ModItems.BRONZE_INGOT.get());
                        output.accept(ModItems.TIN_INGOT.get());
                        output.accept(ModItems.PEWTER_INGOT.get());
                        output.accept(ModItems.MAGNETITE.get());
                        output.accept(ModItems.MAGNETIC_STEEL_INGOT.get());
                        output.accept(ModItems.DAMASCENE_STEEL_INGOT.get());
                        output.accept(ModItems.CUPRONICKEL_INGOT.get());
                        output.accept(ModItems.MOLD_TIN_INGOT.get());
                        output.accept(ModItems.SIMILAR_TIN.get());
                        output.accept(ModItems.DEMON_COPPER.get());
                        output.accept(ModItems.DARK_SALTPETER.get());
                        ModItems.LEGACY_SIMPLE_ITEMS.values().forEach(item -> output.accept(item.get()));
                        ModItems.LEGACY_TOOLS.values().forEach(item -> output.accept(item.get()));
                        ModItems.LEGACY_ARMOR.values().forEach(item -> output.accept(item.get()));
                        ModItems.TOPWORLD_BLOCK_ITEMS.values().forEach(item -> output.accept(item.get()));
                    }).build());

    private ModCreativeTabs() {
    }
}
