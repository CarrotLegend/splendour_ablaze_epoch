package net.zi_jian.splendourablazeepoch.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.entity.SkyDoorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class SkyDoorGlowLayer extends GeoRenderLayer<SkyDoorEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SplendourAblazeEpochMod.MOD_ID,
            "textures/entities/splendour_ablaze_door.png");

    public SkyDoorGlowLayer(GeoRenderer<SkyDoorEntity> renderer) { super(renderer); }

    @Override
    public void render(PoseStack poseStack, SkyDoorEntity entity, BakedGeoModel model, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                       int packedLight, int packedOverlay) {
        RenderType glow = RenderType.entityTranslucentEmissive(TEXTURE);
        getRenderer().reRender(getDefaultBakedModel(entity), poseStack, bufferSource, entity, glow,
                bufferSource.getBuffer(glow), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}
