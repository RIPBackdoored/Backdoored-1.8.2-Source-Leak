package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import a.a.e.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

@b.a(name = "Inventory Preview", description = "Shows you a preview of whats in your inv", category = CategoriesInit.RENDER)
public class InventoryPreview extends BaseHack
{
    private static final ResourceLocation resourceLocation;
    private static final int vl = 5;
    private static final int vm = 1;
    private static final int vn = 18;
    private Setting<Integer> x;
    private Setting<Integer> y;
    
    public InventoryPreview() {
        super();
        this.x = new IntegerSetting("x", this, 2, 0, InventoryPreview.mc.displayWidth + 100);
        this.y = new IntegerSetting("y", this, 2, 0, InventoryPreview.mc.displayHeight + 100);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final int intValue = this.x.getValInt();
        final int intValue2 = this.y.getValInt();
        final NonNullList mainInventory = InventoryPreview.mc.player.inventory.mainInventory;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0f, 0.0f, 700.0f);
        InventoryPreview.mc.getTextureManager().bindTexture(InventoryPreview.resourceLocation);
        RenderHelper.disableStandardItemLighting();
        this.a(intValue, intValue2, 9, 3, 1973019);
        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 9; i < mainInventory.size(); ++i) {
            final ItemStack itemStack = (ItemStack)mainInventory.get(i);
            final int n = intValue + 6 + i % 9 * 18;
            final int n2 = intValue2 + 6 + i / 9 * 18 - 18;
            if (!itemStack.isEmpty()) {
                InventoryPreview.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2);
                InventoryPreview.mc.getRenderItem().renderItemOverlays(c.fontRenderer, itemStack, n, n2);
            }
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void a(final int n, final int n2, final int n3, final int n4, final int n5) {
        InventoryPreview.mc.getTextureManager().bindTexture(InventoryPreview.resourceLocation);
        GlStateManager.color(((n5 & 0xFF0000) >> 16) / 255.0f, ((n5 & 0xFF00) >> 8) / 255.0f, (n5 & 0xFF) / 255.0f);
        RenderHelper.disableStandardItemLighting();
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * n3, n2 + 5 + 18 * n4, 25.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * n3, n2, 25.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(n, n2 + 5 + 18 * n4, 0.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        for (int i = 0; i < n4; ++i) {
            Gui.drawModalRectWithCustomSizedTexture(n, n2 + 5 + 18 * i, 0.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * n3, n2 + 5 + 18 * i, 25.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            for (int j = 0; j < n3; ++j) {
                if (i == 0) {
                    Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * j, n2, 6.0f, 0.0f, 18, 5, 256.0f, 256.0f);
                    Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * j, n2 + 5 + 18 * n4, 6.0f, 25.0f, 18, 5, 256.0f, 256.0f);
                }
                Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * j, n2 + 5 + 18 * i, 6.0f, 6.0f, 18, 18, 256.0f, 256.0f);
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/inv_slot.png");
    }
}
