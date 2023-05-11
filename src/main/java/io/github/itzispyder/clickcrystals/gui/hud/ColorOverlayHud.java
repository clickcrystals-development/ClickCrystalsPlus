package io.github.itzispyder.clickcrystals.gui.hud;

import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.modules.modules.rendering.BrightOrange;
import io.github.itzispyder.clickcrystals.modules.modules.rendering.TotemOverlay;
import io.github.itzispyder.clickcrystals.util.DrawableUtils;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import io.github.itzispyder.clickcrystals.util.InventoryUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;

import static io.github.itzispyder.clickcrystals.ClickCrystals.mc;

public class ColorOverlayHud implements HudRenderCallback {

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        final Window win = mc.getWindow();
        final Module totemOverlay = Module.get(TotemOverlay.class);
        final Module brightOrange = Module.get(BrightOrange.class);

        if (totemOverlay.isEnabled() && !HotbarUtils.isTotemed() && InventoryUtils.has(Items.TOTEM_OF_UNDYING)) {
            DrawableUtils.drawBorder(matrices, 0,0,win.getScaledWidth(),win.getScaledHeight(),10,0xFFFF0000);
            matrices.translate(0.0F, 0.0F, -69F);
        }

        if (brightOrange.isEnabled()) {
            this.renderColor(matrices, 0x30BE8100);
        }
    }
    private void renderColor(MatrixStack matrices, int color) {
        final Window win = mc.getWindow();
        DrawableHelper.fill(matrices, 0, 0, win.getWidth(), win.getHeight(), color);
    }
}
