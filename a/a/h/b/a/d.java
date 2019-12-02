package a.a.h.b.a;

import a.a.h.a.*;
import a.a.g.b.*;
import java.util.function.*;
import java.util.stream.*;
import com.backdoored.hacks.*;
import a.a.k.*;
import java.util.*;

public class d extends b
{
    public d() {
        super("Enabled Hacks");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        final List<? super Object> list = c.hacks().stream().filter((Predicate<? super Object>)d::a).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        int n4 = 0;
        int n5 = 0;
        for (final BaseHack baseHack : list) {
            a.a.k.a.a(baseHack.name, this.d().bco, this.d().bcp + n5);
            n5 += d.mc.fontRenderer.FONT_HEIGHT + 2;
            final int stringWidth = d.mc.fontRenderer.getStringWidth(baseHack.name);
            if (stringWidth > n4) {
                n4 = stringWidth;
            }
        }
        int n6 = list.size() * (d.mc.fontRenderer.FONT_HEIGHT + 2);
        n6 -= 2;
        this.b(new a.a.k.c.c(n4, n6));
    }
    
    private static /* synthetic */ boolean a(final BaseHack baseHack) {
        return baseHack.getEnabled() && baseHack.hn.getValInt();
    }
}
