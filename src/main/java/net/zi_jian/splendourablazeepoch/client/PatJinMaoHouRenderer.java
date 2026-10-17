package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zi_jian.splendourablazeepoch.entity.PatJinMaoHouEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class PatJinMaoHouRenderer extends GeoEntityRenderer<PatJinMaoHouEntity> {
    public PatJinMaoHouRenderer(EntityRendererProvider.Context context) {
        super(context, new PatJinMaoHouModel());
        shadowRadius = 0.8F;
    }
}
