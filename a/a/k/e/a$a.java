package a.a.k.e;

public enum a
{
    bdb("NUMSTACKS", 0), 
    bdc("NUMITEMS", 1);
    
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
        $VALUES = new a[] { a.bdb, a.bdc };
    }
}
