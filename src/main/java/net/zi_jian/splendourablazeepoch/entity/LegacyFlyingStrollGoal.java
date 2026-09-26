package net.zi_jian.splendourablazeepoch.entity;

import java.util.function.BooleanSupplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

public final class LegacyFlyingStrollGoal extends RandomStrollGoal {
    private final PathfinderMob mob;
    private final BooleanSupplier enabled;
    private final int horizontalRange;
    private final int verticalRange;

    public LegacyFlyingStrollGoal(
            PathfinderMob mob,
            double speedModifier,
            int interval,
            int horizontalRange,
            int verticalRange,
            BooleanSupplier enabled
    ) {
        super(mob, speedModifier, interval);
        this.mob = mob;
        this.enabled = enabled;
        this.horizontalRange = horizontalRange;
        this.verticalRange = verticalRange;
    }

    @Override
    public boolean canUse() {
        return enabled.getAsBoolean() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return enabled.getAsBoolean() && super.canContinueToUse();
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return new Vec3(
                mob.getX() + (mob.getRandom().nextFloat() * 2.0F - 1.0F) * horizontalRange,
                mob.getY() + (mob.getRandom().nextFloat() * 2.0F - 1.0F) * verticalRange,
                mob.getZ() + (mob.getRandom().nextFloat() * 2.0F - 1.0F) * horizontalRange
        );
    }
}
