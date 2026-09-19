package net.mcreator.splendourablazeepoch.item;

import net.mcreator.splendourablazeepoch.entity.SkyDoorEntity;
import net.mcreator.splendourablazeepoch.registry.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
    public SkyEmblemItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        if (!level.isClientSide && (level.dimension() == Level.OVERWORLD || level.dimension() == SkyDoorEntity.TOPWORLD)) {
            double requiredY = level.dimension() == Level.OVERWORLD ? 240.0D : 190.0D;
            if (player.getY() > requiredY) {
                SkyDoorEntity door = ModEntities.SKY_DOOR.get().spawn((net.minecraft.server.level.ServerLevel) level,
                        BlockPos.containing(player.getX(), player.getY() + 10.0D, player.getZ()), MobSpawnType.MOB_SUMMONED);
                if (door != null) door.setYRot(level.random.nextFloat() * 360.0F);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 600, 1, false, false));
            } else if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket(
                        Component.literal("还......还不够高......").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)));
            }
        }
        return super.use(level, player, hand);
    }
}
