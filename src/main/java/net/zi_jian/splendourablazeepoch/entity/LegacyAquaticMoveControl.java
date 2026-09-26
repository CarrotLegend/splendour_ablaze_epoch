package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public final class LegacyAquaticMoveControl extends MoveControl {
    public LegacyAquaticMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
            double dx = wantedX - mob.getX();
            double dy = wantedY - mob.getY();
            double dz = wantedZ - mob.getZ();
            float targetYaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
            float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));

            mob.setYRot(rotlerp(mob.getYRot(), targetYaw, 10.0F));
            mob.yBodyRot = mob.getYRot();
            mob.yHeadRot = mob.getYRot();

            if (mob.isInWater()) {
                float targetPitch = -(float) (Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180.0D / Math.PI));
                targetPitch = Mth.clamp(Mth.wrapDegrees(targetPitch), -85.0F, 85.0F);
                mob.setXRot(rotlerp(mob.getXRot(), targetPitch, 5.0F));

                // PathfinderMob does not have AbstractFish's water travel method. The old
                // controller put a speed-scaled value into the travel input, which water
                // movement scales again, leaving both horizontal and vertical motion tiny.
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                mob.setSpeed(0.0F);
                mob.setZza(0.0F);
                mob.setYya(0.0F);
                if (distance > 1.0E-4D) {
                    Vec3 current = mob.getDeltaMovement();
                    mob.setDeltaMovement(
                            Mth.lerp(0.25D, current.x, dx / distance * speed),
                            Mth.lerp(0.25D, current.y, dy / distance * speed),
                            Mth.lerp(0.25D, current.z, dz / distance * speed)
                    );
                }
            } else {
                mob.setSpeed(speed * 0.05F);
            }
        } else {
            mob.setSpeed(0.0F);
            mob.setYya(0.0F);
            mob.setZza(0.0F);
        }
    }
}
