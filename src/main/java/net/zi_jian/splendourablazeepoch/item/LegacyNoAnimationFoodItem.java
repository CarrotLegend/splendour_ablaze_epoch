package net.zi_jian.splendourablazeepoch.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public final class LegacyNoAnimationFoodItem extends Item {
    public LegacyNoAnimationFoodItem(Properties properties) {
        super(properties);
    }

    @Override public UseAnim getUseAnimation(ItemStack stack) { return UseAnim.NONE; }
    @Override public int getUseDuration(ItemStack stack) { return 0; }
}
