package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.menu.PrintTableMenu;

public final class PrintTableScreen
        extends AbstractContainerScreen<PrintTableMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "textures/screens/printtablegui.png"
            );

    public PrintTableScreen(
            PrintTableMenu menu,
            Inventory inventory,
            Component title
    ) {
        super(
                menu,
                inventory,
                title
        );

        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = 72;
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        renderBackground(graphics);

        super.render(
                graphics,
                mouseX,
                mouseY,
                partialTick
        );

        renderTooltip(
                graphics,
                mouseX,
                mouseY
        );
    }

    @Override
    protected void renderBg(
            GuiGraphics graphics,
            float partialTick,
            int mouseX,
            int mouseY
    ) {
        graphics.blit(
                TEXTURE,
                leftPos,
                topPos,
                0,
                0,
                176,
                166,
                176,
                166
        );
    }
}