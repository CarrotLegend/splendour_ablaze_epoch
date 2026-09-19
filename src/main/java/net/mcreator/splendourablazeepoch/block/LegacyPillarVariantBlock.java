package net.mcreator.splendourablazeepoch.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class LegacyPillarVariantBlock extends RotatedPillarBlock {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 2);

    public LegacyPillarVariantBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AXIS, net.minecraft.core.Direction.Axis.Y).setValue(BLOCKSTATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCKSTATE);
    }
}
