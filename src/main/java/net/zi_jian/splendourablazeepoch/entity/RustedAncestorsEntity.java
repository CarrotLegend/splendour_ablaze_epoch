package net.zi_jian.splendourablazeepoch.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class RustedAncestorsEntity extends TopworldPathfinderEntity {
    private static final ResourceKey<Biome> SPECLUME_FOREST = ResourceKey.create(
            Registries.BIOME,
            new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "speclumeforest")
    );
    private static final String NAME_TAG = "name";
    private static final String YEARNING_ACTIVE_TAG = "YearningActive";
    private static final String YEARNING_TICKS_TAG = "YearningTicks";
    private static final String YEARNING_PLAYER_TAG = "YearningPlayer";

    private boolean yearningActive;
    private int yearningTicks;
    @Nullable
    private UUID yearningPlayer;

    public RustedAncestorsEntity(EntityType<? extends RustedAncestorsEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(0.6F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, true, true));
        goalSelector.addGoal(4, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(5, new HurtByTargetGoal(this));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (level().isClientSide) {
            return super.mobInteract(player, hand);
        }

        if (yearningActive
                || !player.getMainHandItem().is(ModItems.byId("previousletter").get())
                || !level().getBiome(blockPosition()).is(SPECLUME_FOREST)
                || "zabing".equals(getPersistentData().getString(NAME_TAG))) {
            return super.mobInteract(player, hand);
        }

        if (getRandom().nextDouble() >= 0.2D) {
            getPersistentData().putString(NAME_TAG, "zabing");
            return InteractionResult.SUCCESS;
        }

        if (!player.getAbilities().instabuild) {
            player.getMainHandItem().shrink(1);
        }

        getPersistentData().putString(NAME_TAG, "A cang");
        yearningActive = true;
        yearningTicks = 0;
        yearningPlayer = player.getUUID();
        setTarget(null);
        getNavigation().stop();
        zeroCombatAttributes();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (level().isClientSide || !yearningActive) {
            return;
        }

        getNavigation().stop();
        setTarget(null);
        setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
        zeroCombatAttributes();
        yearningTicks++;

        if (yearningTicks == 30) {
            sendYearningMessage("…… 红…… 红儿？");
        } else if (yearningTicks == 50) {
            sendYearningMessage("对不起......我没能......为你摘下那朵花......");
        } else if (yearningTicks >= 90) {
            sendYearningMessage("能......带我回家吗......");
            awaken();
        }
    }

    private void zeroCombatAttributes() {
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.setBaseValue(0.0D);
        }
        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(0.0D);
        }
    }

    private void sendYearningMessage(String message) {
        if (!(level() instanceof ServerLevel serverLevel) || yearningPlayer == null) {
            return;
        }
        ServerPlayer player = serverLevel.getServer().getPlayerList().getPlayer(yearningPlayer);
        if (player != null) {
            player.displayClientMessage(Component.literal(message), true);
        }
    }

    private void awaken() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        AwakenedAncestorEntity awakened = ModEntities.AWAKENED_ANCESTOR.get().create(serverLevel);
        if (awakened != null) {
            awakened.moveTo(getX(), getY(), getZ(), getRandom().nextFloat() * 360.0F, 0.0F);
            serverLevel.addFreshEntity(awakened);
        }
        discard();
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
        ServerLevel serverLevel = level.getLevel();

        if (getRandom().nextDouble() < 0.2D) {
            RustedWomanEntity replacement = ModEntities.RUSTED_WOMAN.get().create(serverLevel);
            if (replacement != null) {
                replacement.moveTo(getX(), getY(), getZ(), getRandom().nextFloat() * 360.0F, 0.0F);
                serverLevel.addFreshEntity(replacement);
                discard();
            }
            return result;
        }

        if (getRandom().nextDouble() < 0.1D) {
            RustedChildEntity replacement = ModEntities.RUSTED_CHILD.get().create(serverLevel);
            if (replacement != null) {
                replacement.moveTo(getX(), getY(), getZ(), getRandom().nextFloat() * 360.0F, 0.0F);
                serverLevel.addFreshEntity(replacement);
                discard();
            }
        }

        return result;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getRandom().nextDouble() < 0.1D) {
            spawnAtLocation(ModItems.byId("nutmeg").get());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(YEARNING_ACTIVE_TAG, yearningActive);
        tag.putInt(YEARNING_TICKS_TAG, yearningTicks);
        if (yearningPlayer != null) {
            tag.putUUID(YEARNING_PLAYER_TAG, yearningPlayer);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        yearningActive = tag.getBoolean(YEARNING_ACTIVE_TAG);
        yearningTicks = tag.getInt(YEARNING_TICKS_TAG);
        yearningPlayer = tag.hasUUID(YEARNING_PLAYER_TAG) ? tag.getUUID(YEARNING_PLAYER_TAG) : null;
        if (yearningActive) {
            zeroCombatAttributes();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, state -> {
            if (yearningActive) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("2"));
            }
            return LegacyEntityAnimations.movement(this, state);
        }));
    }

    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }
}
