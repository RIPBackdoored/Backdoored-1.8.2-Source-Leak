package a.a.k.f;

public static class c
{
    private final double bdm;
    private final double bdn;
    private final a bdo;
    
    public c(final double bdm, final double bdn, final a bdo) {
        super();
        this.bdm = bdm;
        this.bdn = bdn;
        this.bdo = bdo;
    }
    
    public double a() {
        return this.bdm;
    }
    
    public double b() {
        return this.bdn;
    }
    
    public a c() {
        return this.bdo;
    }
    
    public boolean a(final a a) {
        return this.bdo == a;
    }
    
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
}
