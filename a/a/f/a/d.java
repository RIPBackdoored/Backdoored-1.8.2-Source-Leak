package a.a.f.a;

import com.backdoored.setting.*;
import net.minecraft.client.gui.*;
import com.backdoored.*;
import a.a.f.*;
import org.lwjgl.opengl.*;

public class d extends e
{
    private Setting gc;
    
    public d(final Setting gc) {
        super(gc);
        this.gc = gc;
    }
    
    @Override
    public void c(final GuiScreen guiScreen, final int n, final int n2) {
        Globals.mc.renderEngine.bindTexture(b.resourceLocation);
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        guiScreen.drawTexturedModalRect(this.fe + 1, this.ff + this.fh - 2, 0, 0, (this.fg - 2) * this.gc.getValInt().intValue() / Math.max(this.gc.g().intValue() - this.gc.i().intValue(), 1), 1);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    @Override
    public Setting a() {
        return this.gc;
    }
}
