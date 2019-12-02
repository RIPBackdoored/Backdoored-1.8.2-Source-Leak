package a.a.h.b.a;

import a.a.h.a.*;
import java.awt.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import a.a.k.e.*;

public class c extends b
{
    private static final Color yy;
    
    public c() {
        super("Crystal Counter");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        this.e().bco = 18;
        this.e().bcp = 18;
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        if (!(c.mc.currentScreen instanceof a.a.h.b.b)) {
            a.a.k.f.c.a(this.d().bco, this.d().bcp, this.d().bco + this.e().bco, this.d().bcp + this.e().bcp, c.yy.getRGB());
        }
        final ItemStack itemStack = new ItemStack(Items.END_CRYSTAL);
        itemStack.setCount(a.a.k.e.a.a(Items.END_CRYSTAL, true, a.a.k.e.a.a.bdc));
        a.a.k.f.c.a(this.d().bco, this.d().bcp, itemStack);
    }
    
    static {
        yy = new Color(64, 64, 64, 127);
    }
}
