package net.zi_jian.splendourablazeepoch.block;

import java.util.Comparator;
import java.util.PriorityQueue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

@Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FeatheryFoamBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final PriorityQueue<DelayedStuck> DELAYED =
            new PriorityQueue<>(Comparator.comparingLong(DelayedStuck::dueTick));

    public FeatheryFoamBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(WATERLOGGED,
                context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighbor, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level instanceof ServerLevel serverLevel) {
            entity.fallDistance = 0.0F;
            DELAYED.add(new DelayedStuck(serverLevel.getServer().getTickCount() + 2L, entity));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!player.getMainHandItem().is(Items.BUCKET)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            ItemStack bucket = player.getMainHandItem();
            ItemStack foamBucket = new ItemStack(ModItems.byId("featheryfoambucket").get());
            bucket.shrink(1);
            if (bucket.isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, foamBucket);
            } else if (!player.getInventory().add(foamBucket)) {
                player.drop(foamBucket, false);
            }
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        long tick = event.getServer().getTickCount();
        while (!DELAYED.isEmpty() && DELAYED.peek().dueTick() <= tick) {
            Entity entity = DELAYED.remove().entity();
            if (!entity.level().isClientSide) {
                entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25D, 0.05D, 0.25D));
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        DELAYED.clear();
    }

    private record DelayedStuck(long dueTick, Entity entity) {
    }
}
