package a.a.f.a;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import com.backdoored.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class a
{
    private static final ResourceLocation resourceLocation;
    public boolean fc;
    public boolean fd;
    public int fe;
    public int ff;
    public int fg;
    public int fh;
    public String fi;
    public boolean fj;
    private float[] fk;
    public String fl;
    public static ArrayList<a> fm;
    
    public a(final int fe, final int ff, final int fg, final int fh, final String fi, final boolean fj, final float[] fk) {
        super();
        this.fc = false;
        this.fd = false;
        this.fl = "FFFFFF";
        this.fe = fe;
        this.ff = ff;
        this.fg = fg;
        this.fh = fh;
        this.fi = fi;
        this.fj = fj;
        this.fk = fk;
        a.fm.add(this);
    }
    
    public void a(final GuiScreen guiScreen, final int n, final int n2) {
        final int n3 = 1;
        Globals.mc.renderEngine.bindTexture(a.resourceLocation);
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(this.fk[0], this.fk[1], this.fk[2], this.fk[3]);
        final List listFormattedStringToWidth = guiScreen.mc.fontRenderer.listFormattedStringToWidth(this.fi, this.fg - (n3 + 1));
        final int fh = listFormattedStringToWidth.size() * guiScreen.mc.fontRenderer.FONT_HEIGHT + 15;
        if (fh > guiScreen.mc.fontRenderer.FONT_HEIGHT + 15) {
            this.fh = fh;
        }
        guiScreen.drawTexturedModalRect(this.fe, this.ff, 0, 0, this.fg, this.fh);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        guiScreen.drawTexturedModalRect(this.fe + n3, this.ff + n3, 0, 0, this.fg - n3 * 2, this.fh - n3 * 2);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        guiScreen.mc.fontRenderer.drawSplitString(this.fi, this.fe + (n3 + 1) + (this.fg - (n3 + 1) - guiScreen.mc.fontRenderer.getStringWidth((String)listFormattedStringToWidth.get(0))) / 2, this.ff + this.fh / 2 - guiScreen.mc.fontRenderer.FONT_HEIGHT * listFormattedStringToWidth.size() / 2, this.fg - (n3 + 1), Integer.parseInt(this.fl, 16));
        this.c(guiScreen, n, n2);
    }
    
    public void c(final GuiScreen guiScreen, final int n, final int n2) {
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/white.png");
        a.fm = new ArrayList<a>();
    }
}
