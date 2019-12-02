package a.a.h.b.a;

import a.a.h.a.*;
import net.minecraft.client.*;
import a.a.k.*;

public class e extends b
{
    public e() {
        super("Fps");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        final String string = Minecraft.getDebugFPS() + " fps";
        a.a.k.a.a(string, this.d().bco, this.d().bcp);
        this.e().bco = e.mc.fontRenderer.getStringWidth(string);
        this.e().bcp = e.mc.fontRenderer.FONT_HEIGHT;
    }
}
