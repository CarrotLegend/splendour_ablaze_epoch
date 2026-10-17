package net.zi_jian.splendourablazeepoch.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public final class ParalyticToxinEffect extends MobEffect {
    public ParalyticToxinEffect() {
        super(MobEffectCategory.HARMFUL, 0x17201A);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public void applyInstantenousEffect(
            Entity source,
            Entity attacker,
            LivingEntity target,
            int amplifier,
            double health
    ) {
        applyEffectTick(target, amplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) {
            return;
        }
        entity.hurt(entity.damageSources().magic(), 0.2F);
        if (entity.getRandom().nextInt(10) == 4) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25D, 0.05D, 0.25D));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
