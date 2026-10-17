package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PatJinMaoHouEntity extends TamableAnimal implements GeoEntity {
    private static final String FOLLOW_TAG = "follow";
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    public PatJinMaoHouEntity(EntityType<? extends PatJinMaoHouEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(2.0F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
        targetSelector.addGoal(3, new OwnerHurtTargetGoal(this) {
            @Override
            public boolean canUse() {
                return isFollowing() && super.canUse();
            }
        });
        targetSelector.addGoal(4, new OwnerHurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return isFollowing() && super.canUse();
            }
        });
        goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false) {
            @Override
            public boolean canUse() {
                return isFollowing() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return isFollowing() && super.canContinueToUse();
            }
        });
        goalSelector.addGoal(6, new LeapAtTargetGoal(this, 0.5F) {
            @Override
            public boolean canUse() {
                return isFollowing() && super.canUse();
            }
        });
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        goalSelector.addGoal(8, new FloatGoal(this));
    }

    public boolean isFollowing() {
        return getPersistentData().getBoolean(FOLLOW_TAG);
    }

    public void setFollowing(boolean following) {
        getPersistentData().putBoolean(FOLLOW_TAG, following);
        setOrderedToSit(!following);
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.setBaseValue(following ? 0.35D : 0.0D);
        }
        if (!following) {
            getNavigation().stop();
            setTarget(null);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack mainHand = player.getMainHandItem();

        if (isLegacyFood(mainHand)) {
            if (!level().isClientSide) {
                if (getHealth() <= getMaxHealth() - 10.0F) {
                    mainHand.shrink(1);
                    setHealth(getHealth() + 10.0F);
                } else {
                    setHealth(getMaxHealth());
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (player.isShiftKeyDown() && mainHand.isEmpty()) {
            if (!level().isClientSide) {
                setFollowing(!isFollowing());
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    private static boolean isLegacyFood(ItemStack stack) {
        return stack.is(ModItems.byId("cookedvenison").get())
                || stack.is(Items.COOKED_PORKCHOP)
                || stack.is(Items.COOKED_BEEF)
                || stack.is(Items.COOKED_MUTTON);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return isLegacyFood(stack);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE)
                || source.is(DamageTypes.FALL)
                || source.is(DamageTypes.CACTUS)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, state -> {
            if (!isFollowing()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("6"));
            }
            if (getTarget() != null || isSprinting()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("2"));
            }
            if (state.isMoving()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("1"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("0"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }
}
