package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class FirearmTigerGuardEntity extends TopworldMonsterEntity {
    public FirearmTigerGuardEntity(EntityType<? extends FirearmTigerGuardEntity> type, Level level) {
        super(type, level);
        xpReward = 8;
        setMaxUpStep(0.6F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 0.7D, false));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new RandomStrollGoal(this, 0.5D));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }
}
