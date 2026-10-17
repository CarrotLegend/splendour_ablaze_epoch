package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.block.SericultureHiveBlock;

public final class SericultureRegistry {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SplendourAblazeEpochMod.MOD_ID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<Block> SERICULTURE_HIVE = BLOCKS.register(
            "sericulturehive",
            () -> new SericultureHiveBlock(SericultureHiveBlock.Stage.EMPTY)
    );

    public static final RegistryObject<Block> SERICULTURE_HIVE_LEAVES = BLOCKS.register(
            "sericulturehiveleaves",
            () -> new SericultureHiveBlock(SericultureHiveBlock.Stage.LEAVES)
    );

    public static final RegistryObject<Block> SERICULTURE_HIVE_FULL = BLOCKS.register(
            "sericulturehivefull",
            () -> new SericultureHiveBlock(SericultureHiveBlock.Stage.FULL)
    );

    public static final RegistryObject<Item> SERICULTURE_HIVE_ITEM = ITEMS.register(
            "sericulturehive",
            () -> new BlockItem(SERICULTURE_HIVE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> SERICULTURE_HIVE_LEAVES_ITEM = ITEMS.register(
            "sericulturehiveleaves",
            () -> new BlockItem(SERICULTURE_HIVE_LEAVES.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> SERICULTURE_HIVE_FULL_ITEM = ITEMS.register(
            "sericulturehivefull",
            () -> new BlockItem(SERICULTURE_HIVE_FULL.get(), new Item.Properties())
    );

    @Mod.EventBusSubscriber(
            modid = SplendourAblazeEpochMod.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static final class CreativeTabHandler {
        @SubscribeEvent
        public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (event.getTab() != ModCreativeTabs.BLOCKS.get()) {
                return;
            }

            ItemStack gnome = new ItemStack(ModItems.byId("gnome").get());
            ItemStack hive = new ItemStack(SERICULTURE_HIVE_ITEM.get());
            event.getEntries().putBefore(
                    gnome,
                    hive,
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }

        private CreativeTabHandler() {
        }
    }

    private SericultureRegistry() {
    }
}
