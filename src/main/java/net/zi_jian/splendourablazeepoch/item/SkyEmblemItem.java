package net.zi_jian.splendourablazeepoch.item;

import net.zi_jian.splendourablazeepoch.entity.SkyDoorEntity;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class SkyEmblemItem extends Item {

    private static final double OVERWORLD_REQUIRED_Y =
            240.0D;

    private static final double TOPWORLD_REQUIRED_Y =
            190.0D;

    private static final double DOOR_SPAWN_Y_OFFSET =
            10.0D;

    private static final int LEVITATION_DURATION =
            600;

    private static final int LEVITATION_AMPLIFIER =
            1;

    public SkyEmblemItem(
            Properties properties
    ) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        ItemStack stack =
                player.getItemInHand(hand);

        if (!isValidDimension(level)) {
            return InteractionResultHolder.pass(stack);
        }

        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResultHolder.pass(stack);
        }

        double requiredY =
                level.dimension().equals(Level.OVERWORLD)
                        ? OVERWORLD_REQUIRED_Y
                        : TOPWORLD_REQUIRED_Y;

        if (player.getY() <= requiredY) {
            sendHeightWarning(player);

            return InteractionResultHolder.success(stack);
        }

        SkyDoorEntity door =
                ModEntities.SKY_DOOR
                        .get()
                        .create(serverLevel);

        if (door == null) {
            return InteractionResultHolder.fail(stack);
        }

        door.moveTo(
                player.getX(),
                player.getY()
                        + DOOR_SPAWN_Y_OFFSET,
                player.getZ(),
                level.random.nextFloat()
                        * 360.0F,
                0.0F
        );

        if (!serverLevel.addFreshEntity(door)) {
            return InteractionResultHolder.fail(stack);
        }

        player.addEffect(
                new MobEffectInstance(
                        MobEffects.LEVITATION,
                        LEVITATION_DURATION,
                        LEVITATION_AMPLIFIER,
                        false,
                        false
                )
        );

        return InteractionResultHolder.success(stack);
    }

    private static boolean isValidDimension(
            Level level
    ) {
        return level.dimension().equals(Level.OVERWORLD)
                || level.dimension().equals(
                        SkyDoorEntity.TOPWORLD
                );
    }

    private static void sendHeightWarning(
            Player player
    ) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        serverPlayer.connection.send(
                new ClientboundSetTitleTextPacket(
                        Component.translatable(
                                        "message.splendour_ablaze_epoch.sky_emblem.not_high_enough"
                                )
                                .withStyle(
                                        ChatFormatting.GOLD,
                                        ChatFormatting.BOLD
                                )
                )
        );
    }
}
