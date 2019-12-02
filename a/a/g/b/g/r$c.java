package a.a.g.b.g;

enum c
{
    public static final c tq;
    public static final c tr;
    public static final c ts;
    private static final /* synthetic */ c[] $VALUES;
    
    public static c[] values() {
        return c.$VALUES.clone();
    }
    
    public static c valueOf(final String s) {
        return Enum.valueOf(c.class, s);
    }
    
    private c(final String s, final int n) {
    }
    
    static {
        c.tq = new c("FILE", 0);
        c.tr = new c("SPAM", 1);
        c.ts = new c("EMPTY", 2);
        c.$VALUES = new c[] { c.tq, c.tr, c.ts };
    }
}
