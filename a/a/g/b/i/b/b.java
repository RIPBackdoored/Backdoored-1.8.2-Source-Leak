package a.a.g.b.i.b;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import com.backdoored.*;

public class b extends Gui
{
    private static Minecraft mc;
    private ResourceLocation resourceLocation;
    private ResourceLocation resourceLocation;
    private static final String xc = "Backdoored 1.8.2";
    private static int xd;
    
    public b() {
        super();
        this.resourceLocation = new ResourceLocation("backdoored", "textures/dev-donor-client.png");
        this.resourceLocation = new ResourceLocation("backdoored", "textures/backdoored-standard-client.png");
    }
    
    static {
        b.mc = Globals.mc;
        b.xd = 2;
    }
}
