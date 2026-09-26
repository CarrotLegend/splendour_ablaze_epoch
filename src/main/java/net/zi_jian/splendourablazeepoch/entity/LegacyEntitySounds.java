package net.zi_jian.splendourablazeepoch.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.zi_jian.splendourablazeepoch.registry.ModSounds;

final class LegacyEntitySounds {
    private LegacyEntitySounds() {
    }

    @Nullable
    static SoundEvent ambient(LivingEntity entity) {
        return switch (id(entity)) {
            case "magpie" -> ModSounds.MAGPIE_1.get();
            case "muskdeer" -> ModSounds.MUSK_DEER_2.get();
            case "peacock" -> ModSounds.PEACOCK_2.get();
            case "pheasant" -> SoundEvents.CHICKEN_STEP;
            case "rustedancestors", "rustedwoman", "rustedchild", "ac" -> SoundEvents.HUSK_STEP;
            case "rusthound" -> SoundEvents.WOLF_GROWL;
            case "rustrelics", "rustrelicsb", "rustrelicss" -> SoundEvents.SKELETON_AMBIENT;
            case "waterbuffalo" -> SoundEvents.COW_AMBIENT;
            case "alivepictograph" -> sound("minecraft:block.enchantment_table.use");
            case "darkworm" -> ModSounds.WORM_1.get();
            case "pagewraith", "pagegnat" -> ModSounds.PAGE_1.get();
            default -> null;
        };
    }

    @Nullable
    static SoundEvent hurt(LivingEntity entity) {
        return switch (id(entity)) {
            case "magpie" -> ModSounds.MAGPIE_3.get();
            case "muskdeer" -> ModSounds.MUSK_DEER_1.get();
            case "peacock" -> ModSounds.PEACOCK_3.get();
            case "pheasant" -> SoundEvents.CHICKEN_HURT;
            case "rustedancestors", "rustedwoman", "rustedchild" -> SoundEvents.HUSK_HURT;
            case "rusthound" -> SoundEvents.WOLF_HURT;
            case "rustrelics", "rustrelicsb", "rustrelicss" -> SoundEvents.SKELETON_HURT;
            case "waterbuffalo" -> SoundEvents.COW_HURT;
            case "alivepictograph" -> sound("minecraft:ui.stonecutter.take_result");
            case "terracottawarriorsguard", "terracottawarriors" -> sound("minecraft:block.decorated_pot.shatter");
            case "castinscribedautomaton" -> sound("minecraft:entity.iron_golem.damage");
            case "darkworm" -> ModSounds.WORM_2.get();
            case "pagewraith", "pagegnat" -> ModSounds.PAGE_2.get();
            case "croaker", "koifish", "messenger", "raccoondog", "ac", "girlghost", "goldenhairhou",
                    "rustedchef", "cauldronbeast", "terracottageneral", "flyarrowhead", "skyadministrator",
                    "firearmtigerguard" -> SoundEvents.GENERIC_HURT;
            default -> null;
        };
    }

    @Nullable
    static SoundEvent death(LivingEntity entity) {
        return switch (id(entity)) {
            case "magpie" -> ModSounds.MAGPIE_2.get();
            case "muskdeer" -> SoundEvents.GENERIC_DEATH;
            case "peacock" -> ModSounds.PEACOCK_3.get();
            case "pheasant" -> SoundEvents.CHICKEN_DEATH;
            case "rustedancestors", "rustedwoman", "rustedchild" -> SoundEvents.HUSK_DEATH;
            case "rusthound" -> SoundEvents.WOLF_DEATH;
            case "rustrelics", "rustrelicsb", "rustrelicss" -> SoundEvents.SKELETON_DEATH;
            case "waterbuffalo" -> SoundEvents.COW_DEATH;
            case "alivepictograph" -> sound("minecraft:entity.generic.explode");
            case "terracottawarriorsguard", "terracottawarriors" -> sound("minecraft:block.decorated_pot.shatter");
            case "castinscribedautomaton" -> sound("minecraft:entity.iron_golem.death");
            case "pagewraith", "pagegnat" -> ModSounds.PAGE_3.get();
            case "croaker", "koifish", "messenger", "raccoondog", "ac", "girlghost", "goldenhairhou",
                    "rustedchef", "cauldronbeast", "terracottageneral", "flyarrowhead", "skyadministrator",
                    "darkworm", "firearmtigerguard" -> SoundEvents.GENERIC_DEATH;
            default -> null;
        };
    }

    @Nullable
    static SoundEvent step(LivingEntity entity) {
        return switch (id(entity)) {
            case "peacock" -> ModSounds.MAGPIE_1.get();
            case "rustedancestors", "rustedwoman", "rustedchild" -> SoundEvents.HUSK_STEP;
            case "rustrelics", "rustrelicsb", "rustrelicss" -> SoundEvents.SKELETON_STEP;
            case "waterbuffalo" -> SoundEvents.COW_STEP;
            case "terracottawarriorsguard", "terracottawarriors" -> sound("minecraft:block.decorated_pot.step");
            default -> null;
        };
    }

    @Nullable
    private static SoundEvent sound(String id) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(id));
    }

    private static String id(LivingEntity entity) {
        var key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return key == null ? "" : key.getPath();
    }
}
