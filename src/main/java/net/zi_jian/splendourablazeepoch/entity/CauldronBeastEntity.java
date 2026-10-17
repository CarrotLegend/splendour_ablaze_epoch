package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;

public final class CauldronBeastEntity extends TopworldMonsterEntity {
    private static final String EXPERIENCE_TAG = "ex";

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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            MobSpawnType reason,
            @Nullable SpawnGroupData spawnData,
            @Nullable CompoundTag dataTag
    ) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        getPersistentData().putDouble(EXPERIENCE_TAG, 5.0D);

        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(5.0D);
        }

        AttributeInstance reach = getAttribute(ForgeMod.ENTITY_REACH.get());
        if (reach != null) {
            reach.setBaseValue(2.0D);
        }

        return result;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hurt = super.doHurtTarget(target);
        if (!hurt || level().isClientSide) {
            return hurt;
        }

        getPersistentData().putDouble(
                EXPERIENCE_TAG,
                getPersistentData().getDouble(EXPERIENCE_TAG) + 5.0D
        );

        if (target instanceof Player player) {
            player.giveExperiencePoints(-5);
        }

        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (level().isClientSide) {
            return;
        }

        double experience = getPersistentData().getDouble(EXPERIENCE_TAG);
        if (experience <= 60.0D) {
            AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
            if (attack != null) {
                attack.setBaseValue(7.0D + experience / 5.0D);
            }
        }
    }

    @Override
    public void die(DamageSource source) {
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
            int experience = (int) getPersistentData().getDouble(EXPERIENCE_TAG);
            if (experience > 0) {
                serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, getX(), getY(), getZ(), experience));
            }
        }
        super.die(source);
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
