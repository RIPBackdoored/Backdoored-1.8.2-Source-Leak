package a.a.k.f;

public enum a
{
    bdi("INSIDE", 0), 
    bdj("OUTSIDE", 1), 
    bdk("INVERTED", 2), 
    bdl("FAIL", 3);
    
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
        $VALUES = new a[] { a.bdi, a.bdj, a.bdk, a.bdl };
    }
}
