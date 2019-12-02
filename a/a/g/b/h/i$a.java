package a.a.g.b.h;

enum a
{
    public static final a vq;
    public static final a vr;
    public static final a vs;
    public static final a vt;
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
        a.vq = new a("NOHANDS", 0);
        a.vr = new a("NOLEFT", 1);
        a.vs = new a("NORIGHT", 2);
        a.vt = new a("ALL", 3);
        a.$VALUES = new a[] { a.vq, a.vr, a.vs, a.vt };
    }
}
