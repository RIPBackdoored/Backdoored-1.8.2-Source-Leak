package a.a.g.b.e;

enum a
{
    public static final a qu;
    public static final a qv;
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
        a.qu = new a("PRIVATE", 0);
        a.qv = new a("PUBLIC", 1);
        a.$VALUES = new a[] { a.qu, a.qv };
    }
}
