package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class MilletCropBlock extends LegacyCropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 4);

    public MilletCropBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(BLOCKSTATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, BLOCKSTATE);
    }

    @Override protected IntegerProperty ageProperty() { return AGE; }
    @Override protected IntegerProperty variantProperty() { return BLOCKSTATE; }
    @Override protected int maxAge() { return 4; }
    @Override protected boolean millet() { return true; }
}
