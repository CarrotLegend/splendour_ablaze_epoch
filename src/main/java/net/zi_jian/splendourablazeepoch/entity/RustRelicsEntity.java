package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public final class RustRelicsEntity extends TopworldPathfinderEntity {
    private boolean pendingSpawnVariant;

    public RustRelicsEntity(EntityType<? extends RustRelicsEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(0.6F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        goalSelector.addGoal(3, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(4, new HurtByTargetGoal(this));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide && pendingSpawnVariant) {
            pendingSpawnVariant = false;
            if (trySpawnVariant()) {
                return;
            }
        }
        super.aiStep();
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
        pendingSpawnVariant = true;
        return result;
    }

    private boolean trySpawnVariant() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        Mob replacement;
        if (getRandom().nextInt(10) == 0) {
            replacement = ModEntities.RUST_RELICS_BOW.get().create(serverLevel);
        } else if (getRandom().nextInt(10) == 1) {
            replacement = ModEntities.RUST_RELICS_SWORD.get().create(serverLevel);
        } else {
            return false;
        }

        if (replacement == null) {
            return false;
        }
        replacement.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        if (!serverLevel.addFreshEntity(replacement)) {
            return false;
        }
        discard();
        return true;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getRandom().nextInt(10) == 4) {
            spawnAtLocation(Items.BONE);
        }
        if (getRandom().nextDouble() < 0.1D) {
            spawnAtLocation(ModItems.byId("staranise").get());
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}
