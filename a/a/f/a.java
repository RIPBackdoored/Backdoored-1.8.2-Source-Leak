package a.a.f;

import net.minecraft.client.gui.*;
import java.io.*;
import java.util.*;
import com.backdoored.*;
import org.lwjgl.opengl.*;
import com.backdoored.hacks.*;
import a.a.f.a.*;

public class a extends GuiScreen
{
    public static int gh;
    public static int gi;
    
    public a() {
        super();
        this.allowUserInput = true;
    }
    
    public void initGui() {
        super.initGui();
        this.allowUserInput = true;
    }
    
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
    }
    
    public void drawScreen(final int gh, final int gi, final float n) {
        a.gh = gh;
        a.gi = gi;
        for (final a.a.f.a.a a : b.b()) {
            if (a.fj) {
                a.a(this, gh, gi);
            }
        }
        this.a(gh, gi);
        super.drawScreen(gh, gi, n);
    }
    
    private void a(final int n, final int n2) {
        final a.a.f.a.a a = b.a(n, n2);
        if (a != null) {
            final BaseHack a2 = b.a(a);
            if (a2 != null) {
                Globals.mc.renderEngine.bindTexture(b.resourceLocation);
                GL11.glPushAttrib(1048575);
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                GL11.glColor4f(255.0f, 255.0f, 255.0f, 1.0f);
                final int n3 = this.fontRenderer.getStringWidth(a2.description) + 1;
                final int n4 = this.fontRenderer.FONT_HEIGHT + 1;
                this.drawTexturedModalRect(n + 5, n2 + 5, n3, n4, n3, n4);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
                this.fontRenderer.drawString(a2.description, n + 6, n2 + 6, 0);
            }
        }
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        for (final a.a.f.a.a a : b.b()) {
            if (!a.fj) {
                continue;
            }
            if (n >= a.fe && n <= a.fe + a.fg && n2 >= a.ff && n2 <= a.ff + a.fh && n3 == 0) {
                for (final c c : c.d()) {
                    if (a.fi.equals(c.fi)) {
                        c.a().setEnabled(!c.a().getEnabled());
                        return;
                    }
                }
                a.fc = !a.fc;
                return;
            }
            if (n >= a.fe && n <= a.fe + a.fg && n2 >= a.ff && n2 <= a.ff + a.fh && n3 == 1) {
                a.fd = !a.fd;
                return;
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        a.gh = 0;
        a.gi = 0;
    }
}
