package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.resources.ResourceLocation;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.entity.PatJinMaoHouEntity;
import software.bernie.geckolib.model.GeoModel;

public final class PatJinMaoHouModel extends GeoModel<PatJinMaoHouEntity> {
    @Override
    public ResourceLocation getModelResource(PatJinMaoHouEntity entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "geo/goldenhaired_hou.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PatJinMaoHouEntity entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "textures/entities/goldenhaired_hou.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PatJinMaoHouEntity entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "animations/goldenhaired_hou.animation.json");
    }
}
