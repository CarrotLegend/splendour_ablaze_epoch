package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

public final class CrisisEffectClient {
    public static IClientMobEffectExtensions create() {
        // 原版主动隐藏危机在背包与 HUD 中的显示。
        return new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen,
                                               GuiGraphics graphics, int x, int y, int blitOffset) {
                return false;
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance instance) {
                return false;
            }
        };
    }

    private CrisisEffectClient() {
    }
}
