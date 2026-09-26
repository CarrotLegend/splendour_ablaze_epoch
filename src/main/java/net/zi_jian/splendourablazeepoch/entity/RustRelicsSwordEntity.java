package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public final class RustRelicsSwordEntity extends TopworldMonsterEntity {
    public RustRelicsSwordEntity(EntityType<? extends RustRelicsSwordEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(0.6F);
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomStrollGoal(this, 0.8D));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        goalSelector.addGoal(3, new LegacyMeleeAttackGoal(this, 1.2D, false));
        targetSelector.addGoal(4, new HurtByTargetGoal(this));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getRandom().nextInt(10) == 4) {
            spawnAtLocation(Items.BONE);
        }
        if (getRandom().nextDouble() < 0.1D) {
            spawnAtLocation(ModItems.byId("staranise").get());
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}
