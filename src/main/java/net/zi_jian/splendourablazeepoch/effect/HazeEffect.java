package net.zi_jian.splendourablazeepoch.effect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class HazeEffect extends MobEffect {
    public HazeEffect() {
        super(MobEffectCategory.HARMFUL, 0x554B42);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Items.TOTEM_OF_UNDYING));
        return items;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity instanceof Mob mob) {
            mob.setTarget(entity);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
