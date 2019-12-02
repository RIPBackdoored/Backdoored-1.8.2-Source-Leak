package a.a.h.a;

import a.a.k.c.*;
import java.awt.*;
import com.backdoored.*;
import net.minecraft.client.gui.*;

public abstract class b extends a
{
    private boolean yu;
    protected c yv;
    private static final Color yw;
    
    public b(final String s) {
        super();
        this.yu = false;
        this.yv = new c(0, 0);
        this.a(s);
        this.a(true);
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        if (!this.g()) {
            return;
        }
        if (b.mc.currentScreen instanceof a.a.h.b.b) {
            if (Backdoored.k.a(n, n2) == this || this.yu) {
                if (!this.c()) {
                    a.a.k.f.c.a(this.d().bco - 1, this.d().bcp - 1, this.d().bco + this.e().bco + 1, this.d().bcp + this.e().bcp + 1, b.yw.getRGB());
                }
                else {
                    a.a.k.f.c.a(this.d().bco - 1, this.d().bcp - 1, this.d().bco + this.e().bco + 1, this.d().bcp + this.e().bcp + 1, Color.DARK_GRAY.getRGB());
                }
                if (!this.yu) {
                    final int n4 = b.mc.fontRenderer.getStringWidth(this.b()) + 1;
                    final int n5 = b.mc.fontRenderer.FONT_HEIGHT + 1;
                    if (!this.c()) {
                        a.a.k.f.c.a(n + 5, n2 + 5, n + 5 + n4, n2 + 5 + n5, b.yw.getRGB());
                    }
                    else {
                        a.a.k.f.c.a(n + 5, n2 + 5, n + 5 + n4, n2 + 5 + n5, Color.WHITE.getRGB());
                    }
                    b.mc.fontRenderer.drawString(this.b(), n + 6, n2 + 6, 0);
                }
            }
            else if (!this.c()) {
                a.a.k.f.c.a(this.d().bco - 1, this.d().bcp - 1, this.d().bco + this.e().bco + 1, this.d().bcp + this.e().bcp + 1, b.yw.getRGB());
            }
            else {
                a.a.k.f.c.a(this.d().bco - 1, this.d().bcp - 1, this.d().bco + this.e().bco + 1, this.d().bcp + this.e().bcp + 1, Color.GRAY.getRGB());
            }
        }
        this.f();
    }
    
    @Override
    public void d(final int n, final int n2, final int n3) {
        final boolean b = Backdoored.k.a(n, n2) == this;
        if (n3 == 1 && b) {
            this.a(!this.c());
        }
        if (n3 == 0 && b) {
            this.yu = true;
            this.yv.bco = n - this.d().bco;
            this.yv.bcp = n2 - this.d().bcp;
            Backdoored.k.a(this);
        }
    }
    
    @Override
    public void a(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            this.yu = false;
        }
    }
    
    @Override
    public void b(final int n, final int n2, final int n3) {
        if (n3 == 0 && this.yu) {
            this.d().bco = n - this.yv.bco;
            this.d().bcp = n2 - this.yv.bcp;
            this.f();
        }
    }
    
    protected void f() {
        if (this.d().bco < 0) {
            this.d().bco = 0;
        }
        if (this.d().bcp < 0) {
            this.d().bcp = 0;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(b.mc);
        if (this.d().bco + this.e().bco > scaledResolution.getScaledWidth()) {
            this.d().bco = scaledResolution.getScaledWidth() - this.e().bco;
        }
        if (this.d().bcp + this.e().bcp > scaledResolution.getScaledHeight()) {
            this.d().bcp = scaledResolution.getScaledHeight() - this.e().bcp;
        }
    }
    
    public void b(final boolean yu) {
        this.yu = yu;
    }
    
    public boolean g() {
        return this.c() || b.mc.currentScreen instanceof a.a.h.b.b;
    }
    
    static {
        yw = new Color(255, 0, 0, 127);
    }
}
