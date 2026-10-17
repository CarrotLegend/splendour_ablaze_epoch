package net.zi_jian.splendourablazeepoch.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModEnchantments;

@Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class InorganicDestructionEvents {
    private static final TagKey<EntityType<?>> INORGANIC = TagKey.create(
            Registries.ENTITY_TYPE,
            new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "inorganic_entity")
    );

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide || !target.getType().is(INORGANIC)) {
            return;
        }

        Entity source = event.getSource().getEntity();
        if (!(source instanceof LivingEntity attacker)) {
            return;
        }

        int level = EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantments.INORGANIC_DESTRUCTION.get(),
                attacker.getMainHandItem()
        );
        if (level <= 0) {
            return;
        }

        // 与原版相同，在正常伤害结算前直接扣除生命值，不再次触发伤害事件。
        target.setHealth(Math.max(0.0F, target.getHealth() - level * 2.0F));
    }

    private InorganicDestructionEvents() {
    }
}
