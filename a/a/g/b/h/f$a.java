package a.a.g.b.h;

enum a
{
    public static final a uz;
    public static final a va;
    public static final a vb;
    public static final a vc;
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
        a.uz = new a("BEDROCK", 0);
        a.va = new a("OBBY", 1);
        a.vb = new a("OTHER", 2);
        a.vc = new a("NONE", 3);
        a.$VALUES = new a[] { a.uz, a.va, a.vb, a.vc };
    }
}
