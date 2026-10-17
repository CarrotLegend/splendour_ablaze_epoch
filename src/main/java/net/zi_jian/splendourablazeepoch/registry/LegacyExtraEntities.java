package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.entity.PatJinMaoHouEntity;

public final class LegacyExtraEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<EntityType<PatJinMaoHouEntity>> PAT_JIN_MAO_HOU =
            ENTITY_TYPES.register(
                    "patjinmaohou",
                    () -> EntityType.Builder.of(PatJinMaoHouEntity::new, MobCategory.MONSTER)
                            .sized(2.0F, 2.5F)
                            .clientTrackingRange(64)
                            .updateInterval(3)
                            .build("patjinmaohou")
            );

    @Mod.EventBusSubscriber(
            modid = SplendourAblazeEpochMod.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static final class AttributesHandler {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(PAT_JIN_MAO_HOU.get(), PatJinMaoHouEntity.createAttributes().build());
        }

        private AttributesHandler() {
        }
    }

    private LegacyExtraEntities() {
    }
}
