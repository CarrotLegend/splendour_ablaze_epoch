package net.zi_jian.splendourablazeepoch.enchantment;

import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

public final class InorganicDestructionEnchantment extends Enchantment {
    private static final Set<Item> VANILLA_ITEMS = Set.of(
            Items.WOODEN_SWORD,
            Items.WOODEN_PICKAXE,
            Items.STONE_SWORD,
            Items.STONE_PICKAXE,
            Items.GOLDEN_SWORD,
            Items.GOLDEN_PICKAXE,
            Items.IRON_SWORD,
            Items.IRON_PICKAXE,
            Items.DIAMOND_SWORD,
            Items.DIAMOND_PICKAXE,
            Items.NETHERITE_SWORD,
            Items.NETHERITE_PICKAXE,
            Items.BOOK
    );

    private static final Set<String> MOD_ITEMS = Set.of(
            "celes_terral_pickaxe",
            "celes_terral_sword",
            "pewter_pickaxe",
            "pewter_sword",
            "bronze_pickaxe",
            "bronze_sword",
            "magnetic_steel_pickaxe",
            "magnetic_steel_sword"
    );

    private static final EnchantmentCategory CATEGORY = EnchantmentCategory.create(
            "splendour_ablaze_epoch_inorganicdestruction",
            item -> {
                if (VANILLA_ITEMS.contains(item)) {
                    return true;
                }
                ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
                return key != null
                        && SplendourAblazeEpochMod.MOD_ID.equals(key.getNamespace())
                        && MOD_ITEMS.contains(key.getPath());
            }
    );

    public InorganicDestructionEnchantment() {
        super(Rarity.COMMON, CATEGORY, EquipmentSlot.values());
    }

    @Override
    public int getMinCost(int level) {
        return 1 + level * 10;
    }

    @Override
    public int getMaxCost(int level) {
        return 6 + level * 10;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other)
                && other != Enchantments.SMITE
                && other != Enchantments.BANE_OF_ARTHROPODS;
    }
}
