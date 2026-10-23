package net.zi_jian.splendourablazeepoch.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;

@Mod.EventBusSubscriber(
        modid = SplendourAblazeEpochMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class Phase10ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                ModEntities.PAT_JIN_MAO_HOU.get(),
                PatJinMaoHouRenderer::new
        );
    }

    private Phase10ClientEvents() {
    }
}
