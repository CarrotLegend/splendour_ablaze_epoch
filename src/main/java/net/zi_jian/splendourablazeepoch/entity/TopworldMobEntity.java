package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class TopworldMobEntity
        extends PathfinderMob
        implements GeoEntity {

    private final AnimatableInstanceCache animationCache =
            GeckoLibUtil.createInstanceCache(this);

    protected TopworldMobEntity(
            EntityType<? extends TopworldMobEntity> type,
            Level level
    ) {
        super(
                type,
                level
        );
    }

    protected static AttributeSupplier.Builder createBaseAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(
                        Attributes.MOVEMENT_SPEED,
                        0.3D
                )
                .add(
                        Attributes.MAX_HEALTH,
                        10.0D
                )
                .add(
                        Attributes.ARMOR,
                        0.0D
                )
                .add(
                        Attributes.ATTACK_DAMAGE,
                        0.0D
                )
                .add(
                        Attributes.FOLLOW_RANGE,
                        16.0D
                )
                .add(
                        Attributes.KNOCKBACK_RESISTANCE,
                        0.3D
                );
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(
                this
        );
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
                        state ->
                                state.setAndContinue(
                                        RawAnimation.begin()
                                                .thenLoop("0")
                                )
                )
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}