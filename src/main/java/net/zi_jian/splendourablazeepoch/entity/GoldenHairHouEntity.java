package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.LegacyExtraEntities;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public final class GoldenHairHouEntity extends TopworldPathfinderEntity {
    public GoldenHairHouEntity(EntityType<? extends GoldenHairHouEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new LegacyMeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.5F));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(6, new FloatGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack food = player.getMainHandItem();

        if (getHealth() < 20.0F && isTamingFood(food)) {
            if (level().isClientSide) {
                return InteractionResult.SUCCESS;
            }

            PatJinMaoHouEntity tamed = LegacyExtraEntities.PAT_JIN_MAO_HOU.get().create(level());
            if (tamed == null) {
                return InteractionResult.FAIL;
            }

            tamed.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
            tamed.tame(player);
            tamed.setFollowing(true);
            tamed.setPersistenceRequired();

            if (!level().addFreshEntity(tamed)) {
                return InteractionResult.FAIL;
            }

            food.shrink(1);
            level().playSound(
                    null,
                    blockPosition(),
                    SoundEvents.NOTE_BLOCK_BELL.value(),
                    SoundSource.NEUTRAL,
                    1.0F,
                    1.0F
            );

            if (player instanceof ServerPlayer serverPlayer) {
                grantTaisuiSubmission(serverPlayer);
            }

            discard();
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    private static boolean isTamingFood(ItemStack stack) {
        return stack.is(ModItems.byId("cookedvenison").get())
                || stack.is(Items.COOKED_PORKCHOP)
                || stack.is(Items.COOKED_BEEF)
                || stack.is(Items.COOKED_MUTTON);
    }

    private static void grantTaisuiSubmission(ServerPlayer player) {
        Advancement advancement = player.server.getAdvancements().getAdvancement(
                new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "taisuis_submission")
        );
        if (advancement == null) {
            return;
        }

        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, criterion);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }
}
