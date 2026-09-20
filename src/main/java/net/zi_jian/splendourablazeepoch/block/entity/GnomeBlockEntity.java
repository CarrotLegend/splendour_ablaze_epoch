package net.zi_jian.splendourablazeepoch.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.zi_jian.splendourablazeepoch.registry.ModBlockEntities;

public final class GnomeBlockEntity extends BlockEntity {

    private CompoundTag preservedData =
            new CompoundTag();

    private GnomeBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state
    ) {
        super(
                type,
                pos,
                state
        );
    }

    public static GnomeBlockEntity create(
            BlockPos pos,
            BlockState state
    ) {
        return new GnomeBlockEntity(
                ModBlockEntities.get("gnome"),
                pos,
                state
        );
    }

    @Override
    public void load(
            CompoundTag tag
    ) {
        super.load(tag);

        preservedData =
                new CompoundTag();

        for (String key : tag.getAllKeys()) {
            if ("id".equals(key)
                    || "x".equals(key)
                    || "y".equals(key)
                    || "z".equals(key)) {
                continue;
            }

            Tag value =
                    tag.get(key);

            if (value != null) {
                preservedData.put(
                        key,
                        value.copy()
                );
            }
        }
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {
        for (String key
                : preservedData.getAllKeys()) {

            Tag value =
                    preservedData.get(key);

            if (value != null) {
                tag.put(
                        key,
                        value.copy()
                );
            }
        }

        super.saveAdditional(tag);
    }
}