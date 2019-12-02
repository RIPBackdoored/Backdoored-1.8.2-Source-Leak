package a.a.g.b.e;

enum b
{
    public static final b qq;
    public static final b qr;
    public static final b qs;
    public static final b qt;
    private static final /* synthetic */ b[] $VALUES;
    
    public static b[] values() {
        return b.$VALUES.clone();
    }
    
    public static b valueOf(final String s) {
        return Enum.valueOf(b.class, s);
    }
    
    private b(final String s, final int n) {
    }
    
    static {
        b.qq = new b("posX", 0);
        b.qr = new b("posZ", 1);
        b.qs = new b("negX", 2);
        b.qt = new b("negZ", 3);
        b.$VALUES = new b[] { b.qq, b.qr, b.qs, b.qt };
    }
}
