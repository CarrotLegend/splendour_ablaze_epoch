package net.zi_jian.splendourablazeepoch.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.menu.MillenniaFragranceMenu;

public final class MillenniaFragranceScreen extends AbstractContainerScreen<MillenniaFragranceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            SplendourAblazeEpochMod.MOD_ID, "textures/screens/millennia_fragrance.png");

    private static final String[] HINTS = {
            "tooltip_yi_ba_jiao_shan_li_de_xi_hu_ke_neng_you_dan_ta_yi_jing_bian_cheng_dui_bai_gu_liao",
            "tooltip_er_hua_jiao_zai_wen_nuan_de_ping_yuan_li_nong_hu_jia_li_ying_gai_hui_you",
            "tooltip_san_xiao_hui_xiang_qu_bei_fang_de_han_leng_di_dai_li_zhao_zhao_kan_ni",
            "tooltip_empty",
            "tooltip_wu_gui_pi_shui_xiang_ze_guo_de_chang_jian_xiang_liao_xian_zai_ye_shi",
            "tooltip_lu_xiang_xie_bu_yuan_chu_wu_tong_shu_lin_li_de_ren_jia_li_huo_xu_you_bei",
            "tooltip_qi_rou_dou_kou_wo_de_lin_ju_da_gai_you_dan_ta_ye_bei_du_xiu_dai_zou_liao",
            "tooltip_ba_bai_zhi_zai_na_ge_sheng_chan_yu_de_xiao_zhen_li_wo_jian_guo_qu_na_li_shi_shi"
    };
    private static final int[][] HINT_POSITIONS = {
            {79, 7}, {52, 16}, {43, 43}, {52, 69},
            {79, 79}, {106, 69}, {115, 43}, {106, 16}
    };

    public MillenniaFragranceScreen(MillenniaFragranceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 190;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
        for (int slot = 0; slot < HINTS.length; slot++) {
            int x = leftPos + HINT_POSITIONS[slot][0];
            int y = topPos + HINT_POSITIONS[slot][1];
            if (menu.getSlot(slot).getItem().isEmpty()
                    && mouseX > x - 4 && mouseX < x + 20
                    && mouseY > y - 5 && mouseY < y + 19) {
                graphics.renderTooltip(font,
                        Component.translatable("gui.splendour_ablaze_epoch.millennia_fragrance."
                                + HINTS[slot]), mouseX, mouseY);
                break;
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, 176, 190, 176, 190);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // The original screen draws no default title or inventory label.
    }
}
