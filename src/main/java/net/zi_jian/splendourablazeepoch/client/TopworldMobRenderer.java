package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class TopworldMobRenderer<T extends LivingEntity & GeoEntity> extends GeoEntityRenderer<T> {
    public TopworldMobRenderer(EntityRendererProvider.Context context) {
        super(context, new TopworldMobModel<>());
        shadowRadius = 0.5F;
    }
}
