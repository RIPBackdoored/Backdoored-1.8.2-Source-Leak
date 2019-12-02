package a.a.g.b.c;

enum a
{
    public static final a km;
    public static final a kn;
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
        a.km = new a("HOPPER_ONLY", 0);
        a.kn = new a("DISPENSER", 1);
        a.$VALUES = new a[] { a.km, a.kn };
    }
}
