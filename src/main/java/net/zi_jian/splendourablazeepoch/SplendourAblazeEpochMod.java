package net.zi_jian.splendourablazeepoch;

import com.mojang.logging.LogUtils;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModCreativeTabs;
import net.zi_jian.splendourablazeepoch.registry.ModEffects;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;
import net.zi_jian.splendourablazeepoch.registry.ModEntitySpawnPlacements;
import net.zi_jian.splendourablazeepoch.registry.ModFeatures;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;
import net.zi_jian.splendourablazeepoch.registry.ModParticles;
import net.zi_jian.splendourablazeepoch.registry.ModRecipes;
import net.zi_jian.splendourablazeepoch.registry.ModSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SplendourAblazeEpochMod.MOD_ID)
public final class SplendourAblazeEpochMod {
    public static final String MOD_ID = "splendour_ablaze_epoch";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SplendourAblazeEpochMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();


        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenus.MENU_TYPES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModEntitySpawnPlacements::register);
        LOGGER.info("Common setup for {}", MOD_ID);
    }
}
