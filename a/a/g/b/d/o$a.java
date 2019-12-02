package a.a.g.b.d;

enum a
{
    public static final a po;
    public static final a pp;
    public static final a pq;
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
        a.po = new a("BOOK", 0);
        a.pp = new a("SIGN", 1);
        a.pq = new a("NEW", 2);
        a.$VALUES = new a[] { a.po, a.pp, a.pq };
    }
}
