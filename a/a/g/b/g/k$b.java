package a.a.g.b.g;

enum b
{
    public static final b ss;
    public static final b st;
    public static final b su;
    public static final b sv;
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
        b.ss = new b("ALL", 0);
        b.st = new b("EXP_ONLY", 1);
        b.su = new b("CRYSTAL_ONLY", 2);
        b.sv = new b("EXP_AND_CRYSTAL_ONLY", 3);
        b.$VALUES = new b[] { b.ss, b.st, b.su, b.sv };
    }
}
