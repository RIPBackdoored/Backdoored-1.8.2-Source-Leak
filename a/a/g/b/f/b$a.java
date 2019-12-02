package a.a.g.b.f;

enum a
{
    public static final a qz;
    public static final a ra;
    public static final a rb;
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
        a.qz = new a("BOOST", 0);
        a.ra = new a("CONTROL", 1);
        a.rb = new a("FLIGHT", 2);
        a.$VALUES = new a[] { a.qz, a.ra, a.rb };
    }
}
