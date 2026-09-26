package net.zi_jian.splendourablazeepoch.entity;

import java.util.EnumSet;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public final class LegacyRelicRangedAttackGoal extends Goal {
    private final Mob mob;
    private final RangedAttackMob rangedAttackMob;
    private final double speedModifier;
    private final int attackIntervalMin;
    private final int attackIntervalMax;
    private final float attackRadius;
    private final float attackRadiusSqr;
    private LivingEntity target;
    private int attackTime = -1;
    private int seeTime;

    public LegacyRelicRangedAttackGoal(
            RangedAttackMob rangedAttackMob,
            double speedModifier,
            int attackInterval,
            float attackRadius
    ) {
        this(rangedAttackMob, speedModifier, attackInterval, attackInterval, attackRadius);
    }

    public LegacyRelicRangedAttackGoal(
            RangedAttackMob rangedAttackMob,
            double speedModifier,
            int attackIntervalMin,
            int attackIntervalMax,
            float attackRadius
    ) {
        if (!(rangedAttackMob instanceof Mob mob)) {
            throw new IllegalArgumentException("LegacyRelicRangedAttackGoal requires a Mob implementing RangedAttackMob");
        }
        this.rangedAttackMob = rangedAttackMob;
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackIntervalMin = attackIntervalMin;
        this.attackIntervalMax = attackIntervalMax;
        this.attackRadius = attackRadius;
        this.attackRadiusSqr = attackRadius * attackRadius;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity currentTarget = mob.getTarget();
        if (currentTarget == null || !currentTarget.isAlive()) {
            return false;
        }
        target = currentTarget;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void stop() {
        target = null;
        seeTime = 0;
        attackTime = -1;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (target == null) {
            return;
        }

        double distanceSqr = mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean canSee = mob.getSensing().hasLineOfSight(target);

        if (canSee) {
            seeTime++;
        } else {
            seeTime = 0;
        }

        if (distanceSqr <= attackRadiusSqr && seeTime >= 5) {
            mob.getNavigation().stop();
        } else {
            mob.getNavigation().moveTo(target, speedModifier);
        }

        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        attackTime--;
        if (attackTime == 0) {
            if (!canSee) {
                return;
            }
            float distanceFactor = (float) Math.sqrt(distanceSqr) / attackRadius;
            float power = Mth.clamp(distanceFactor, 0.1F, 1.0F);
            rangedAttackMob.performRangedAttack(target, power);
            attackTime = Mth.floor(distanceFactor * (attackIntervalMax - attackIntervalMin) + attackIntervalMin);
        } else if (attackTime < 0) {
            attackTime = Mth.floor(Mth.lerp(Math.sqrt(distanceSqr) / attackRadius, attackIntervalMin, attackIntervalMax));
        }
    }
}
