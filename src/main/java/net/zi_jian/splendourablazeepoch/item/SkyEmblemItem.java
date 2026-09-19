package net.zi_jian.splendourablazeepoch.item;

import net.zi_jian.splendourablazeepoch.entity.SkyDoorEntity;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class SkyEmblemItem extends Item {

    private static final double OVERWORLD_REQUIRED_Y = 240.0D;
    private static final double TOPWORLD_REQUIRED_Y = 190.0D;
    private static final double DOOR_SPAWN_Y_OFFSET = 10.0D;

    public SkyEmblemItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        ItemStack stack = player.getItemInHand(hand);

        boolean validDimension =
                level.dimension() == Level.OVERWORLD
                        || level.dimension() == SkyDoorEntity.TOPWORLD;

        if (!validDimension) {
            return InteractionResultHolder.pass(stack);
        }

        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResultHolder.pass(stack);
        }

        double requiredY =
                level.dimension() == Level.OVERWORLD
                        ? OVERWORLD_REQUIRED_Y
                        : TOPWORLD_REQUIRED_Y;

        if (player.getY() <= requiredY) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(
                        new ClientboundSetTitleTextPacket(
                                Component.literal("还......还不够高......")
                                        .withStyle(
                                                ChatFormatting.GOLD,
                                                ChatFormatting.BOLD
                                        )
                        )
                );
            }

            return InteractionResultHolder.success(stack);
        }

        BlockPos spawnPos = BlockPos.containing(
                player.getX(),
                player.getY() + DOOR_SPAWN_Y_OFFSET,
                player.getZ()
        );

        SkyDoorEntity door =
                ModEntities.SKY_DOOR.get().spawn(
                        serverLevel,
                        spawnPos,
                        MobSpawnType.MOB_SUMMONED
                );

        if (door == null) {
            return InteractionResultHolder.fail(stack);
        }

        door.setYRot(
                level.random.nextFloat() * 360.0F
        );

        player.addEffect(
                new MobEffectInstance(
                        MobEffects.LEVITATION,
                        600,
                        1,
                        false,
                        false
                )
        );

        return InteractionResultHolder.success(stack);
    }
}