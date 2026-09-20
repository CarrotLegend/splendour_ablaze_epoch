package net.zi_jian.splendourablazeepoch.client;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;

public final class TopworldMobModel<T extends LivingEntity & GeoEntity> extends GeoModel<T> {
    private static final Map<String, String> RESOURCE_NAMES = Map.ofEntries(
            Map.entry("koifish", "koi_fish"),
            Map.entry("muskdeer", "musk_deer"),
            Map.entry("raccoondog", "raccoon_dog"),
            Map.entry("rustedancestors", "rusted_ancestors"),
            Map.entry("rustedwoman", "rusted_ancestors_w"),
            Map.entry("rusthound", "rust_hound"),
            Map.entry("rustrelics", "rust_relics"),
            Map.entry("waterbuffalo", "water_buffalo")
    );

    private String resourceName(T entity) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (id == null) {
            throw new IllegalStateException("Unregistered entity type " + entity.getType());
        }
        return RESOURCE_NAMES.getOrDefault(id.getPath(), id.getPath());
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "geo/" + resourceName(entity) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "textures/entities/" + resourceName(entity) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "animations/" + resourceName(entity) + ".animation.json");
    }
}
