package net.zi_jian.splendourablazeepoch.effect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.client.CrisisEffectClient;

public final class CrisisEffect extends MobEffect {
    private static final ResourceLocation BIGTYPE_ID = new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, "bigtype");

    public CrisisEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFFFF);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.addAttributeModifiers(entity, attributes, amplifier);
        if (entity.level() instanceof ServerLevel serverLevel) {
            // 原版仅在服务端召唤；实体尚未迁移时保留其余效果且安全跳过。
            if (ForgeRegistries.ENTITY_TYPES.containsKey(BIGTYPE_ID)) {
                EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(BIGTYPE_ID);
                if (type != null) {
                    Entity summoned = type.spawn(
                            serverLevel,
                            BlockPos.containing(entity.getX(), entity.getY() + 15.0D, entity.getZ()),
                            net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED
                    );
                    if (summoned != null) {
                        summoned.setYRot(serverLevel.getRandom().nextFloat() * 360.0F);
                    }
                }
            }
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25D, 0.05D, 0.25D));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(CrisisEffectClient.create());
    }
}
