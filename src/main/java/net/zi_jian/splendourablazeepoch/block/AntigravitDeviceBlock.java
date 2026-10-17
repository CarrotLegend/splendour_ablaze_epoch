package net.zi_jian.splendourablazeepoch.block;

import java.util.Comparator;
import java.util.PriorityQueue;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModParticles;

@Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AntigravitDeviceBlock extends Block {
    private static final PriorityQueue<DelayedEffect> DELAYED_EFFECTS =
            new PriorityQueue<>(Comparator.comparingLong(DelayedEffect::dueTick));

    public AntigravitDeviceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level instanceof ServerLevel serverLevel) {
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 1200, 1, false, false));
                DELAYED_EFFECTS.add(new DelayedEffect(serverLevel.getServer().getTickCount() + 1200L, living));
            }
            serverLevel.sendParticles(ModParticles.FLOWING.get(), pos.getX(), pos.getY(), pos.getZ(),
                    20, 1.0D, 0.0D, 1.0D, 0.001D);
        }
        super.stepOn(level, pos, state, entity);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        long tick = event.getServer().getTickCount();
        while (!DELAYED_EFFECTS.isEmpty() && DELAYED_EFFECTS.peek().dueTick() <= tick) {
            LivingEntity entity = DELAYED_EFFECTS.remove().entity();
            if (!entity.level().isClientSide) {
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 1, false, false));
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        DELAYED_EFFECTS.clear();
    }

    private record DelayedEffect(long dueTick, LivingEntity entity) {
    }
}
