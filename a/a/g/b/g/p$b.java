package a.a.g.b.g;

enum b
{
    public static final b td;
    public static final b te;
    public static final b tf;
    public static final b tg;
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
        b.td = new b("NORTH", 0);
        b.te = new b("EAST", 1);
        b.tf = new b("SOUTH", 2);
        b.tg = new b("WEST", 3);
        b.$VALUES = new b[] { b.td, b.te, b.tf, b.tg };
    }
}
