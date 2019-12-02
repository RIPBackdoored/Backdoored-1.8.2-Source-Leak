package a.a.j.a;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import com.backdoored.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class b
{
    private static final ResourceLocation resourceLocation;
    private static final int bas = 100;
    private static final int bat = 45;
    private static final int bau = 8;
    public static final String bav = "FFFFFF";
    private static ArrayList<b> baw;
    public static int bax;
    public String bay;
    public GuiButton guiButton;
    public boolean bba;
    public int bbb;
    public int bbc;
    public int bbd;
    
    public b(final GuiScreen guiScreen, final String bay, final boolean bba) {
        super();
        this.bay = bay;
        this.bba = bba;
        this.bbd = b.bax;
        this.bbb = guiScreen.width / 2 - 50;
        this.bbc = guiScreen.height / 4 + 24 * (this.bbd - 1) - 16;
        if (bba) {
            this.guiButton = new GuiButton(this.bbd, this.bbb + 50 + 22, this.bbc - 5, 22, 12, " ");
        }
        else {
            this.guiButton = new GuiButton(this.bbd, this.bbb + 50, this.bbc - 5, 22, 16, "");
        }
        c.bbe.a().add(this.guiButton);
        b.baw.add(this);
        ++b.bax;
    }
    
    public void a(final GuiScreen guiScreen) {
        Globals.mc.renderEngine.bindTexture(b.resourceLocation);
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        guiScreen.drawTexturedModalRect(this.bbb + 50, this.bbc, 0, 0, 22, 8);
        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        guiScreen.drawTexturedModalRect(this.bbb + 50 + 22, this.bbc, 0, 0, 22, 8);
        guiScreen.drawCenteredString(guiScreen.mc.fontRenderer, this.bay, this.bbb + 22, this.bbc + 4 - guiScreen.mc.fontRenderer.FONT_HEIGHT / 2, Integer.parseInt("FFFFFF", 16));
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void b(final GuiScreen guiScreen) {
        final Iterator<b> iterator = b.baw.iterator();
        while (iterator.hasNext()) {
            iterator.next().a(guiScreen);
        }
    }
    
    public static void a() {
        b.baw.clear();
        b.bax = 0;
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/white.png");
        b.baw = new ArrayList<b>();
        b.bax = 1;
    }
}
