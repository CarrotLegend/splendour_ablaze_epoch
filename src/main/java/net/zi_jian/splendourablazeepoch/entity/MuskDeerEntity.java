package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModEntities;

public final class MuskDeerEntity extends TopworldAnimalEntity {
    public MuskDeerEntity(EntityType<? extends MuskDeerEntity> type, Level level) {
        super(type, level);
        setMaxUpStep(1.0F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8D));
        goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        goalSelector.addGoal(5, new FloatGoal(this));
    }

    @Nullable
    @Override
    public MuskDeerEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.MUSK_DEER.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModBlocks.CYAN_TWIG.get().asItem()) || stack.is(Items.WHEAT);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}
