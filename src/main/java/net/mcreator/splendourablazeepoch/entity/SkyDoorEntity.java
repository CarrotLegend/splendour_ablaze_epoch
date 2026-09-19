package net.mcreator.splendourablazeepoch.entity;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

public final class SkyDoorEntity extends MigratedTopworldMob {
    public static final ResourceKey<Level> TOPWORLD = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "topworld"));

    public SkyDoorEntity(EntityType<? extends MigratedTopworldMob> type, Level level) {
        super(type, level, "splendour_ablaze_door");
        setNoGravity(true);
        noPhysics = true;
    }

    @Override
    public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source) {
        return true;
    }

    @Override
    public void checkDespawn() {
        if (++noActionTime >= 20) discard();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4,
                state -> state.setAndContinue(state.isMoving()
                        ? RawAnimation.begin().thenPlay("2")
                        : RawAnimation.begin().thenLoop("0"))));
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!(player instanceof ServerPlayer serverPlayer) || level().isClientSide) return;
        if (level().dimension() == Level.OVERWORLD) {
            ServerLevel target = serverPlayer.server.getLevel(TOPWORLD);
            if (target == null) return;
            double x = getX();
            double z = getZ();
            serverPlayer.removeAllEffects();
            serverPlayer.teleportTo(target, x, 200.0D, z, serverPlayer.getYRot(), serverPlayer.getXRot());
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 1, false, false));
            discard();
        } else if (level().dimension() == TOPWORLD) {
            ServerLevel target = serverPlayer.server.getLevel(Level.OVERWORLD);
            if (target == null) return;
            BlockPos destination = serverPlayer.getRespawnDimension() == Level.OVERWORLD && serverPlayer.getRespawnPosition() != null
                    ? serverPlayer.getRespawnPosition() : target.getSharedSpawnPos();
            serverPlayer.removeAllEffects();
            serverPlayer.teleportTo(target, destination.getX() + 0.5D, destination.getY(), destination.getZ() + 0.5D,
                    serverPlayer.getYRot(), serverPlayer.getXRot());
            discard();
        }
    }
}
