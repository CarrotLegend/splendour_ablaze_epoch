package net.zi_jian.splendourablazeepoch.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.menu.ForgingFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class ForgingFurnaceScreen
        extends AbstractContainerScreen<ForgingFurnaceMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(
                    SplendourAblazeEpochMod.MOD_ID,
                    "textures/screens/forgingfurnace_gui.png"
            );

    private static final int BAR_X = 91;
    private static final int BAR_Y = 39;
    private static final int BAR_WIDTH = 25;
    private static final int BAR_HEIGHT = 8;

    public ForgingFurnaceScreen(
            ForgingFurnaceMenu menu,
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
        RenderSystem.setShaderColor(
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );

        graphics.blit(
                TEXTURE,
                leftPos,
                topPos,
                0,
                0,
                imageWidth,
                imageHeight,
                imageWidth,
                imageHeight
        );

        graphics.fill(
                leftPos + BAR_X,
                topPos + BAR_Y,
                leftPos + BAR_X + BAR_WIDTH,
                topPos + BAR_Y + BAR_HEIGHT,
                0xFF392F27
        );

        int progress =
                menu.getScaledProgress(
                        BAR_WIDTH
                );

        if (progress > 0) {
            graphics.fill(
                    leftPos + BAR_X,
                    topPos + BAR_Y,
                    leftPos + BAR_X + progress,
                    topPos + BAR_Y + BAR_HEIGHT,
                    0xFFFFA52B
            );
        }
    }

    @Override
    protected void renderLabels(
            GuiGraphics graphics,
            int mouseX,
            int mouseY
    ) {
    }
}