package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MigratedTopworldMob extends PathfinderMob implements GeoEntity {

    private static final String VISUAL_ID_NBT = "MigratedVisualId";

    private final AnimatableInstanceCache animationCache =
            GeckoLibUtil.createInstanceCache(this);

    private String visualId;

    public MigratedTopworldMob(
            EntityType<? extends MigratedTopworldMob> type,
            Level level,
            String visualId
    ) {
        super(type, level);
        this.visualId = visualId;

    }

    public String visualId() {
        return visualId;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        if (visualId != null && !visualId.isEmpty()) {
            tag.putString(VISUAL_ID_NBT, visualId);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains(VISUAL_ID_NBT)) {
            String savedVisualId = tag.getString(VISUAL_ID_NBT);

            if (!savedVisualId.isEmpty()) {
                visualId = savedVisualId;
            }
        }
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
                                RawAnimation.begin().thenLoop("0")
                        )
                )
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
