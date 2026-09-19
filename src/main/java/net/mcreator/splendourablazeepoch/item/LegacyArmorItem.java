package net.mcreator.splendourablazeepoch.item;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public final class LegacyArmorItem extends ArmorItem {
    private final String textureName;

    public LegacyArmorItem(String family, Type type, Supplier<Ingredient> repair) {
        super(material(family, repair), type, new Item.Properties());
        this.textureName = family.equals("celes_terral") ? "celesterral" : family;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return SplendourAblazeEpochMod.MOD_ID + ":textures/models/armor/" + textureName
                + (slot == EquipmentSlot.LEGS ? "_layer_2.png" : "_layer_1.png");
    }

    private static ArmorMaterial material(String family, Supplier<Ingredient> repair) {
        ArmorValues v = switch (family) {
            case "celes_terral" -> new ArmorValues(45, new int[]{8,20,24,8}, 36, 3, .2F);
            case "bronze" -> new ArmorValues(23, new int[]{2,6,7,2}, 15, 2, .1F);
            case "magnetic_steel" -> new ArmorValues(30, new int[]{3,7,8,3}, 12, 2, .1F);
            case "damascene_steel" -> new ArmorValues(30, new int[]{4,9,11,4}, 18, 1.5F, .2F);
            case "cupronickel" -> new ArmorValues(30, new int[]{4,10,12,4}, 18, 0, 0);
            case "mold_tin" -> new ArmorValues(16, new int[]{3,7,9,3}, 18, 0, 0);
            default -> throw new IllegalArgumentException("Unknown legacy armor family " + family);
        };
        return new ArmorMaterial() {
            @Override public int getDurabilityForType(Type type) { return new int[]{13,15,16,11}[type.getSlot().getIndex()] * v.multiplier; }
            @Override public int getDefenseForType(Type type) { return v.defense[type.getSlot().getIndex()]; }
            @Override public int getEnchantmentValue() { return v.enchantment; }
            @Override public SoundEvent getEquipSound() { return SoundEvents.EMPTY; }
            @Override public Ingredient getRepairIngredient() { return repair.get(); }
            @Override public String getName() { return family + "_armor"; }
            @Override public float getToughness() { return v.toughness; }
            @Override public float getKnockbackResistance() { return v.knockback; }
        };
    }

    private record ArmorValues(int multiplier, int[] defense, int enchantment, float toughness, float knockback) {}
}
