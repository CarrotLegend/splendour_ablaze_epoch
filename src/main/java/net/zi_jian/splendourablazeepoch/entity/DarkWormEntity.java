package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public final class DarkWormEntity extends TopworldMonsterEntity implements RangedAttackMob {
    public DarkWormEntity(EntityType<? extends DarkWormEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(0.6F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (level().isClientSide) {
            return;
        }
        Arrow arrow = new Arrow(level(), this);
        double dx = target.getX() - getX();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        double dy = target.getY(0.3333333333333333D) - arrow.getY() + horizontal * 0.2D;
        arrow.shoot(dx, dy, dz, 1.6F, 12.0F);
        arrow.setBaseDamage(5.0D);
        level().addFreshEntity(arrow);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }
}
