package net.zi_jian.splendourablazeepoch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zi_jian.splendourablazeepoch.registry.ModItems;

public abstract class LegacyCropBlock extends Block implements BonemealableBlock {
    private static final net.minecraft.tags.TagKey<Block> COMPOST_ACTIVATORS =
            BlockTags.create(new ResourceLocation("farmersdelight", "compost_activators"));
    private static final net.minecraft.tags.TagKey<Block> FORGE_FARMLAND =
            BlockTags.create(new ResourceLocation("forge", "farmland"));

    protected LegacyCropBlock(Properties properties) {
        super(properties.randomTicks());
    }

    protected abstract IntegerProperty ageProperty();
    protected abstract IntegerProperty variantProperty();
    protected abstract int maxAge();
    protected abstract boolean millet();

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState soil = level.getBlockState(pos.below());
        return soil.is(COMPOST_ACTIVATORS) || soil.is(FORGE_FARMLAND) || soil.is(Blocks.FARMLAND);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return canSurvive(state, level, pos) ? super.updateShape(state, direction, neighbor, level, pos, neighborPos)
                : Blocks.AIR.defaultBlockState();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1F && state.getValue(ageProperty()) < maxAge()) {
            grow(level, pos, state);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(ageProperty()) < maxAge();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(ageProperty()) < maxAge()) {
            grow(level, pos, state);
        }
    }

    private void grow(ServerLevel level, BlockPos pos, BlockState state) {
        int next = state.getValue(ageProperty()) + 1;
        level.setBlock(pos, state.setValue(ageProperty(), next).setValue(variantProperty(), next), 3);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int stage = state.getValue(variantProperty());
        int height = millet() ? new int[]{5, 9, 12, 14, 16}[stage] : new int[]{3, 5, 8}[stage];
        return box(0, 0, 0, 16, height, 16);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
                                       boolean willHarvest, FluidState fluid) {
        boolean removed = super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
        if (removed && millet() && state.getValue(ageProperty()) == maxAge() && level instanceof ServerLevel serverLevel) {
            dropMillet(serverLevel, pos);
            if (serverLevel.random.nextFloat() < 0.2F) {
                dropMillet(serverLevel, pos);
            }
        }
        return removed;
    }

    private static void dropMillet(ServerLevel level, BlockPos pos) {
        ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(),
                new ItemStack(ModItems.byId("millet").get()));
        item.setPickUpDelay(10);
        level.addFreshEntity(item);
    }
}
