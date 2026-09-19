package net.mcreator.splendourablazeepoch.client;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.entity.SkyDoorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class SkyDoorModel extends GeoModel<SkyDoorEntity> {
    @Override
    public ResourceLocation getModelResource(SkyDoorEntity entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "geo/splendour_ablaze_foor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkyDoorEntity entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "textures/entities/splendour_ablaze_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkyDoorEntity entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "animations/splendour_ablaze_foor.animation.json");
    }
}
