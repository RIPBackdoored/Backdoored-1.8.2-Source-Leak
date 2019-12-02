package a.a.e;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import com.backdoored.*;

public class c
{
    public static final FontRenderer fontRenderer;
    public static final Class<GlStateManager> ex;
    public static final b ey;
    
    public c() {
        super();
    }
    
    static {
        fontRenderer = Globals.mc.fontRenderer;
        ex = GlStateManager.class;
        ey = new b(Globals.mc);
    }
}
