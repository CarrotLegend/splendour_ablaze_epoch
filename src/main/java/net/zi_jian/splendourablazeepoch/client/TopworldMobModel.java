package net.zi_jian.splendourablazeepoch.client;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;

public final class TopworldMobModel<T extends LivingEntity & GeoEntity> extends GeoModel<T> {
    private static final Map<String, String> MODEL_NAMES = Map.ofEntries(
            Map.entry("koifish", "koi_fish"),
            Map.entry("muskdeer", "musk_deer"),
            Map.entry("raccoondog", "raccoon_dog"),
            Map.entry("rustedancestors", "rusted_ancestors"),
            Map.entry("rustedwoman", "rusted_ancestors_w"),
            Map.entry("rustedchild", "rusted_child"),
            Map.entry("rusthound", "rust_hound"),
            Map.entry("rustrelics", "rust_relics"),
            Map.entry("rustrelicsb", "rust_relics"),
            Map.entry("rustrelicss", "rust_relics_s"),
            Map.entry("waterbuffalo", "water_buffalo"),
            Map.entry("ac", "rusted_ancestors_ac"),
            Map.entry("girlghost", "girl_ghost"),
            Map.entry("goldenhairhou", "goldenhaired_hou"),
            Map.entry("rustedchef", "rusted_chef"),
            Map.entry("cauldronbeast", "cauldron_beast"),
            Map.entry("terracottawarriorsguard", "terracotta_warriors_guard"),
            Map.entry("terracottageneral", "terracotta_general"),
            Map.entry("terracottawarriors", "terracotta_warriors_guard"),
            Map.entry("alivepictograph", "alive_pictograph"),
            Map.entry("castinscribedautomaton", "cast_inscribed_automaton"),
            Map.entry("flyarrowhead", "fly_arrowhead"),
            Map.entry("skyadministrator", "sky_administrator"),
            Map.entry("darkworm", "dark_worm"),
            Map.entry("pagewraith", "page_wraith"),
            Map.entry("pagegnat", "page_gnat"),
            Map.entry("firearmtigerguard", "firearm_tiger_guard")
    );

    private static final Map<String, String> TEXTURE_NAMES = Map.ofEntries(
            Map.entry("koifish", "koi_fish"),
            Map.entry("muskdeer", "musk_deer"),
            Map.entry("raccoondog", "raccoon_dog"),
            Map.entry("rustedancestors", "rusted_ancestors"),
            Map.entry("rustedwoman", "rusted_ancestors_w"),
            Map.entry("rustedchild", "rusted_child"),
            Map.entry("rusthound", "rust_hound"),
            Map.entry("rustrelics", "rust_relics"),
            Map.entry("rustrelicsb", "rust_relics"),
            Map.entry("rustrelicss", "rust_relics"),
            Map.entry("waterbuffalo", "water_buffalo"),
            Map.entry("ac", "rusted_ancestors"),
            Map.entry("girlghost", "girl_ghost"),
            Map.entry("goldenhairhou", "goldenhaired_hou"),
            Map.entry("rustedchef", "rusted_chef"),
            Map.entry("cauldronbeast", "cauldron_beast"),
            Map.entry("terracottawarriorsguard", "terracotta_warriors_guard"),
            Map.entry("terracottageneral", "terracotta_general"),
            Map.entry("terracottawarriors", "terracotta_warriors_guard"),
            Map.entry("alivepictograph", "alive_pictograph"),
            Map.entry("castinscribedautomaton", "cast_inscribed_automaton"),
            Map.entry("flyarrowhead", "fly_arrowhead"),
            Map.entry("skyadministrator", "sky_administrator"),
            Map.entry("darkworm", "dark_worm"),
            Map.entry("pagewraith", "page_wraith"),
            Map.entry("pagegnat", "page_gnat"),
            Map.entry("firearmtigerguard", "firearm_tiger_guard")
    );

    private String entityId(T entity) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (id == null) {
            throw new IllegalStateException("Unregistered entity type " + entity.getType());
        }
        return id.getPath();
    }

    private String modelName(T entity) {
        String id = entityId(entity);
        return MODEL_NAMES.getOrDefault(id, id);
    }

    private String textureName(T entity) {
        String id = entityId(entity);
        return TEXTURE_NAMES.getOrDefault(id, id);
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return new ResourceLocation(
                SplendourAblazeEpochMod.MOD_ID,
                "geo/" + modelName(entity) + ".geo.json"
        );
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return new ResourceLocation(
                SplendourAblazeEpochMod.MOD_ID,
                "textures/entities/" + textureName(entity) + ".png"
        );
    }

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return new ResourceLocation(
                SplendourAblazeEpochMod.MOD_ID,
                "animations/" + modelName(entity) + ".animation.json"
        );
    }
}
