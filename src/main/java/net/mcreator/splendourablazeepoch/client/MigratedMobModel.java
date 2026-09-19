package net.mcreator.splendourablazeepoch.client;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.entity.MigratedTopworldMob;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public final class MigratedMobModel extends GeoModel<MigratedTopworldMob> {
    private static final Map<String, String> STEMS = Map.ofEntries(
            Map.entry("koifish", "koi_fish"), Map.entry("muskdeer", "musk_deer"),
            Map.entry("raccoondog", "raccoon_dog"), Map.entry("rustedancestors", "rusted_ancestors"),
            Map.entry("rustedwoman", "rusted_ancestors_w"), Map.entry("rusthound", "rust_hound"),
            Map.entry("rustrelics", "rust_relics"), Map.entry("waterbuffalo", "water_buffalo"),
            Map.entry("splendour_ablaze_door", "splendour_ablaze_foor")
    );

    private String stem(MigratedTopworldMob entity) {
        return STEMS.getOrDefault(entity.visualId(), entity.visualId());
    }

    @Override
    public ResourceLocation getModelResource(MigratedTopworldMob entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "geo/" + stem(entity) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MigratedTopworldMob entity) {
        String texture = entity.visualId().equals("splendour_ablaze_door") ? "splendour_ablaze_door" : stem(entity);
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "textures/entities/" + texture + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MigratedTopworldMob entity) {
        return new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "animations/" + stem(entity) + ".animation.json");
    }
}
