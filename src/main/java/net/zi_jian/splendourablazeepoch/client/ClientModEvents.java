package net.zi_jian.splendourablazeepoch.client;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = SplendourAblazeEpochMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerEntityRenderer(
                ModEntities.SKY_DOOR.get(),
                SkyDoorRenderer::new
        );

        ModEntities.TOPWORLD_MOBS
                .values()
                .forEach(
                        type -> event.registerEntityRenderer(
                                type.get(),
                                MigratedMobRenderer::new
                        )
                );
    }

    @SubscribeEvent
    public static void registerBlockColors(
            RegisterColorHandlersEvent.Block event
    ) {
        event.register(
                (state, level, pos, tintIndex) -> {

                    if (tintIndex != 0) {
                        return 0xFFFFFF;
                    }

                    if (level != null && pos != null) {
                        return BiomeColors.getAverageGrassColor(
                                level,
                                pos
                        );
                    }

                    return GrassColor.getDefaultColor();
                },
                ModBlocks.INK_GRASS_BLOCK.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(
            RegisterColorHandlersEvent.Item event
    ) {
        event.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 0) {
                        return GrassColor.getDefaultColor();
                    }

                    return 0xFFFFFF;
                },
                ModBlocks.INK_GRASS_BLOCK.get()
        );
    }

    @SubscribeEvent
    public static void registerDimensionEffects(
            RegisterDimensionSpecialEffectsEvent event
    ) {
        event.register(
                new ResourceLocation(
                        SplendourAblazeEpochMod.MOD_ID,
                        "topworld"
                ),
                new DimensionSpecialEffects(
                        192.0F,
                        true,
                        DimensionSpecialEffects.SkyType.NORMAL,
                        false,
                        false
                ) {
                    @Override
                    public Vec3 getBrightnessDependentFogColor(
                            Vec3 color,
                            float sunHeight
                    ) {
                        return color.multiply(
                                sunHeight * 0.94F + 0.06F,
                                sunHeight * 0.94F + 0.06F,
                                sunHeight * 0.91F + 0.09F
                        );
                    }

                    @Override
                    public boolean isFoggyAt(
                            int x,
                            int y
                    ) {
                        return false;
                    }
                }
        );
    }

    @SubscribeEvent
    public static void clientSetup(
            FMLClientSetupEvent event
    ) {
        event.enqueueWork(
                () -> {
                        MenuScreens.register(
                                ModMenus.FORGING_FURNACE_GUI.get(),
                                ForgingFurnaceScreen::new
                        );

                        MenuScreens.register(
                                ModMenus.PRINT_TABLE_GUI.get(),
                                PrintTableScreen::new
                        );
                }
        );
    }

    private ClientModEvents() {
    }
}
