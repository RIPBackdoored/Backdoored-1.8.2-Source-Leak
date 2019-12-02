package a.a.h.b;

import net.minecraft.client.gui.*;
import com.backdoored.*;
import a.a.h.a.*;

public class b extends GuiScreen
{
    public b() {
        super();
    }
    
    public void initGui() {
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        for (int i = 0; i < Backdoored.k.yx.size(); ++i) {
            Backdoored.k.yx.get(i).b(n, n2, n3);
        }
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) {
        for (int i = 0; i < Backdoored.k.yx.size(); ++i) {
            Backdoored.k.yx.get(i).d(n, n2, n3);
        }
    }
    
    protected void mouseReleased(final int n, final int n2, final int n3) {
        for (int i = 0; i < Backdoored.k.yx.size(); ++i) {
            Backdoored.k.yx.get(i).a(n, n2, n3);
        }
    }
    
    public void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        for (int i = 0; i < Backdoored.k.yx.size(); ++i) {
            Backdoored.k.yx.get(i).b(n, n2, n3);
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        Backdoored.k.yx.stream().filter(a -> a instanceof a.a.h.a.b).map(b -> b).forEach(b2 -> b2.b(false));
    }
    
    private static /* synthetic */ void a(final a.a.h.a.b b) {
        b.b(false);
    }
    
    private static /* synthetic */ a.a.h.a.b a(final a a) {
        return (a.a.h.a.b)a;
    }
    
    private static /* synthetic */ boolean b(final a a) {
        return a instanceof a.a.h.a.b;
    }
}
