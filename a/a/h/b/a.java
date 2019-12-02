package a.a.h.b;

import a.a.h.a.*;
import com.backdoored.*;
import java.util.stream.*;
import java.awt.*;
import a.a.k.f.*;
import java.util.*;

public class a extends b
{
    public a() {
        super("Element Picker");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        if (a.mc.currentScreen instanceof a.a.h.b.b) {
            final List<? super Object> list = Backdoored.k.yx.stream().filter(a -> a != this).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
            c.a(this.d().bco = list.size() * (a.mc.fontRenderer.FONT_HEIGHT + 2) - 2, this.d().bcp, this.e().bco, this.e().bcp, Color.BLACK.getRGB());
            a.mc.fontRenderer.drawString("Element Picket", this.d().bco + 2, this.d().bcp + 2, Color.WHITE.getRGB());
            int n4 = a.mc.fontRenderer.FONT_HEIGHT + 2;
            c.a(this.d().bco + 2, this.d().bcp + 2 + n4, this.e().bco - 2, this.e().bcp - 2, Color.WHITE.getRGB());
            for (final a.a.h.a.a a2 : list) {
                a.mc.fontRenderer.drawString(a2.b(), this.d().bco + 2, this.d().bcp + n4, a2.c() ? Color.GRAY.getRGB() : Color.WHITE.getRGB());
                n4 += a.mc.fontRenderer.FONT_HEIGHT + 2;
            }
            this.e().bcp = n4 + a.mc.fontRenderer.FONT_HEIGHT + 2;
        }
        this.f();
    }
    
    @Override
    public void a(final int n, final int n2, final int n3) {
        super.a(n, n2, n3);
        if (n3 == 0 && this.a(n, n2)) {
            final List<? super Object> list = Backdoored.k.yx.stream().filter(a -> a != this).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
            int n4 = a.mc.fontRenderer.FONT_HEIGHT + 2;
            for (final a.a.h.a.a a2 : list) {
                if (n >= this.d().bco + 2 && n2 >= this.d().bcp + n4 && n <= this.d().bco + 2 + a.mc.fontRenderer.getStringWidth(a2.b()) && n2 <= this.d().bcp + n4 + a.mc.fontRenderer.FONT_HEIGHT) {
                    a2.a(!a2.c());
                }
                n4 += a.mc.fontRenderer.FONT_HEIGHT + 2;
            }
        }
    }
    
    private /* synthetic */ boolean a(final a.a.h.a.a a) {
        return a != this;
    }
    
    private /* synthetic */ boolean b(final a.a.h.a.a a) {
        return a != this;
    }
}
