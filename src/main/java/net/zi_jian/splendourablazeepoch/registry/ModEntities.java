package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.entity.AwakenedAncestorEntity;
import net.zi_jian.splendourablazeepoch.entity.CroakerEntity;
import net.zi_jian.splendourablazeepoch.entity.KoiFishEntity;
import net.zi_jian.splendourablazeepoch.entity.MagpieEntity;
import net.zi_jian.splendourablazeepoch.entity.MessengerEntity;
import net.zi_jian.splendourablazeepoch.entity.MuskDeerEntity;
import net.zi_jian.splendourablazeepoch.entity.PeacockEntity;
import net.zi_jian.splendourablazeepoch.entity.PheasantEntity;
import net.zi_jian.splendourablazeepoch.entity.RaccoonDogEntity;
import net.zi_jian.splendourablazeepoch.entity.RustHoundEntity;
import net.zi_jian.splendourablazeepoch.entity.RustRelicsBowEntity;
import net.zi_jian.splendourablazeepoch.entity.RustRelicsEntity;
import net.zi_jian.splendourablazeepoch.entity.RustRelicsSwordEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedAncestorsEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedChildEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedWomanEntity;
import net.zi_jian.splendourablazeepoch.entity.SkyDoorEntity;
import net.zi_jian.splendourablazeepoch.entity.WaterBuffaloEntity;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITY_TYPES,
            SplendourAblazeEpochMod.MOD_ID
    );

    public static final RegistryObject<EntityType<SkyDoorEntity>> SKY_DOOR = register(
            "splendourablazedoor", SkyDoorEntity::new, MobCategory.MISC, 3.0F, 1.0F, true
    );
    public static final RegistryObject<EntityType<CroakerEntity>> CROAKER = register(
            "croaker", CroakerEntity::new, MobCategory.WATER_CREATURE, 0.8F, 0.8F
    );
    public static final RegistryObject<EntityType<KoiFishEntity>> KOI_FISH = register(
            "koifish", KoiFishEntity::new, MobCategory.WATER_CREATURE, 0.5F, 0.5F
    );
    public static final RegistryObject<EntityType<MagpieEntity>> MAGPIE = register(
            "magpie", MagpieEntity::new, MobCategory.CREATURE, 0.6F, 0.6F
    );
    public static final RegistryObject<EntityType<MessengerEntity>> MESSENGER = register(
            "messenger", MessengerEntity::new, MobCategory.MONSTER, 1.0F, 1.0F
    );
    public static final RegistryObject<EntityType<MuskDeerEntity>> MUSK_DEER = register(
            "muskdeer", MuskDeerEntity::new, MobCategory.CREATURE, 0.6F, 1.8F
    );
    public static final RegistryObject<EntityType<PeacockEntity>> PEACOCK = register(
            "peacock", PeacockEntity::new, MobCategory.CREATURE, 1.0F, 1.0F
    );
    public static final RegistryObject<EntityType<PheasantEntity>> PHEASANT = register(
            "pheasant", PheasantEntity::new, MobCategory.CREATURE, 0.6F, 0.6F
    );
    public static final RegistryObject<EntityType<RaccoonDogEntity>> RACCOON_DOG = register(
            "raccoondog", RaccoonDogEntity::new, MobCategory.CREATURE, 1.0F, 0.8F
    );
    public static final RegistryObject<EntityType<RustedAncestorsEntity>> RUSTED_ANCESTORS = register(
            "rustedancestors", RustedAncestorsEntity::new, MobCategory.AMBIENT, 0.6F, 1.8F
    );
    public static final RegistryObject<EntityType<RustedWomanEntity>> RUSTED_WOMAN = register(
            "rustedwoman", RustedWomanEntity::new, MobCategory.AMBIENT, 0.6F, 1.8F
    );
    public static final RegistryObject<EntityType<RustedChildEntity>> RUSTED_CHILD = register(
            "rustedchild", RustedChildEntity::new, MobCategory.AMBIENT, 0.6F, 1.0F
    );
    public static final RegistryObject<EntityType<RustHoundEntity>> RUST_HOUND = register(
            "rusthound", RustHoundEntity::new, MobCategory.AMBIENT, 0.6F, 1.0F
    );
    public static final RegistryObject<EntityType<RustRelicsEntity>> RUST_RELICS = register(
            "rustrelics", RustRelicsEntity::new, MobCategory.AMBIENT, 0.6F, 1.8F
    );
    public static final RegistryObject<EntityType<RustRelicsBowEntity>> RUST_RELICS_BOW = register(
            "rustrelicsb", RustRelicsBowEntity::new, MobCategory.AMBIENT, 0.6F, 1.8F
    );
    public static final RegistryObject<EntityType<RustRelicsSwordEntity>> RUST_RELICS_SWORD = register(
            "rustrelicss", RustRelicsSwordEntity::new, MobCategory.AMBIENT, 0.6F, 1.8F
    );
    public static final RegistryObject<EntityType<WaterBuffaloEntity>> WATER_BUFFALO = register(
            "waterbuffalo", WaterBuffaloEntity::new, MobCategory.CREATURE, 1.0F, 1.5F
    );
    public static final RegistryObject<EntityType<AwakenedAncestorEntity>> AWAKENED_ANCESTOR = register(
            "ac", AwakenedAncestorEntity::new, MobCategory.MONSTER, 0.6F, 1.8F
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(
            String id,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            float width,
            float height
    ) {
        return register(id, factory, category, width, height, false);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(
            String id,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            float width,
            float height,
            boolean fireImmune
    ) {
        return ENTITY_TYPES.register(id, () -> {
            EntityType.Builder<T> builder = EntityType.Builder.of(factory, category)
                    .sized(width, height)
                    .clientTrackingRange(64)
                    .updateInterval(3);
            if (fireImmune) {
                builder.fireImmune();
            }
            return builder.build(id);
        });
    }

    @Mod.EventBusSubscriber(
            modid = SplendourAblazeEpochMod.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static final class AttributesHandler {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(CROAKER.get(), CroakerEntity.createAttributes().build());
            event.put(KOI_FISH.get(), KoiFishEntity.createAttributes().build());
            event.put(MAGPIE.get(), MagpieEntity.createAttributes().build());
            event.put(MESSENGER.get(), MessengerEntity.createAttributes().build());
            event.put(MUSK_DEER.get(), MuskDeerEntity.createAttributes().build());
            event.put(PEACOCK.get(), PeacockEntity.createAttributes().build());
            event.put(PHEASANT.get(), PheasantEntity.createAttributes().build());
            event.put(RACCOON_DOG.get(), RaccoonDogEntity.createAttributes().build());
            event.put(RUSTED_ANCESTORS.get(), RustedAncestorsEntity.createAttributes().build());
            event.put(RUSTED_WOMAN.get(), RustedWomanEntity.createAttributes().build());
            event.put(RUSTED_CHILD.get(), RustedChildEntity.createAttributes().build());
            event.put(RUST_HOUND.get(), RustHoundEntity.createAttributes().build());
            event.put(RUST_RELICS.get(), RustRelicsEntity.createAttributes().build());
            event.put(RUST_RELICS_BOW.get(), RustRelicsBowEntity.createAttributes().build());
            event.put(RUST_RELICS_SWORD.get(), RustRelicsSwordEntity.createAttributes().build());
            event.put(WATER_BUFFALO.get(), WaterBuffaloEntity.createAttributes().build());
            event.put(AWAKENED_ANCESTOR.get(), AwakenedAncestorEntity.createAttributes().build());
        }

        private AttributesHandler() {
        }
    }

    private ModEntities() {
    }
}
