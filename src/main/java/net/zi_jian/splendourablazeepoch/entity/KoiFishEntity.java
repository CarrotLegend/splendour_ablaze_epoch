package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public final class KoiFishEntity extends TopworldPathfinderEntity {
    public KoiFishEntity(EntityType<? extends KoiFishEntity> type, Level level) {
        super(type, level);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        moveControl = new LegacyAquaticMoveControl(this);
        setMaxUpStep(0.2F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new PanicGoal(this, 1.2D));
        goalSelector.addGoal(3, new RandomSwimmingGoal(this, 0.7D, 40));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!level().isClientSide
                && level().dimension().equals(SkyDoorEntity.TOPWORLD)
                && getTarget() == null
                && LegacyEntityBehavior.isMovePulse(this)
                && getRandom().nextDouble() < 0.4D) {
            LegacyEntityBehavior.moveRandom2D(this, 10.0D, 1.0D);
        }

        if (!level().isClientSide
                && !level().getBlockState(blockPosition()).is(Blocks.WATER)
                && getRandom().nextInt(20) == 14) {
            setDeltaMovement(
                    getRandom().nextDouble() * 0.4D - 0.2D,
                    0.3D,
                    getRandom().nextDouble() * 0.4D - 0.2D
            );
            hurt(damageSources().inWall(), 1.0F);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && !level().isClientSide) {
            LegacyEntityBehavior.fleeAfterHurt(this);
        }
        return hurt;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!held.is(Items.WATER_BUCKET)) {
            return super.mobInteract(player, hand);
        }

        playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);

        if (!level().isClientSide) {
            ItemStack filled = new ItemStack(ModItems.KOI_FISH_BUCKET.get());
            player.setItemInHand(hand, ItemUtils.createFilledResult(held, player, filled));
            discard();
        }

        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(ForgeMod.SWIM_SPEED.get(), 0.2D);
    }
}
