package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.zi_jian.splendourablazeepoch.registry.ModBlocks;
import net.zi_jian.splendourablazeepoch.registry.ModItems;
import net.zi_jian.splendourablazeepoch.registry.SericultureRegistry;
import org.jetbrains.annotations.Nullable;

public final class SericultureHiveBlock extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    public enum Stage {
        EMPTY,
        LEAVES,
        FULL
    }

    private final Stage stage;

    public SericultureHiveBlock(Stage stage) {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.WOOD)
                .strength(1.0F, 10.0F)
                .randomTicks()
                .lightLevel(state -> 15));
        this.stage = stage;
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(AGE, 0);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (stage != Stage.LEAVES) {
            return;
        }

        int age = state.getValue(AGE);
        if (age >= 5) {
            level.setBlock(pos, SericultureRegistry.SERICULTURE_HIVE_FULL.get().defaultBlockState().setValue(AGE, 0), 3);
        } else {
            level.setBlock(pos, state.setValue(AGE, age + 1), 3);
        }
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (stage == Stage.LEAVES) {
            return InteractionResult.PASS;
        }

        if (stage == Stage.EMPTY) {
            ItemStack held = player.getItemInHand(hand);
            if (held.is(ModBlocks.get("mulberry_leaves").asItem())) {
                if (!level.isClientSide) {
                    held.shrink(1);
                    level.setBlock(
                            pos,
                            SericultureRegistry.SERICULTURE_HIVE_LEAVES.get().defaultBlockState().setValue(AGE, 0),
                            3
                    );
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            int cocoonCount = 1 + serverLevel.random.nextInt(3);
            for (int i = 0; i < cocoonCount; i++) {
                drop(serverLevel, pos, new ItemStack(ModItems.byId("silkwormcocoon").get()));
            }
            if (serverLevel.random.nextDouble() < 0.2D) {
                drop(serverLevel, pos, new ItemStack(ModItems.byId("silkworm").get()));
            }
            level.setBlock(
                    pos,
                    SericultureRegistry.SERICULTURE_HIVE.get().defaultBlockState().setValue(AGE, 0),
                    3
            );
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static void drop(ServerLevel level, BlockPos pos, ItemStack stack) {
        ItemEntity entity = new ItemEntity(
                level,
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                stack
        );
        entity.setPickUpDelay(10);
        level.addFreshEntity(entity);
    }
}
