package a.a.g.b.c;

enum a
{
    public static final a mk;
    public static final a ml;
    public static final a mm;
    public static final a mn;
    public static final a mo;
    public static final a mp;
    public static final a mq;
    private String mr;
    private static final /* synthetic */ a[] $VALUES;
    
    public static a[] values() {
        return a.$VALUES.clone();
    }
    
    public static a valueOf(final String s) {
        return Enum.valueOf(a.class, s);
    }
    
    private a(final String s, final int n, final String mr) {
        this.mr = mr;
    }
    
    @Override
    public String toString() {
        return this.mr;
    }
    
    static {
        a.mk = new a("SHITSTAIN", 0, "Nice fight, shit_stain.pl owns me and all!");
        a.ml = new a("SHITTIER", 1, "Nice fight, shit_tier.pl owns me and all!");
        a.mm = new a("DOTSHIT", 2, "Nice fight, DotShit.cc owns me and all!");
        a.mn = new a("EZ", 3, "Ez");
        a.mo = new a("COOKIEDRAGON", 4, "Nice fight, cookiedragon234 owns me and all!");
        a.mp = new a("TIGERMOUTHBEAR", 5, "Nice fight, tigermouthbear owns me and all!");
        a.mq = new a("BACKDOOED", 6, "Nice fight, Backdoored Client owns me and all!");
        a.$VALUES = new a[] { a.mk, a.ml, a.mm, a.mn, a.mo, a.mp, a.mq };
    }
}
