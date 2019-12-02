package a.a.g.b.d;

enum a
{
    public static final a oe;
    public static final a of;
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
        a.oe = new a("PRIVATE", 0);
        a.of = new a("PUBLIC", 1);
        a.$VALUES = new a[] { a.oe, a.of };
    }
}
