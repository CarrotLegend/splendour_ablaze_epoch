package net.zi_jian.splendourablazeepoch.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class MessengerEntity extends TopworldMonsterEntity {
    private static final int EXECUTION_DELAY = 20;

    private int executionTicks = -1;
    @Nullable
    private UUID executionTarget;

    public MessengerEntity(EntityType<? extends MessengerEntity> type, Level level) {
        super(type, level);
        moveControl = new FlyingMoveControl(this, 10, true);
        setNoGravity(true);
        setMaxUpStep(0.6F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        goalSelector.addGoal(2, new LegacyMeleeAttackGoal(this, 1.2D, true));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(5, new LegacyFlyingStrollGoal(
                this,
                0.8D,
                20,
                16,
                16,
                () -> true
        ));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        setNoGravity(true);

        if (level().isClientSide) {
            return;
        }

        if (executionTicks >= 0) {
            tickExecution();
            return;
        }

        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        AABB contactBox = new AABB(
                getX() - 1.0D,
                getY() - 1.0D,
                getZ() - 1.0D,
                getX() + 1.0D,
                getY() + 1.0D,
                getZ() + 1.0D
        );

        if (!target.getBoundingBox().intersects(contactBox)) {
            return;
        }

        executionTarget = target.getUUID();
        executionTicks = EXECUTION_DELAY;
        target.startRiding(this);
        setDeltaMovement(new Vec3(0.0D, 0.2D, 0.0D));

        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.FIREWORK,
                    getX(),
                    getY() + 0.5D,
                    getZ(),
                    8,
                    0.25D,
                    0.25D,
                    0.25D,
                    0.01D
            );
        }
    }

    private void tickExecution() {
        if (executionTicks > 0) {
            executionTicks--;
            return;
        }

        if (level() instanceof ServerLevel serverLevel && executionTarget != null) {
            Entity entity = serverLevel.getEntity(executionTarget);
            if (entity instanceof LivingEntity living && living.isAlive()) {
                living.kill();
            }
        }

        executionTicks = -1;
        executionTarget = null;
        hurt(damageSources().generic(), 100.0F);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.DROWN)
                || source.is(DamageTypes.LIGHTNING_BOLT)
                || source.is(DamageTypes.EXPLOSION)
                || source.is(DamageTypes.DRAGON_BREATH)
                || source.is(DamageTypes.WITHER)
                || source.is(DamageTypes.WITHER_SKULL)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (getRandom().nextDouble() < 0.5D) {
            spawnAtLocation(ModItems.byId("blackpowder").get());
        }

        if (!level().isClientSide) {
            spawnRedFirework();
        }
    }

    private void spawnRedFirework() {
        ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET);
        CompoundTag fireworks = rocket.getOrCreateTagElement("Fireworks");
        fireworks.putByte("Flight", (byte) 0);

        CompoundTag explosion = new CompoundTag();
        explosion.putByte("Type", (byte) 0);
        explosion.putBoolean("Flicker", true);
        explosion.putBoolean("Trail", false);
        explosion.putIntArray("Colors", new int[]{0xFF0000});
        explosion.putIntArray("FadeColors", new int[0]);

        ListTag explosions = new ListTag();
        explosions.add(explosion);
        fireworks.put("Explosions", explosions);

        FireworkRocketEntity firework = new FireworkRocketEntity(
                level(),
                getX(),
                getY(),
                getZ(),
                rocket
        );

        CompoundTag saved = new CompoundTag();
        firework.saveWithoutId(saved);
        saved.putInt("LifeTime", 2);
        firework.load(saved);
        level().addFreshEntity(firework);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ExecutionTicks", executionTicks);
        if (executionTarget != null) {
            tag.putUUID("ExecutionTarget", executionTarget);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        executionTicks = tag.getInt("ExecutionTicks");
        executionTarget = tag.hasUUID("ExecutionTarget") ? tag.getUUID("ExecutionTarget") : null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, state ->
                state.setAndContinue(RawAnimation.begin().thenLoop(executionTicks >= 0 ? "1" : "0"))));
    }

    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.FLYING_SPEED, 0.3D);
    }
}
