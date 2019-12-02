package a.a.f.a;

import java.util.*;
import com.backdoored.setting.*;
import a.a.f.*;
import net.minecraft.client.gui.*;

public class e extends a
{
    private static ArrayList<e> gd;
    private c ge;
    private Setting gf;
    public boolean gg;
    
    public e(final Setting gf) {
        super(0, 0, 100, 12, gf.a() + ": " + gf.getValInt(), false, new float[] { 1.0f, 0.4f, 0.0f, 1.0f });
        this.gg = false;
        this.ge = b.gl.get(gf.b());
        this.gf = gf;
        this.ge.c().add(this);
        e.gd.add(this);
    }
    
    @Override
    public void c(final GuiScreen guiScreen, final int n, final int n2) {
    }
    
    public c b() {
        return this.ge;
    }
    
    public Setting a() {
        return this.gf;
    }
    
    public static ArrayList<e> c() {
        return e.gd;
    }
    
    static {
        e.gd = new ArrayList<e>();
    }
}
