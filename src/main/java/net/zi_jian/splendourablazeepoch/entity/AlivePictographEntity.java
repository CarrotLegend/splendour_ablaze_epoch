package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class AlivePictographEntity extends TopworldMonsterEntity {
    public AlivePictographEntity(EntityType<? extends AlivePictographEntity> type, Level level) {
        super(type, level);
        xpReward = 1;
        setMaxUpStep(0.6F);
        moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomStrollGoal(this, 0.8D, 20) {
            @Nullable
            @Override
            protected Vec3 getPosition() {
                RandomSource random = AlivePictographEntity.this.getRandom();
                return new Vec3(
                        AlivePictographEntity.this.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F,
                        AlivePictographEntity.this.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F,
                        AlivePictographEntity.this.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F
                );
            }
        });
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        goalSelector.addGoal(3, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(4, new HurtByTargetGoal(this));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        setNoGravity(true);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.enchantment_table.use"));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "ui.stonecutter.take_result"));
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.explode"));
    }

    @Override
    public void die(DamageSource source) {
        if (!level().isClientSide && !isRemoved()) {
            dropLegacyDeathItem();
        }
        super.die(source);
    }

    private void dropLegacyDeathItem() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        RandomSource random = getRandom();
        String itemId = null;
        if (random.nextInt(2) == 0) {
            if (random.nextInt(6) == 0) {
                itemId = "typefirst";
            } else if (random.nextInt(6) == 1) {
                itemId = "typesecond";
            } else if (random.nextInt(6) == 2) {
                itemId = "typethird";
            }
        } else if (random.nextInt(2) == 0) {
            itemId = "rawlead";
        }
        if (itemId == null) {
            return;
        }
        ItemEntity item = new ItemEntity(serverLevel, getX(), getY(), getZ(), new ItemStack(ModItems.byId(itemId).get()));
        item.setPickUpDelay(10);
        serverLevel.addFreshEntity(item);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, state -> {
            if (isDeadOrDying()) {
                return state.setAndContinue(RawAnimation.begin().thenPlay("2"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("1"));
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FLYING_SPEED, 0.3D);
    }
}
