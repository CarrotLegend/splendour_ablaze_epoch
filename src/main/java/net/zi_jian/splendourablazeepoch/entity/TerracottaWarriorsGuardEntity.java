package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class TerracottaWarriorsGuardEntity extends TopworldMonsterEntity {
    public TerracottaWarriorsGuardEntity(EntityType<? extends TerracottaWarriorsGuardEntity> type, Level level) {
        super(type, level);
        xpReward = 0;
        setMaxUpStep(1.0F);
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        goalSelector.addGoal(2, new LegacyMeleeAttackGoal(this, 0.9D, false));
        goalSelector.addGoal(3, new RandomStrollGoal(this, 0.5D));
        targetSelector.addGoal(4, new HurtByTargetGoal(this));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "intentionally_empty"));
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.decorated_pot.step"));
        if (sound != null) {
            playSound(sound, 0.15F, 1.0F);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.decorated_pot.shatter"));
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.decorated_pot.shatter"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof AbstractArrow
                || source.getDirectEntity() instanceof ThrownPotion
                || source.getDirectEntity() instanceof AreaEffectCloud
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.DROWN)
                || source.is(DamageTypes.LIGHTNING_BOLT)
                || source.is(DamageTypes.DRAGON_BREATH)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        spawnAtLocation(Items.BRICK);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide
                && level().dimension().location().equals(new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "topworld"))
                && getTarget() == null
                && level().getGameTime() % 121L == 0L
                && getRandom().nextDouble() < 0.4D) {
            getNavigation().moveTo(
                    getX() + Mth.nextInt(getRandom(), -10, 10),
                    getY(),
                    getZ() + Mth.nextInt(getRandom(), -10, 10),
                    1.0D
            );
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("idel"))));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}
