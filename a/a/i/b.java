package a.a.i;

public class b
{
    private static b bag;
    
    public b() {
        super();
    }
    
    public static String a(final String s) {
        if (b.bag == null) {
            b.bag = new b();
        }
        return "";
    }
    
    public static String b(final String s) {
        if (b.bag == null) {
            b.bag = new b();
        }
        return "";
    }
    
    public native String c(final String p0);
    
    public native String d(final String p0);
}
