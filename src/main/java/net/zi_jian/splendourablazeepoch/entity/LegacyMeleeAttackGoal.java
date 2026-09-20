package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public final class LegacyMeleeAttackGoal extends MeleeAttackGoal {
    private final PathfinderMob attacker;

    public LegacyMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.attacker = mob;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return attacker.getBbWidth() * attacker.getBbWidth() + target.getBbWidth();
    }
}
