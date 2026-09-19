package net.zi_jian.splendourablazeepoch.entity;

import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class SkyDoorEntity extends MigratedTopworldMob {

    public static final ResourceKey<Level> TOPWORLD =
            ResourceKey.create(
                    Registries.DIMENSION,
                    new ResourceLocation(
                            SplendourAblazeEpochMod.MOD_ID,
                            "topworld"
                    )
            );

    private static final int MAX_LIFETIME_TICKS = 200;

    private static final long TELEPORT_COOLDOWN_TICKS = 40L;

    private static final String PORTAL_AGE_NBT =
            "SkyDoorAge";

    private static final String PLAYER_COOLDOWN_NBT =
            "SplendourAblazeSkyDoorCooldown";

    private int portalAge = 0;

    public SkyDoorEntity(
            EntityType<? extends MigratedTopworldMob> type,
            Level level
    ) {
        super(
                type,
                level,
                "splendour_ablaze_door"
        );

        setNoGravity(true);
        setInvulnerable(true);
        noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();

        setNoGravity(true);
        noPhysics = true;
        setDeltaMovement(Vec3.ZERO);

        if (level().isClientSide) {
            return;
        }

        portalAge++;

        if (portalAge >= MAX_LIFETIME_TICKS) {
            discard();
        }
    }

    @Override
    public void checkDespawn() {
    }

    @Override
    public boolean removeWhenFarAway(
            double distanceToClosestPlayer
    ) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(
            DamageSource source
    ) {
        return true;
    }

    @Override
    public void registerControllers(
            AnimatableManager.ControllerRegistrar controllers
    ) {
        controllers.add(
                new AnimationController<>(
                        this,
                        "movement",
                        4,
                        state -> state.setAndContinue(
                                state.isMoving()
                                        ? RawAnimation.begin()
                                                .thenPlay("2")
                                        : RawAnimation.begin()
                                                .thenLoop("0")
                        )
                )
        );
    }

    @Override
    public void playerTouch(
            Player player
    ) {
        super.playerTouch(player);

        if (level().isClientSide) {
            return;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ServerLevel sourceLevel =
                serverPlayer.serverLevel();

        long currentGameTime =
                sourceLevel.getGameTime();

        long cooldownUntil =
                serverPlayer
                        .getPersistentData()
                        .getLong(PLAYER_COOLDOWN_NBT);

        if (currentGameTime < cooldownUntil) {
            return;
        }

        if (sourceLevel.dimension().equals(Level.OVERWORLD)) {
            teleportToTopworld(
                    serverPlayer,
                    sourceLevel,
                    currentGameTime
            );

            return;
        }

        if (sourceLevel.dimension().equals(TOPWORLD)) {
            teleportToOverworld(
                    serverPlayer,
                    sourceLevel,
                    currentGameTime
            );
        }
    }

    private void teleportToTopworld(
            ServerPlayer player,
            ServerLevel sourceLevel,
            long currentGameTime
    ) {
        ServerLevel targetLevel =
                sourceLevel
                        .getServer()
                        .getLevel(TOPWORLD);

        if (targetLevel == null) {
            return;
        }

        double targetX = player.getX();
        double targetZ = player.getZ();

        setPlayerPortalCooldown(
                player,
                currentGameTime
        );

        player.removeEffect(
                MobEffects.LEVITATION
        );

        player.teleportTo(
                targetLevel,
                targetX,
                200.0D,
                targetZ,
                player.getYRot(),
                player.getXRot()
        );

        player.addEffect(
                new MobEffectInstance(
                        MobEffects.SLOW_FALLING,
                        600,
                        1,
                        false,
                        false
                )
        );
    }

    private void teleportToOverworld(
            ServerPlayer player,
            ServerLevel sourceLevel,
            long currentGameTime
    ) {
        ServerLevel targetLevel =
                sourceLevel
                        .getServer()
                        .getLevel(Level.OVERWORLD);

        if (targetLevel == null) {
            return;
        }

        BlockPos destination;

        if (player.getRespawnDimension().equals(Level.OVERWORLD)
                && player.getRespawnPosition() != null) {

            destination =
                    player.getRespawnPosition();

        } else {

            destination =
                    targetLevel.getSharedSpawnPos();
        }

        setPlayerPortalCooldown(
                player,
                currentGameTime
        );

        player.removeEffect(
                MobEffects.LEVITATION
        );

        player.teleportTo(
                targetLevel,
                destination.getX() + 0.5D,
                destination.getY(),
                destination.getZ() + 0.5D,
                player.getYRot(),
                player.getXRot()
        );

    }

    private static void setPlayerPortalCooldown(
            ServerPlayer player,
            long currentGameTime
    ) {
        player.getPersistentData().putLong(
                PLAYER_COOLDOWN_NBT,
                currentGameTime
                        + TELEPORT_COOLDOWN_TICKS
        );
    }

    @Override
    public void addAdditionalSaveData(
            CompoundTag tag
    ) {
        super.addAdditionalSaveData(tag);

        tag.putInt(
                PORTAL_AGE_NBT,
                portalAge
        );
    }

    @Override
    public void readAdditionalSaveData(
            CompoundTag tag
    ) {
        super.readAdditionalSaveData(tag);

        if (tag.contains(PORTAL_AGE_NBT)) {
            portalAge =
                    Math.max(
                            0,
                            tag.getInt(PORTAL_AGE_NBT)
                    );
        }
    }
}