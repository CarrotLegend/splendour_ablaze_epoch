package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class CauldronBeastEntity extends TopworldMonsterEntity {
    public CauldronBeastEntity(EntityType<? extends CauldronBeastEntity> type, Level level) {
        super(type, level);
        xpReward = 1;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PathfinderMob.class, false, false));
        goalSelector.addGoal(4, new LegacyMeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(5, new LeapAtTargetGoal(this, 0.5F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
}
