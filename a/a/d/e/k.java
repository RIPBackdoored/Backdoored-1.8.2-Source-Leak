package a.a.d.e;

import com.backdoored.event.*;

public abstract class k extends BackdooredEvent
{
    public float ep;
    public float eq;
    public boolean er;
    
    public k(final float ep, final float eq, final boolean er) {
        super();
        this.ep = ep;
        this.eq = eq;
        this.er = er;
    }
    
    public static class a extends k
    {
        public a(final float n, final float n2, final boolean b) {
            super(n, n2, b);
        }
    }
    
    public static class b extends k
    {
        public b(final float n, final float n2, final boolean b) {
            super(n, n2, b);
        }
    }
}
