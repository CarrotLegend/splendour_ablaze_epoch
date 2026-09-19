package net.mcreator.splendourablazeepoch.client;

import net.mcreator.splendourablazeepoch.entity.SkyDoorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class SkyDoorRenderer extends GeoEntityRenderer<SkyDoorEntity> {
    public SkyDoorRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyDoorModel());
        this.shadowRadius = 0.5F;
        addRenderLayer(new SkyDoorGlowLayer(this));
    }
}
