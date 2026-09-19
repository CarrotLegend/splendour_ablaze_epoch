package net.zi_jian.splendourablazeepoch.block.entity;

import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public final class MigratedBlockEntity extends BlockEntity {
    private CompoundTag preservedData = new CompoundTag();

    private MigratedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static MigratedBlockEntity create(String id, BlockPos pos, BlockState state) {
        return new MigratedBlockEntity(ModBlockEntities.get(id), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        preservedData = tag.copy();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        for (String key : preservedData.getAllKeys()) {
            if (!"id".equals(key) && !"x".equals(key) && !"y".equals(key) && !"z".equals(key)) {
                tag.put(key, preservedData.get(key).copy());
            }
        }
        super.saveAdditional(tag);
    }
}
