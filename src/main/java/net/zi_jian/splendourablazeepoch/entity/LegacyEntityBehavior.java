package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;

public final class LegacyEntityBehavior {
    private static final long CYCLE_LENGTH = 121L;

    public static boolean isMovePulse(PathfinderMob mob) {
        return mob.level().getGameTime() % CYCLE_LENGTH == 0L;
    }

    public static boolean isFlyWindow(PathfinderMob mob) {
        return mob.level().getGameTime() % CYCLE_LENGTH == 120L;
    }

    public static void moveRandom2D(PathfinderMob mob, double range, double speed) {
        int radius = (int) range;
        double x = mob.getX() + Mth.nextInt(mob.getRandom(), -radius, radius);
        double z = mob.getZ() + Mth.nextInt(mob.getRandom(), -radius, radius);
        mob.getNavigation().moveTo(x, mob.getY(), z, speed);
    }

    public static void moveRandom3D(PathfinderMob mob, double horizontalRange, double verticalRange, double speed) {
        int horizontalRadius = (int) horizontalRange;
        int verticalRadius = (int) verticalRange;
        double x = mob.getX() + Mth.nextInt(mob.getRandom(), -horizontalRadius, horizontalRadius);
        double y = mob.getY() + Mth.nextInt(mob.getRandom(), -verticalRadius, verticalRadius);
        double z = mob.getZ() + Mth.nextInt(mob.getRandom(), -horizontalRadius, horizontalRadius);
        mob.getNavigation().moveTo(x, y, z, speed);
    }

    public static void fleeAfterHurt(PathfinderMob mob) {
        if (!mob.level().dimension().equals(SkyDoorEntity.TOPWORLD)) {
            return;
        }
        moveRandom2D(mob, 10.0D, 1.5D);
    }

    private LegacyEntityBehavior() {
    }
}
