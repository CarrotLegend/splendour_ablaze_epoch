package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class AwakenedAncestorEntity extends TopworldMonsterEntity {
    private static final String CUSTOM_NAME_KEY = "entity.splendour_ablaze_epoch.ac.custom_name";

    public AwakenedAncestorEntity(EntityType<? extends AwakenedAncestorEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(0.6F);
        setCustomName(Component.translatable(CUSTOM_NAME_KEY));
        setCustomNameVisible(true);
        setPersistenceRequired();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        Component name = getCustomName();
        if (name != null
                && name.getContents() instanceof LiteralContents literal
                && "阿苍".equals(literal.text())
                && name.getSiblings().isEmpty()) {
            setCustomName(Component.translatable(CUSTOM_NAME_KEY));
        }
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, true));
        goalSelector.addGoal(2, new LegacyMeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof ThrownPotion
                || source.getDirectEntity() instanceof AreaEffectCloud
                || source.is(DamageTypes.DROWN)
                || source.is(DamageTypes.DRAGON_BREATH)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }
}
