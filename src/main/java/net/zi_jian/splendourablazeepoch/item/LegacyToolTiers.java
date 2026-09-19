package net.zi_jian.splendourablazeepoch.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public final class LegacyToolTiers {
    private record Values(int uses, float speed, float damage, int level, int enchantment) {}

    public static Tier create(String family, String kind, Supplier<Ingredient> repair) {
        Values value = values(family, kind);
        return new Tier() {
            @Override public int getUses() { return value.uses; }
            @Override public float getSpeed() { return value.speed; }
            @Override public float getAttackDamageBonus() { return value.damage; }
            @Override public int getLevel() { return value.level; }
            @Override public int getEnchantmentValue() { return value.enchantment; }
            @Override public Ingredient getRepairIngredient() { return repair.get(); }
        };
    }

    private static Values values(String family, String kind) {
        return switch (family) {
            case "celes_terral" -> new Values(1741, 14, kind.equals("axe") ? 14 : kind.equals("sword") ? 10 : 8, 4, 56);
            case "bronze" -> new Values(720, 8, switch (kind) { case "axe" -> 7; case "sword", "shovel" -> 4; default -> 3; }, 2, 22);
            case "pewter" -> new Values(kind.equals("pickaxe") ? 360 : 540, 11,
                    switch (kind) { case "axe" -> 8; case "sword", "pickaxe", "hoe" -> 3; default -> 2; }, 2, 24);
            case "magnetic_steel" -> new Values(960, 8, switch (kind) { case "axe" -> 9; case "sword" -> 5; default -> 4; }, 3, 18);
            case "damascene_steel" -> new Values(1020, 9, switch (kind) { case "axe" -> 10; case "sword" -> 6; default -> 5; }, 3,
                    kind.equals("sword") ? 20 : 28);
            case "cupronickel" -> new Values(660, 9, switch (kind) { case "axe" -> 16; case "sword" -> 8; case "shovel" -> 7; case "hoe" -> 1; default -> 6; }, 3, 28);
            case "mold_tin" -> new Values(330, 9, switch (kind) { case "axe" -> 16; case "sword" -> 8; case "shovel" -> 7; case "hoe" -> 1; default -> 6; },
                    kind.equals("shovel") ? 3 : 2, 28);
            default -> throw new IllegalArgumentException("Unknown legacy tool family " + family);
        };
    }

    public static float attackModifier(String family, String kind) {
        if (kind.equals("sword")) return 3;
        if (kind.equals("hoe")) return 0;
        return 1;
    }

    public static float speedModifier(String family, String kind) {
        return switch (family) {
            case "celes_terral" -> switch (kind) { case "pickaxe" -> -2.6F; case "axe" -> -2.8F; case "sword" -> -1F; case "shovel" -> -2.5F; default -> -2.4F; };
            case "bronze", "pewter", "magnetic_steel" -> switch (kind) { case "pickaxe" -> -3F; case "axe" -> -3.2F; case "sword" -> family.equals("magnetic_steel") ? -2.2F : -2F; case "shovel" -> -2.8F; default -> -2.6F; };
            case "damascene_steel" -> switch (kind) { case "pickaxe" -> -3.2F; case "axe" -> -3.3F; case "sword" -> -2.4F; case "shovel" -> -3F; default -> -2.8F; };
            case "cupronickel", "mold_tin" -> kind.equals("sword") ? -1F : kind.equals("hoe") ? 2F : -2F;
            default -> throw new IllegalArgumentException("Unknown legacy tool family " + family);
        };
    }

    private LegacyToolTiers() {}
}
