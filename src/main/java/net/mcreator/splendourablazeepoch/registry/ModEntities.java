package net.mcreator.splendourablazeepoch.registry;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.entity.MigratedTopworldMob;
import net.mcreator.splendourablazeepoch.entity.SkyDoorEntity;
import net.mcreator.splendourablazeepoch.world.TopworldClosure;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<EntityType<SkyDoorEntity>> SKY_DOOR = ENTITY_TYPES.register("splendourablazedoor",
            () -> EntityType.Builder.<SkyDoorEntity>of(SkyDoorEntity::new, MobCategory.MONSTER)
                    .sized(3.0F, 1.0F).clientTrackingRange(64).updateInterval(3).fireImmune()
                    .build("splendourablazedoor"));
    public static final Map<String, RegistryObject<EntityType<MigratedTopworldMob>>> TOPWORLD_MOBS = new LinkedHashMap<>();

    static {
        register("croaker", MobCategory.WATER_CREATURE, 0.8F, 0.8F);
        register("koifish", MobCategory.WATER_CREATURE, 0.5F, 0.5F);
        register("magpie", MobCategory.CREATURE, 0.6F, 0.6F);
        register("messenger", MobCategory.MONSTER, 1.0F, 1.0F);
        register("muskdeer", MobCategory.CREATURE, 0.6F, 1.8F);
        register("peacock", MobCategory.CREATURE, 1.0F, 1.0F);
        register("pheasant", MobCategory.CREATURE, 0.6F, 0.6F);
        register("raccoondog", MobCategory.CREATURE, 1.0F, 0.8F);
        register("rustedancestors", MobCategory.AMBIENT, 0.6F, 1.8F);
        register("rustedwoman", MobCategory.AMBIENT, 0.6F, 1.8F);
        register("rusthound", MobCategory.AMBIENT, 0.6F, 1.0F);
        register("rustrelics", MobCategory.AMBIENT, 0.6F, 1.8F);
        register("waterbuffalo", MobCategory.CREATURE, 1.0F, 1.5F);
        if (!TOPWORLD_MOBS.keySet().equals(new java.util.LinkedHashSet<>(TopworldClosure.SPAWN_ENTITY_IDS))) {
            throw new IllegalStateException("Topworld entity closure and registry differ");
        }
    }

    private static void register(String id, MobCategory category, float width, float height) {
        TOPWORLD_MOBS.put(id, ENTITY_TYPES.register(id,
                () -> EntityType.Builder.<MigratedTopworldMob>of((type, level) -> new MigratedTopworldMob(type, level, id), category)
                        .sized(width, height).clientTrackingRange(64).updateInterval(3).build(id)));
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 0.0D).add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
    }

    @Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class AttributesHandler {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(SKY_DOOR.get(), attributes().build());
            TOPWORLD_MOBS.values().forEach(type -> event.put(type.get(), attributes().build()));
        }
    }

    private ModEntities() {
    }
}
