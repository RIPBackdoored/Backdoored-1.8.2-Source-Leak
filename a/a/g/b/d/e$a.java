package a.a.g.b.d;

enum a
{
    public static final a om;
    public static final a on;
    public static final a oo;
    private final String op;
    private static final /* synthetic */ a[] $VALUES;
    
    public static a[] values() {
        return a.$VALUES.clone();
    }
    
    public static a valueOf(final String s) {
        return Enum.valueOf(a.class, s);
    }
    
    private a(final String s, final int n, final String op) {
        this.op = op;
    }
    
    @Override
    public String toString() {
        return this.op;
    }
    
    static {
        a.om = new a("PORTAL", 0, "Portal God Mode");
        a.on = new a("SPOOFDEATH", 1, "Spoof Death");
        a.oo = new a("PACKET", 2, "Anti Packet");
        a.$VALUES = new a[] { a.om, a.on, a.oo };
    }
}
