package a.a.g.b.d;

enum a
{
    public static final a os;
    public static final a ot;
    public static final a ou;
    public static final a ov;
    public static final a ow;
    public static final a ox;
    public static final a oy;
    public static final a oz;
    private final String pa;
    private static final /* synthetic */ a[] $VALUES;
    
    public static a[] values() {
        return a.$VALUES.clone();
    }
    
    public static a valueOf(final String s) {
        return Enum.valueOf(a.class, s);
    }
    
    private a(final String s, final int n, final String pa) {
        this.pa = pa;
    }
    
    @Override
    public String toString() {
        return this.pa;
    }
    
    static {
        a.os = new a("OFFHAND", 0, "Offhand");
        a.ot = new a("SNEAK", 1, "Sneak");
        a.ou = new a("VCLIP", 2, "VClip");
        a.ov = new a("WINDOWCLICK", 3, "Window Click");
        a.ow = new a("ITEMSWITCH", 4, "Item Switch");
        a.ox = new a("USEITEM", 5, "Use Item");
        a.oy = new a("HANDSPAM", 6, "Hand Spam");
        a.oz = new a("ENTITY_MOVEMENT", 7, "Entity Movement");
        a.$VALUES = new a[] { a.os, a.ot, a.ou, a.ov, a.ow, a.ox, a.oy, a.oz };
    }
}
