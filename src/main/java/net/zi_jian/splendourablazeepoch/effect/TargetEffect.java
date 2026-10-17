package net.zi_jian.splendourablazeepoch.effect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public final class TargetEffect extends MobEffect {
    public TargetEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) {
            return;
        }
        AABB area = new AABB(
                entity.getX() - 9.0D,
                entity.getY() - 3.0D,
                entity.getZ() - 9.0D,
                entity.getX() + 9.0D,
                entity.getY() + 3.0D,
                entity.getZ() + 9.0D
        );
        for (Entity nearby : entity.level().getEntities(entity, area)) {
            if (nearby instanceof Monster monster) {
                monster.setTarget(entity);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
