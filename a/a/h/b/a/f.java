package a.a.h.b.a;

import a.a.h.a.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import a.a.k.f.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class f extends b
{
    private static final Color yz;
    
    public f() {
        super("Inventory Preview");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        final int bco = 18 * 9;
        final int bcp = 54;
        this.e().bco = bco;
        this.e().bcp = bcp;
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        final NonNullList mainInventory = f.mc.player.inventory.mainInventory;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0f, 0.0f, 700.0f);
        RenderHelper.disableStandardItemLighting();
        if (!(f.mc.currentScreen instanceof a.a.h.b.b)) {
            c.a(this.d().bco, this.d().bcp, this.d().bco + this.e().bco, this.d().bcp + this.e().bcp, f.yz.getRGB());
        }
        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 9; i < mainInventory.size(); ++i) {
            final ItemStack itemStack = (ItemStack)mainInventory.get(i);
            final int n4 = this.d().bco + i % 9 * 18;
            final int n5 = this.d().bcp + i / 9 * 18 - 18;
            if (!itemStack.isEmpty()) {
                f.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n4, n5);
                f.mc.getRenderItem().renderItemOverlays(f.mc.fontRenderer, itemStack, n4, n5);
            }
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    static {
        yz = new Color(64, 64, 64, 127);
    }
}
