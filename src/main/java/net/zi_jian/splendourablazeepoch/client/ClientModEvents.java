package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.entity.AlivePictographEntity;
import net.zi_jian.splendourablazeepoch.entity.AwakenedAncestorEntity;
import net.zi_jian.splendourablazeepoch.entity.CastInscribedAutomatonEntity;
import net.zi_jian.splendourablazeepoch.entity.CauldronBeastEntity;
import net.zi_jian.splendourablazeepoch.entity.CroakerEntity;
import net.zi_jian.splendourablazeepoch.entity.DarkWormEntity;
import net.zi_jian.splendourablazeepoch.entity.FirearmTigerGuardEntity;
import net.zi_jian.splendourablazeepoch.entity.FlyArrowheadEntity;
import net.zi_jian.splendourablazeepoch.entity.GirlGhostEntity;
import net.zi_jian.splendourablazeepoch.entity.GoldenHairHouEntity;
import net.zi_jian.splendourablazeepoch.entity.KoiFishEntity;
import net.zi_jian.splendourablazeepoch.entity.MagpieEntity;
import net.zi_jian.splendourablazeepoch.entity.MessengerEntity;
import net.zi_jian.splendourablazeepoch.entity.MuskDeerEntity;
import net.zi_jian.splendourablazeepoch.entity.PageGnatEntity;
import net.zi_jian.splendourablazeepoch.entity.PageWraithEntity;
import net.zi_jian.splendourablazeepoch.entity.PeacockEntity;
import net.zi_jian.splendourablazeepoch.entity.PheasantEntity;
import net.zi_jian.splendourablazeepoch.entity.RaccoonDogEntity;
import net.zi_jian.splendourablazeepoch.entity.RustHoundEntity;
import net.zi_jian.splendourablazeepoch.entity.RustRelicsBowEntity;
import net.zi_jian.splendourablazeepoch.entity.RustRelicsEntity;
import net.zi_jian.splendourablazeepoch.entity.RustRelicsSwordEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedAncestorsEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedChefEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedChildEntity;
import net.zi_jian.splendourablazeepoch.entity.RustedWomanEntity;
import net.zi_jian.splendourablazeepoch.entity.SkyAdministratorEntity;
import net.zi_jian.splendourablazeepoch.entity.TerracottaGeneralEntity;
import net.zi_jian.splendourablazeepoch.entity.TerracottaWarriorsEntity;
import net.zi_jian.splendourablazeepoch.entity.TerracottaWarriorsGuardEntity;
import net.zi_jian.splendourablazeepoch.entity.WaterBuffaloEntity;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;
import net.zi_jian.splendourablazeepoch.registry.ModMenus;

@Mod.EventBusSubscriber(
        modid = SplendourAblazeEpochMod.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SKY_DOOR.get(), SkyDoorRenderer::new);
        event.registerEntityRenderer(ModEntities.CROAKER.get(), context -> new TopworldMobRenderer<CroakerEntity>(context));
        event.registerEntityRenderer(ModEntities.KOI_FISH.get(), context -> new TopworldMobRenderer<KoiFishEntity>(context));
        event.registerEntityRenderer(ModEntities.MAGPIE.get(), context -> new TopworldMobRenderer<MagpieEntity>(context));
        event.registerEntityRenderer(ModEntities.MESSENGER.get(), context -> new TopworldMobRenderer<MessengerEntity>(context));
        event.registerEntityRenderer(ModEntities.MUSK_DEER.get(), context -> new TopworldMobRenderer<MuskDeerEntity>(context));
        event.registerEntityRenderer(ModEntities.PEACOCK.get(), context -> new TopworldMobRenderer<PeacockEntity>(context));
        event.registerEntityRenderer(ModEntities.PHEASANT.get(), context -> new TopworldMobRenderer<PheasantEntity>(context));
        event.registerEntityRenderer(ModEntities.RACCOON_DOG.get(), context -> new TopworldMobRenderer<RaccoonDogEntity>(context));
        event.registerEntityRenderer(ModEntities.RUSTED_ANCESTORS.get(), context -> new TopworldMobRenderer<RustedAncestorsEntity>(context));
        event.registerEntityRenderer(ModEntities.RUSTED_WOMAN.get(), context -> new TopworldMobRenderer<RustedWomanEntity>(context));
        event.registerEntityRenderer(ModEntities.RUSTED_CHILD.get(), context -> new TopworldMobRenderer<RustedChildEntity>(context));
        event.registerEntityRenderer(ModEntities.RUST_HOUND.get(), context -> new TopworldMobRenderer<RustHoundEntity>(context));
        event.registerEntityRenderer(ModEntities.RUST_RELICS.get(), context -> new TopworldMobRenderer<RustRelicsEntity>(context));
        event.registerEntityRenderer(ModEntities.RUST_RELICS_BOW.get(), context -> new TopworldMobRenderer<RustRelicsBowEntity>(context));
        event.registerEntityRenderer(ModEntities.RUST_RELICS_SWORD.get(), context -> new TopworldMobRenderer<RustRelicsSwordEntity>(context));
        event.registerEntityRenderer(ModEntities.WATER_BUFFALO.get(), context -> new TopworldMobRenderer<WaterBuffaloEntity>(context));
        event.registerEntityRenderer(ModEntities.AWAKENED_ANCESTOR.get(), context -> new TopworldMobRenderer<AwakenedAncestorEntity>(context));
        event.registerEntityRenderer(ModEntities.GIRL_GHOST.get(), context -> new TopworldMobRenderer<GirlGhostEntity>(context));
        event.registerEntityRenderer(ModEntities.GOLDEN_HAIR_HOU.get(), context -> new TopworldMobRenderer<GoldenHairHouEntity>(context));
        event.registerEntityRenderer(ModEntities.RUSTED_CHEF.get(), context -> new TopworldMobRenderer<RustedChefEntity>(context));
        event.registerEntityRenderer(ModEntities.CAULDRON_BEAST.get(), context -> new TopworldMobRenderer<CauldronBeastEntity>(context));
        event.registerEntityRenderer(ModEntities.TERRACOTTA_WARRIORS_GUARD.get(), context -> new TopworldMobRenderer<TerracottaWarriorsGuardEntity>(context));
        event.registerEntityRenderer(ModEntities.TERRACOTTA_GENERAL.get(), context -> new TopworldMobRenderer<TerracottaGeneralEntity>(context));
        event.registerEntityRenderer(ModEntities.TERRACOTTA_WARRIORS.get(), context -> new TopworldMobRenderer<TerracottaWarriorsEntity>(context));
        event.registerEntityRenderer(ModEntities.ALIVE_PICTOGRAPH.get(), context -> new TopworldMobRenderer<AlivePictographEntity>(context));
        event.registerEntityRenderer(ModEntities.CAST_INSCRIBED_AUTOMATON.get(), context -> new TopworldMobRenderer<CastInscribedAutomatonEntity>(context));
        event.registerEntityRenderer(ModEntities.FLY_ARROWHEAD.get(), context -> new TopworldMobRenderer<FlyArrowheadEntity>(context));
        event.registerEntityRenderer(ModEntities.SKY_ADMINISTRATOR.get(), context -> new TopworldMobRenderer<SkyAdministratorEntity>(context));
        event.registerEntityRenderer(ModEntities.DARK_WORM.get(), context -> new TopworldMobRenderer<DarkWormEntity>(context));
        event.registerEntityRenderer(ModEntities.PAGE_WRAITH.get(), context -> new TopworldMobRenderer<PageWraithEntity>(context));
        event.registerEntityRenderer(ModEntities.PAGE_GNAT.get(), context -> new TopworldMobRenderer<PageGnatEntity>(context));
        event.registerEntityRenderer(ModEntities.FIREARM_TIGER_GUARD.get(), context -> new TopworldMobRenderer<FirearmTigerGuardEntity>(context));
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex != 0) {
                return 0xFFFFFF;
            }
            if (level != null && pos != null) {
                return BiomeColors.getAverageGrassColor(level, pos);
            }
            return GrassColor.getDefaultColor();
        }, ModBlocks.INK_GRASS_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 ? GrassColor.getDefaultColor() : 0xFFFFFF,
                ModBlocks.INK_GRASS_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "topworld"),
                new DimensionSpecialEffects(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
                    @Override
                    public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                        return color.multiply(
                                sunHeight * 0.94F + 0.06F,
                                sunHeight * 0.94F + 0.06F,
                                sunHeight * 0.91F + 0.09F
                        );
                    }

                    @Override
                    public boolean isFoggyAt(int x, int y) {
                        return false;
                    }
                }
        );
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.FORGING_FURNACE_GUI.get(), ForgingFurnaceScreen::new);
            MenuScreens.register(ModMenus.PRINT_TABLE_GUI.get(), PrintTableScreen::new);
        });
    }

    private ClientModEvents() {
    }
}
