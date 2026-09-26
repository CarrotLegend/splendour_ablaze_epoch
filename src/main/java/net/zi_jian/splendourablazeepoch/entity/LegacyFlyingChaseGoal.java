package net.zi_jian.splendourablazeepoch.entity;

import java.util.EnumSet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public final class LegacyFlyingChaseGoal extends Goal {
    private final PathfinderMob mob;
    private final double speedModifier;

    public LegacyFlyingChaseGoal(PathfinderMob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (mob.getBoundingBox().intersects(target.getBoundingBox())) {
            mob.doHurtTarget(target);
            return;
        }
        if (mob.distanceToSqr(target) < 64.0D) {
            mob.getMoveControl().setWantedPosition(target.getX(), target.getEyeY(), target.getZ(), speedModifier);
        }
    }
}
