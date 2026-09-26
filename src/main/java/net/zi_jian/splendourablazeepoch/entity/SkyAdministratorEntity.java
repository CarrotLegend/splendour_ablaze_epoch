package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class SkyAdministratorEntity extends TopworldMonsterEntity {
    public SkyAdministratorEntity(EntityType<? extends SkyAdministratorEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(0.6F);
        moveControl = new FlyingMoveControl(this, 10, true);
        setNoGravity(true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PathfinderMob.class, false, false));
        goalSelector.addGoal(2, new LegacyFlyingChaseGoal(this, 1.0D));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new LegacyFlyingRandomStrollGoal(this, 0.8D, 20, 16.0D, 16.0D));
        goalSelector.addGoal(5, new LeapAtTargetGoal(this, 0.5F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        setNoGravity(true);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.FLYING_SPEED, 0.3D);
    }
}
