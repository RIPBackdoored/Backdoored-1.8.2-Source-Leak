package a.a.k.f;

public enum a
{
    bdd("ORTHOGONAL", 0), 
    bde("DIRECT", 1), 
    bdf("NONE", 2);
    
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
        $VALUES = new a[] { a.bdd, a.bde, a.bdf };
    }
}
