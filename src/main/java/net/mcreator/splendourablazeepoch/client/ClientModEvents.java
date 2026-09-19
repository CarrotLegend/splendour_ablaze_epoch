package net.mcreator.splendourablazeepoch.client;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.registry.ModEntities;
import net.mcreator.splendourablazeepoch.registry.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SKY_DOOR.get(), SkyDoorRenderer::new);
        ModEntities.TOPWORLD_MOBS.values().forEach(type -> event.registerEntityRenderer(type.get(), MigratedMobRenderer::new));
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "topworld"),
                new DimensionSpecialEffects(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
                    @Override
                    public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                        return color.multiply(sunHeight * 0.94F + 0.06F, sunHeight * 0.94F + 0.06F, sunHeight * 0.91F + 0.09F);
                    }

                    @Override
                    public boolean isFoggyAt(int x, int y) { return false; }
                });
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModMenus.FORGING_FURNACE_GUI.get(), ForgingFurnaceScreen::new));
    }

    private ClientModEvents() {}
}
