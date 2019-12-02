package a.a.k.c;

public class c
{
    public int bco;
    public int bcp;
    
    public c(final int bco, final int bcp) {
        super();
        this.bco = bco;
        this.bcp = bcp;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof c) {
            final c c = (c)o;
            return c.bco == this.bco && c.bcp == this.bcp;
        }
        return super.equals(o);
    }
    
    @Override
    public String toString() {
        return "(" + this.bco + ", " + this.bcp + ")";
    }
}
