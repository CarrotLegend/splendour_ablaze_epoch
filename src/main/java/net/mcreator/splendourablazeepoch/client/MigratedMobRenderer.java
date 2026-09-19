package net.mcreator.splendourablazeepoch.client;

import net.mcreator.splendourablazeepoch.entity.MigratedTopworldMob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class MigratedMobRenderer extends GeoEntityRenderer<MigratedTopworldMob> {
    public MigratedMobRenderer(EntityRendererProvider.Context context) {
        super(context, new MigratedMobModel());
        this.shadowRadius = 0.5F;
    }
}
