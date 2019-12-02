package a.a.g.b.d;

enum a
{
    public static final a pf;
    public static final a pg;
    private static final /* synthetic */ a[] $VALUES;
    
    public static a[] values() {
        return a.$VALUES.clone();
    }
    
    public static a valueOf(final String s) {
        return Enum.valueOf(a.class, s);
    }
    
    private a(final String s, final int n) {
    }
    
    static {
        a.pf = new a("NO_ANIMATION", 0);
        a.pg = new a("PACKET_MINE", 1);
        a.$VALUES = new a[] { a.pf, a.pg };
    }
}
