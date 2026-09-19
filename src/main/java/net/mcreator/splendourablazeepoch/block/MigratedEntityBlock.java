package net.mcreator.splendourablazeepoch.block;

import net.mcreator.splendourablazeepoch.block.entity.MigratedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class MigratedEntityBlock extends BaseEntityBlock {
    private final String registryName;

    public MigratedEntityBlock(String registryName, BlockBehaviour.Properties properties) {
        super(properties);
        this.registryName = registryName;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MigratedBlockEntity.create(registryName, pos, state);
    }
}
