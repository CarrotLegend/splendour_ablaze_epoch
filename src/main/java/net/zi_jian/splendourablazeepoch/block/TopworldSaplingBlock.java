package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public final class TopworldSaplingBlock extends SaplingBlock {

    private final Supplier<? extends Block> extraSoil;

    public TopworldSaplingBlock(
            AbstractTreeGrower grower,
            BlockBehaviour.Properties properties,
            Supplier<? extends Block> extraSoil
    ) {
        super(grower, properties);
        this.extraSoil = extraSoil;
    }

    @Override
    protected boolean mayPlaceOn(
            BlockState state,
            BlockGetter level,
            BlockPos pos
    ) {
        if (state.is(extraSoil.get())) {
            return true;
        }

        return super.mayPlaceOn(
                state,
                level,
                pos
        );
    }
}
