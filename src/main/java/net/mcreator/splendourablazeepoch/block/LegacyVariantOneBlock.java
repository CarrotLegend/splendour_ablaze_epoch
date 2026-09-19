package net.mcreator.splendourablazeepoch.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class LegacyVariantOneBlock extends Block {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
    public LegacyVariantOneBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(BLOCKSTATE, 0));
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(BLOCKSTATE); }
}
