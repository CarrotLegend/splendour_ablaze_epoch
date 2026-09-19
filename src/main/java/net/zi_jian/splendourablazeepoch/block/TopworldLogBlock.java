package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class TopworldLogBlock extends RotatedPillarBlock {

    private final Supplier<? extends Block> strippedBlock;

    public TopworldLogBlock(
            BlockBehaviour.Properties properties,
            Supplier<? extends Block> strippedBlock
    ) {
        super(properties);
        this.strippedBlock = strippedBlock;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(
            BlockState state,
            UseOnContext context,
            ToolAction toolAction,
            boolean simulate
    ) {
        if (toolAction != ToolActions.AXE_STRIP) {
            return super.getToolModifiedState(
                    state,
                    context,
                    toolAction,
                    simulate
            );
        }

        BlockState stripped =
                strippedBlock.get()
                        .defaultBlockState();

        if (
                state.hasProperty(AXIS)
                        && stripped.hasProperty(AXIS)
        ) {
            stripped = stripped.setValue(
                    AXIS,
                    state.getValue(AXIS)
            );
        }

        return stripped;
    }
}
