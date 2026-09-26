package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class PageWraithEntity extends TopworldMonsterEntity {
    public PageWraithEntity(EntityType<? extends PageWraithEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(3.0F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        targetSelector.addGoal(4, new HurtByTargetGoal(this));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(6, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }
}
