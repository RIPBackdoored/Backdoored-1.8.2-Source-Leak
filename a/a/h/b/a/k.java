package a.a.h.b.a;

import a.a.h.a.*;
import java.awt.*;
import a.a.k.f.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import a.a.k.e.*;

public class k extends b
{
    private static final Color zb;
    
    public k() {
        super("Totem Counter");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        this.e().bco = 18;
        this.e().bcp = 18;
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        if (!(k.mc.currentScreen instanceof a.a.h.b.b)) {
            c.a(this.d().bco, this.d().bcp, this.d().bco + this.e().bco, this.d().bcp + this.e().bcp, k.zb.getRGB());
        }
        final ItemStack itemStack = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack.setCount(a.a.k.e.a.b(Items.TOTEM_OF_UNDYING, true));
        c.a(this.d().bco, this.d().bcp, itemStack);
    }
    
    static {
        zb = new Color(64, 64, 64, 127);
    }
}
