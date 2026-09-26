package net.zi_jian.splendourablazeepoch.entity;

import java.util.EnumSet;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public final class LegacyFlyingRandomStrollGoal extends Goal {
    private final PathfinderMob mob;
    private final double speedModifier;
    private final int interval;
    private final double horizontalRange;
    private final double verticalRange;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    public LegacyFlyingRandomStrollGoal(PathfinderMob mob, double speedModifier, int interval, double horizontalRange, double verticalRange) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.interval = interval;
        this.horizontalRange = horizontalRange;
        this.verticalRange = verticalRange;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!mob.getNavigation().isDone() || mob.getRandom().nextInt(interval) != 0) {
            return false;
        }
        wantedX = mob.getX() + (mob.getRandom().nextDouble() * 2.0D - 1.0D) * horizontalRange;
        wantedY = mob.getY() + (mob.getRandom().nextDouble() * 2.0D - 1.0D) * verticalRange;
        wantedZ = mob.getZ() + (mob.getRandom().nextDouble() * 2.0D - 1.0D) * horizontalRange;
        return true;
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone();
    }
}
