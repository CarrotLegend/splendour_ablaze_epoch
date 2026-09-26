package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.Level;

public final class TerracottaGeneralEntity extends TopworldMonsterEntity {
    public TerracottaGeneralEntity(EntityType<? extends TerracottaGeneralEntity> type, Level level) {
        super(type, level);
        xpReward = 10;
        setMaxUpStep(0.6F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.ARMOR, 14.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }
}
