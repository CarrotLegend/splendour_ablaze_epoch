package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class PageGnatEntity extends TopworldMonsterEntity {
    public PageGnatEntity(EntityType<? extends PageGnatEntity> type, Level level) {
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
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        goalSelector.addGoal(3, new LegacyFlyingRandomStrollGoal(this, 1.0D, 20, 16.0D, 16.0D));
        targetSelector.addGoal(4, new HurtByTargetGoal(this));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(6, new FloatGoal(this));
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
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
                .add(Attributes.FLYING_SPEED, 0.7D);
    }
}
