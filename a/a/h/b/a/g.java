package a.a.h.b.a;

import a.a.h.a.*;
import java.awt.*;
import a.a.k.f.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import a.a.k.e.*;

public class g extends b
{
    private static final Color za;
    
    public g() {
        super("Obby Counter");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        this.e().bco = 18;
        this.e().bcp = 18;
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        if (!(g.mc.currentScreen instanceof a.a.h.b.b)) {
            c.a(this.d().bco, this.d().bcp, this.d().bco + this.e().bco, this.d().bcp + this.e().bcp, g.za.getRGB());
        }
        final ItemStack itemStack = new ItemStack(Blocks.OBSIDIAN);
        itemStack.setCount(a.a.k.e.a.a(itemStack.getItem(), true, a.a.k.e.a.a.bdc));
        c.a(this.d().bco, this.d().bcp, itemStack);
    }
    
    static {
        za = new Color(64, 64, 64, 127);
    }
}
