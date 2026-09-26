package net.zi_jian.splendourablazeepoch.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

final class LegacyEntityAnimations {
    private LegacyEntityAnimations() {
    }

    static PlayState movement(LivingEntity entity, AnimationState<?> state) {
        String id = id(entity);
        String animation = switch (id) {
            case "croaker", "koifish" -> "1";
            case "magpie" -> magpie(entity, state);
            case "muskdeer" -> entity.isSprinting() ? "run" : state.isMoving() ? "walk" : "idel";
            case "peacock" -> !entity.onGround() ? "2" : state.isMoving() ? "3" : "1";
            case "pheasant" -> state.isMoving() ? "walk" : "idel";
            case "raccoondog" -> state.isMoving() ? "1" : "0";
            case "rustedancestors", "rustedwoman", "rustedchild" -> state.isMoving() ? "1" : "0";
            case "rusthound" -> state.isMoving() ? "1" : "2";
            case "rustrelics", "rustrelicsb", "rustrelicss" -> relic(entity, state);
            case "waterbuffalo" -> state.isMoving() ? "2" : "1";
            case "ac" -> state.isMoving() ? "1" : "4";
            default -> "0";
        };
        return state.setAndContinue(RawAnimation.begin().thenLoop(animation));
    }

    private static String magpie(LivingEntity entity, AnimationState<?> state) {
        boolean moving = state.isMoving() || entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D;
        if (!entity.onGround()) {
            return "1";
        }
        return moving ? "2" : "0";
    }

    private static String relic(LivingEntity entity, AnimationState<?> state) {
        if (!state.isMoving()) {
            return "3";
        }
        return entity instanceof net.minecraft.world.entity.Mob mob && mob.getTarget() != null ? "2" : "1";
    }

    private static String id(LivingEntity entity) {
        var key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return key == null ? "" : key.getPath();
    }
}
